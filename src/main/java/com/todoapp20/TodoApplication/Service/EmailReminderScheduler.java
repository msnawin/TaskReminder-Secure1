package com.todoapp20.TodoApplication.Service;

import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Repository.TodoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class EmailReminderScheduler {
    private final TodoRepository todoRepository;
    private final EmailService emailService;
    public EmailReminderScheduler(TodoRepository todoRepository, EmailService emailService) {
        this.todoRepository = todoRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 00 6 * * *", zone = "Asia/Kolkata") // Set for 8:55 PM
    public void checkReminders() {
        System.out.println("--- Scheduler Started at 8:55 PM ---");
        List<Todo> tasks = todoRepository.findByDueDateAndCompleted(LocalDate.now(), false);

        System.out.println("Tasks found for today: " + tasks.size());

        for (Todo todo : tasks) {
            System.out.println("Sending email to: " + todo.getUser().getEmail());
            emailService.sendReminder(todo.getUser().getEmail(), todo.getTitle());
        }
    }
}
