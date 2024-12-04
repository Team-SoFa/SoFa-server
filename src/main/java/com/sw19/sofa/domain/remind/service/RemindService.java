package com.sw19.sofa.domain.remind.service;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.remind.entity.Remind;
import com.sw19.sofa.domain.remind.enums.RemindSortBy;
import com.sw19.sofa.domain.remind.repository.RemindRepository;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemindService {
    private final RemindRepository remindRepository;

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
    public void deleteRemind(LinkCard linkCard, Member member) {
        remindRepository.deleteByLinkCardAndMember(linkCard, member);
    }

    public boolean existsRemind(LinkCard linkCard, Member member) {
        return remindRepository.existsByLinkCardAndMember(linkCard, member);
    }
}
