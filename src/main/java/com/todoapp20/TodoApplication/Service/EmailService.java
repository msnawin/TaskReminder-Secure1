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

    /**
     * Sends a professional HTML welcome email.
     */
    @Async
    public void sendWelcomeEmail(String toEmail, String name) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            String htmlMsg = "<h2 style='color:#2e7d32;'>Welcome to TaskCloud, " + name + "!</h2>"
                    + "<p>We are thrilled to have you on board. TaskCloud is here to help you manage your tasks, stay productive, "
                    + "and achieve your goals efficiently.</p>"
                    + "<ul>"
                    + "<li>Add tasks and set priorities</li>"
                    + "<li>Set due dates and get reminders</li>"
                    + "<li>Track your progress daily</li>"
                    + "</ul>"
                    + "<p>If you have any questions, feel free to reach out. We are always here to help!</p>"
                    + "<a href='http://localhost:8080/login' "
                    + "style='background:#2e7d32;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;'>Log In to Your Dashboard</a>"
                    + "<p>Best regards,<br>The TaskCloud Team</p>";

            helper.setFrom("nawinmutharasu2005@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Welcome to TaskCloud! ✨");
            helper.setText(htmlMsg, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send welcome email", e);
        }
    }

    /**
     * Sends a motivational HTML reminder email for a task due today.
     */
    @Async
    public void sendReminder(String toEmail, String taskTitle) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            String htmlMsg = "<h3 style='color:#f57c00;'>Reminder: " + taskTitle + "</h3>"
                    + "<p>Hi there!</p>"
                    + "<p>Your task <strong>'" + taskTitle + "'</strong> is due today.</p>"
                    + "<p style='font-style:italic;color:#555;'>Remember: Every small step brings you closer to your goals. "
                    + "Stay focused, consistent, and keep pushing forward! 💪</p>"
                    + "<p>You've got this!</p>"
                    + "<p>Best regards,<br>The TaskCloud Team</p>";

            helper.setFrom("nawinmutharasu2005@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Motivational Reminder: " + taskTitle);
            helper.setText(htmlMsg, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send reminder email", e);
        }
    }
}
