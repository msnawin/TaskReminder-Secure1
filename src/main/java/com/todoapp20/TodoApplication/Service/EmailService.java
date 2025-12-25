package com.todoapp20.TodoApplication.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) { this.mailSender = mailSender; }

    @Async
    public void sendWelcomeEmail(String toEmail, String name) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            String htmlMsg = "<h2>Welcome to TaskCloud, " + name + "!</h2>";
            helper.setFrom("nawinmutharasu2005@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Welcome to TaskCloud! ✨");
            helper.setText(htmlMsg, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) { throw new IllegalStateException(e); }
    }

    @Async
    public void sendReminder(String toEmail, String taskTitle) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom("nawinmutharasu2005@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Reminder: " + taskTitle);
            helper.setText("Task due today: " + taskTitle, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) { throw new IllegalStateException(e); }
    }
}
