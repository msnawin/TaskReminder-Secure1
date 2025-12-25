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

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendWelcomeEmail(String toEmail, String name) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            String htmlMsg =
                    "<div style='background-color: #020617; color: #f8fafc; padding: 40px; font-family: \"Plus Jakarta Sans\", sans-serif; border-radius: 16px; max-width: 600px; margin: auto; border: 1px solid rgba(255,255,255,0.08);'>" +
                            "  <h1 style='color: #6366f1; font-size: 26px; margin-top: 0; letter-spacing: -0.5px;'>Hello, " + name + ".</h1>" +
                            "  <p style='font-size: 16px; line-height: 1.6; color: #94a3b8;'>" +
                            "    Welcome to <strong>TaskCloud</strong>. You are now part of a workspace designed for those who value time and precision. " +
                            "    We believe that organization is the foundation of great achievements." +
                            "  </p>" +
                            "  <div style='margin: 30px 0; padding: 25px; border-left: 3px solid #6366f1; background: rgba(99, 102, 241, 0.05); font-style: italic;'>" +
                            "    <p style='margin: 0; font-size: 18px; color: #cbd5e1; line-height: 1.4;'>\"The secret of getting ahead is getting started.\"</p>" +
                            "    <p style='margin: 10px 0 0 0; font-size: 13px; color: #6366f1; font-weight: bold;'>— Mark Twain</p>" +
                            "  </div>" +
                            "  <p style='font-size: 15px; line-height: 1.6; color: #94a3b8;'>" +
                            "    Your dashboard is ready. Start by capturing your first big goal today." +
                            "  </p>" +
                            "  <div style='margin-top: 50px; text-align: right; border-top: 1px solid rgba(255,255,255,0.05); padding-top: 20px;'>" +
                            "    <p style='margin: 0; font-size: 14px; color: #f8fafc; font-weight: 600;'>M S Nawin</p>" +
                            "    <p style='margin: 0; font-size: 12px; color: #6366f1; font-weight: bold; text-transform: uppercase; letter-spacing: 1px;'>Developer, TaskCloud</p>" +
                            "  </div>" +
                            "</div>";

            helper.setFrom("nawinmutharasu2005@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("A New Chapter in Productivity ✨");
            helper.setText(htmlMsg, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send welcome email", e);
        }
    }

    @Async
    public void sendReminder(String toEmail, String taskTitle) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            String htmlMsg =
                    "<div style='background-color: #020617; color: #f8fafc; padding: 40px; font-family: \"Plus Jakarta Sans\", sans-serif; border-radius: 16px; max-width: 600px; margin: auto; border: 1px solid rgba(255,255,255,0.08);'>" +
                            "  <h2 style='color: #f59e0b; font-size: 18px; margin-top: 0; text-transform: uppercase; letter-spacing: 2px;'>Momentum Check</h2>" +
                            "  <p style='font-size: 16px; color: #94a3b8;'>A gentle reminder regarding your commitment:</p>" +
                            "  <div style='background: rgba(245, 158, 11, 0.05); border: 1px solid rgba(245, 158, 11, 0.1); padding: 25px; border-radius: 12px; margin: 25px 0; text-align: center;'>" +
                            "    <p style='margin: 0; font-size: 20px; color: #ffffff; font-weight: 800; letter-spacing: -0.5px;'>" + taskTitle + "</p>" +
                            "  </div>" +
                            "  <div style='margin: 30px 0; padding: 20px; text-align: center; border-bottom: 1px solid rgba(255,255,255,0.05);'>" +
                            "    <p style='margin: 0; font-size: 15px; color: #cbd5e1; font-style: italic;'>\"Action is the foundational key to all success.\"</p>" +
                            "    <p style='margin: 5px 0 0 0; font-size: 12px; color: #f59e0b;'>— Pablo Picasso</p>" +
                            "  </div>" +
                            "  <div style='margin-top: 30px; text-align: right;'>" +
                            "    <p style='margin: 0; font-size: 14px; color: #f8fafc; font-weight: 600;'>M S Nawin</p>" +
                            "    <p style='margin: 0; font-size: 12px; color: #6366f1; font-weight: bold; text-transform: uppercase; letter-spacing: 1px;'>Developer, TaskCloud</p>" +
                            "  </div>" +
                            "</div>";

            helper.setFrom("nawinmutharasu2005@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Focus Check: " + taskTitle);
            helper.setText(htmlMsg, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send reminder email", e);
        }
    }
}



