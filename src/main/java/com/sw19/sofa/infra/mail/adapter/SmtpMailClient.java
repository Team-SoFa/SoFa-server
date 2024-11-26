package com.sw19.sofa.infra.mail.adapter;

import com.sw19.sofa.global.error.exception.BusinessException;
import com.sw19.sofa.infra.mail.MailSender;
import com.sw19.sofa.infra.mail.error.MailErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpMailClient implements MailSender {

    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BusinessException(MailErrorCode.FAILED_SEND_EMAIL);
        }
    }
}
