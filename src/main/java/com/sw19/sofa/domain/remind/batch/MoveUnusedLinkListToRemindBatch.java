package com.sw19.sofa.domain.remind.batch;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.service.LinkCardService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.remind.dto.MemberUnUsedLinkCardListDto;
import com.sw19.sofa.domain.remind.service.RemindService;
import com.sw19.sofa.global.batch.listener.JobLoggingListener;
import com.sw19.sofa.global.batch.listener.StepLoggingListener;
import com.sw19.sofa.global.scheduler.manager.SchedulerManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sw19.sofa.global.common.constants.Constants.batchChunkSize;
import static com.sw19.sofa.global.common.constants.Constants.moveRemind;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class MoveUnusedLinkListToRemindBatch {
    private final JobLoggingListener jobLoggingListener;
    private final StepLoggingListener stepLoggingListener;
    private final SchedulerManager schedulerManager;
    private final LinkCardService linkCardService;
    private final RemindService remindService;

    @Bean(name = moveRemind + "Reader")
    @StepScope
    public ItemReader<MemberUnUsedLinkCardListDto> reader() {
        List<LinkCard> unusedLinkCardList = linkCardService.getUnusedLinkCardList();

        Map<Member, List<LinkCard>> linksGroupedByMember = unusedLinkCardList.stream()
                .collect(Collectors.groupingBy(link -> link.getFolder().getMember()));

        // Map.Entry 리스트로 변환
        List<MemberUnUsedLinkCardListDto> memberLinkCardList = linksGroupedByMember.entrySet().stream()
                .map(entry -> new MemberUnUsedLinkCardListDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Iterator 반환을 위한 ItemReader
        return new IteratorItemReader<>(memberLinkCardList);
    }



    @Bean(name = moveRemind + "Processor")
    @StepScope
    public ItemProcessor<MemberUnUsedLinkCardListDto, MemberUnUsedLinkCardListDto> processor() {
        return memberLinkCardList -> {
            Member member = memberLinkCardList.member();

            // 리마인드 서비스로부터 멤버의 리마인드 리스트를 가져와서 검사
            if (remindService.getRemindListByMember(member).isEmpty()) {
                String encryptUserId = member.getEncryptUserId();
                try {
                    // 리마인드 스케줄러 시작
                    schedulerManager.startMemberRemindNotifyScheduler(encryptUserId);
                } catch (SchedulerException e) {
                    log.error("Error starting the scheduler for memberId: {}", encryptUserId, e);
                }
            }

            return memberLinkCardList;
        };
    }

    @Bean(name = moveRemind + "Writer")
    @StepScope
    public ItemWriter<MemberUnUsedLinkCardListDto> writer() {
        return memberLinkCardsList -> {
            for (MemberUnUsedLinkCardListDto memberLinkCards : memberLinkCardsList) {
                Member member = memberLinkCards.member();
                List<LinkCard> linkCardList = memberLinkCards.linkCardList();

                remindService.addAllByLinkCardListAndMember(linkCardList, member);
            }
        };
    }

    @Bean(name = moveRemind + "Step")
    @JobScope
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager transactionManager,
                     ItemReader<MemberUnUsedLinkCardListDto> reader,
                     ItemProcessor<MemberUnUsedLinkCardListDto, MemberUnUsedLinkCardListDto> processor,
                     ItemWriter<MemberUnUsedLinkCardListDto> writer
    ) {

        return new StepBuilder("remindStep", jobRepository)
                .listener(stepLoggingListener)
                .<MemberUnUsedLinkCardListDto, MemberUnUsedLinkCardListDto>chunk(batchChunkSize, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = moveRemind + "Job")
    public Job job(JobRepository jobRepository, Step step
    ) {
        return new JobBuilder(moveRemind + "job", jobRepository)
                .listener(jobLoggingListener)
                .start(step)
                .build();
    }
}
