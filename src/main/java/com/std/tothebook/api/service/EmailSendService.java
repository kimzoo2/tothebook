package com.std.tothebook.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailSendService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    public void sendMail(String to, String subject, String text) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();

        mailMessage.setFrom(fromEmailId + "@naver.com");
        mailMessage.addRecipients(Message.RecipientType.TO, to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text, "utf-8", "html");

        javaMailSender.send(mailMessage);
    }
}
