
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

    @Scheduled(cron = "0 0 8 * * *") // Runs every day at 8 AM
    public void checkReminders() {
        LocalDate today = LocalDate.now();
        List<Todo> tasks = todoRepository.findByDueDateAndCompleted(today, false);

        for (Todo todo : tasks) {
            // FIX: Extracting the String from the User object
            String userEmail = todo.getUser().getEmail();

            // FIX: Extracting the String from the Todo object
            String taskTitle = todo.getTitle();

            // NOW THE PARAMETERS MATCH: (String, String)
            emailService.sendReminder(userEmail, taskTitle);
        }
    }
}
