package com.todoapp20.TodoApplication;

import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Model.User;
import com.todoapp20.TodoApplication.Repository.TodoRepository;
import com.todoapp20.TodoApplication.Service.EmailReminderScheduler;
import com.todoapp20.TodoApplication.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailReminderSchedulerTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailReminderScheduler emailReminderScheduler;

    @Test
    void checkReminders_ShouldSendEmailsForIncompleteTasksToday() {
        User user = new User();
        user.setEmail("user@test.com");

        Todo task1 = new Todo();
        task1.setTitle("Task 1");
        task1.setUser(user);

        Todo task2 = new Todo();
        task2.setTitle("Task 2");
        task2.setUser(user);

        List<Todo> mockTasks = Arrays.asList(task1, task2);

        when(todoRepository.findByDueDateAndCompleted(LocalDate.now(), false)).thenReturn(mockTasks);

        emailReminderScheduler.checkReminders();

        verify(todoRepository, times(1)).findByDueDateAndCompleted(LocalDate.now(), false);
        verify(emailService, times(1)).sendReminder("user@test.com", "Task 1");
        verify(emailService, times(1)).sendReminder("user@test.com", "Task 2");
    }
}