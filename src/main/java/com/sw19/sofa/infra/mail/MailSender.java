package com.sw19.sofa.infra.mail;

public interface MailSender {
    void send(String to, String title, String content);
}
