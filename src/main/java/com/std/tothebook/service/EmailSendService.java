package com.std.tothebook.service;

import com.std.tothebook.vo.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    public void sendMail(Mail mail) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();

        mailMessage.setFrom(fromEmailId + "@naver.com");
        mailMessage.addRecipients(Message.RecipientType.TO, mail.getTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getText(), "utf-8", "html");

        javaMailSender.send(mailMessage);
    }
}
