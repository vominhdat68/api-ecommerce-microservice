package com.ecommerce.user.email.service;


import com.ecommerce.user.email.dto.request.SendEmailRequest;
import com.ecommerce.user.email.exception.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;
//    private final TemplateEngine templateEngine;

    public void sendEmail(SendEmailRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(request.getTo());
            helper.setSubject(request.getSubject());
            helper.setText(request.getContent());
//            helper.setText(content, isHtml); // Support HTML/CSS

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailException("Failed to send email: " + e.getMessage());
        }
    }



}
