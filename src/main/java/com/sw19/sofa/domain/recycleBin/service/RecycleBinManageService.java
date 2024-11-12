package com.sw19.sofa.domain.recycleBin.service;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.service.LinkCardService;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecycleBinManageService {
    private final LinkCardService linkCardService;
    @Transactional
    public void permanentlyDelete(String encryptId) {
        Long id = EncryptionUtil.decrypt(encryptId);
        LinkCard linkCard = linkCardService.getLinkCard(id);
        linkCardService.deleteLinkCard(linkCard);
    }
}
