

import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Repository.TodoRepository;
import com.todoapp20.TodoApplication.Service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class TaskScheduler {
    private final TodoRepository todoRepository;
    private final EmailService emailService;
    public TaskScheduler(TodoRepository todoRepository, EmailService emailService) {
        this.todoRepository = todoRepository;
        this.emailService = emailService;
    }
    @Scheduled(cron = "0 0 8 * * *")
    public void checkReminders() {
        List<Todo> tasks = todoRepository.findByDueDateAndCompleted(LocalDate.now(), false);
        for (Todo todo : tasks) {
            emailService.sendReminder(todo.getUser().getEmail(), todo.getTitle());
        }
    }
}
