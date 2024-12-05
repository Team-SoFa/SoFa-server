package com.sw19.sofa.domain.remind.service;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.remind.entity.Remind;
import com.sw19.sofa.domain.remind.enums.RemindSortBy;
import com.sw19.sofa.domain.remind.repository.RemindRepository;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import com.sw19.sofa.global.scheduler.manager.SchedulerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemindService {
    private final RemindRepository remindRepository;
    private final SchedulerManager schedulerManager;

    @Transactional
    public void addAllByLinkCardListAndMember(List<LinkCard> linkCardList, Member member){
        List<Remind> remindList = linkCardList.stream().map(linkCard ->
                Remind.builder()
                        .linkCard(linkCard)
                        .member(member)
                        .build()
        ).toList();

        remindRepository.saveAll(remindList);
    }

    public List<Remind> getRemindListByMember(Member member){
        return remindRepository.findAllByMember(member);
    }

    public List<Remind> searchReminds(
            Long memberId,
            Long lastId,
            int limit,
            RemindSortBy sortBy,
            SortOrder sortOrder
    ) {
        return remindRepository.search(memberId, lastId, limit, sortBy, sortOrder);
    }

    @Transactional
    public void removeFromRemind(LinkCard linkCard, Member member) {

        if (remindRepository.existsByLinkCardAndMember(linkCard, member)) {
            remindRepository.deleteByLinkCardAndMember(linkCard, member);
            if(!remindRepository.existsByMember(member)){
                schedulerManager.stopMemberNotifyScheduler(member.getEncryptUserId());
            }
        }
    }
}
