package com.sw19.sofa.domain.memberTag.service;

import com.sw19.sofa.domain.memberTag.repository.MemberTagRepository;
import com.sw19.sofa.global.common.dto.MemberTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberTagService {
    private final MemberTagRepository memberTagRepository;

    public List<MemberTagDto> getMemberTagDtoListByLinkCardId(Long linkCardId){
        return memberTagRepository.findAllByLinkCardId(linkCardId).stream()
                .map(MemberTagDto::new).toList();
    }
}
