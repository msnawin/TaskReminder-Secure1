package com.todoapp20.TodoApplication.Service;



import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to TaskCloud!");
        message.setText("Hi " + name + ",\n\nYour account is ready.");
        mailSender.send(message);
    }

    @Async
    public void sendReminder(String toEmail, String taskTitle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reminder: " + taskTitle);
        message.setText("The task '" + taskTitle + "' is due today!");
        mailSender.send(message);
    }
}