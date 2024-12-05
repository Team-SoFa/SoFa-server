package com.sw19.sofa.domain.remind.service;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.service.LinkCardService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.remind.dto.response.RemindRes;
import com.sw19.sofa.domain.remind.entity.Remind;
import com.sw19.sofa.domain.remind.enums.RemindSortBy;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import com.sw19.sofa.global.scheduler.manager.SchedulerManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemindManageService {
    private final RemindService remindService;
    private final LinkCardService linkCardService;
    private final SchedulerManager schedulerManager;

    @Transactional
    public void moveUnusedLinkListToRemind() {
        List<LinkCard> unusedLinkCardList = linkCardService.getUnusedLinkCardList();

        Map<Member, List<LinkCard>> linksGroupedByMember = unusedLinkCardList.stream()
                .collect(Collectors.groupingBy(link -> link.getFolder().getMember()));

        linksGroupedByMember.forEach(
                (member, linkCardList) ->
                {
                    // 빈 리마인드 함에 신규 링크카드 추가 시 새로운 스케줄러 시작
                    if(remindService.getRemindListByMember(member).isEmpty()){
                        String encryptUserId = member.getEncryptUserId();
                        try {
                            schedulerManager.startMemberRemindNotifyScheduler(encryptUserId);
                        } catch (SchedulerException e) {
                            log.error("Error stopping the scheduler for memberId: {}",encryptUserId, e);
                        }
                    }

                    remindService.addAllByLinkCardListAndMember(linkCardList, member);
                }
        );

    }

    public ListRes<RemindRes> getRemindList(
            Member member,
            String lastId,
            int limit,
            RemindSortBy sortBy,
            SortOrder sortOrder
    ) {
        Long lastIdLong = lastId != null ? Long.parseLong(lastId) : null;
        List<Remind> reminds = remindService.searchReminds(
                member.getId(),
                lastIdLong,
                limit,
                sortBy,
                sortOrder
        );

        boolean hasNext = false;
        if (reminds.size() > limit) {
            hasNext = true;
            reminds = reminds.subList(0, limit);
        }

        List<RemindRes> responses = reminds.stream()
                .map(RemindRes::from)
                .toList();

        return new ListRes<>(responses, limit, responses.size(), hasNext);
    }
}
