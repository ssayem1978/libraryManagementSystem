package org.sayem.librarymanagementsystem.service.impl;

import org.sayem.librarymanagementsystem.entity.User;
import org.sayem.librarymanagementsystem.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendVerificationEmail(User user, String token) {
        String recipientAddress = user.getEmail();
        String subject = "Email Verification";
        String confirmationUrl = "http://localhost:8080/verify?token=" + token;
        String message = "Please click the following link to verify your email address: ";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + confirmationUrl);
        try {
            mailSender.send(email);
            log.info("Verification email sent successfully.");
        } catch (Exception e) {
            log.warn("Failed to send.");
        }
    }

    @Async
    @Override
    public void sendTemporaryUserPassword(User user, String generatedPassword) {
        String recipientAddress = user.getEmail();
        String subject = "Account Information";
        String loginUrl = "http://localhost:8080/login";
        String message = String.format("""
Your account has been created in the Library System. Your account information is given below:

Username: %s
Password: %s

Please log in using the following link: %s
""", user.getUsername(), generatedPassword, loginUrl);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        try {
            mailSender.send(email);
            log.info("Account information sent successfully.");
        } catch (Exception e) {
            log.warn("Failed to send email.", e);
        }
    }
}
