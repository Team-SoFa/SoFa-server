package com.sw19.sofa.infra.mail.service;

import com.sw19.sofa.global.common.dto.MailRequestDto;
import com.sw19.sofa.infra.mail.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailSender mailSender;

    public void sendMail(MailRequestDto request) {
        String content = mergeTemplate(request.templateName(), request.data());
        mailSender.send(request.to(), request.title(), content);
    }

    private String mergeTemplate(String templateName, Map<String, Object> data) {
        return "템플릿 내용";
    }
}
