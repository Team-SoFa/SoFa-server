package com.sw19.sofa.domain.remind.service;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.service.LinkCardService;
import com.sw19.sofa.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RemindManageService {
    private final RemindService remindService;
    private final LinkCardService linkCardService;

    @Transactional
    public void moveUnusedLinkListToRemind() {
        List<LinkCard> unusedLinkCardList = linkCardService.getUnusedLinkCardList();

        Map<Member, List<LinkCard>> linksGroupedByMember = unusedLinkCardList.stream()
                .collect(Collectors.groupingBy(link -> link.getFolder().getMember()));

        linksGroupedByMember.forEach(
                (member, linkCardList) -> remindService.addAllByLinkCardListAndMember(linkCardList, member)
        );

    }
}
