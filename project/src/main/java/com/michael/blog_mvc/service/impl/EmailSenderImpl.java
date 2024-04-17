package com.michael.blog_mvc.service.impl;

import com.michael.blog_mvc.service.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;

    public static final String FAILED_TO_SEND_EMAIL = "Failed to send email";
    public static final String FROM_EMAIL = "blog_support@gmail.com";
    public static final String NEW_PASSWORD_MESSAGE = "Hello  %s,\n\nYou new account password is: %s  \n\nThe Support Team";


    @Override
    @Async
    public void sendEmailForVerification(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm Your email");
            helper.setFrom(FROM_EMAIL);
            mailSender.send(mimeMessage);


        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            throw new RuntimeException(FAILED_TO_SEND_EMAIL);
        }
    }

    @Override
    public void sendNewPassword(String email, String fullName, String password) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(String.format(NEW_PASSWORD_MESSAGE, fullName, password));
            helper.setTo(email);
            helper.setSubject("New password");
            helper.setFrom(FROM_EMAIL);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            throw new RuntimeException(FAILED_TO_SEND_EMAIL);
        }
    }
}

