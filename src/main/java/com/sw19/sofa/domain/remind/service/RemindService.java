package com.sw19.sofa.domain.remind.service;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.remind.entity.Remind;
import com.sw19.sofa.domain.remind.repository.RemindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemindService {
    private final RemindRepository remindRepository;

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

    @Transactional(readOnly = true)
    public List<Remind> getRemindListByMember(Member member){
        return remindRepository.findAllByMember(member);        
    }
}
