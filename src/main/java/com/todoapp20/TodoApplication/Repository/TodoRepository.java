package com.todoapp20.TodoApplication.Repository;

import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // Fix: Added this to support fetching a specific user's tasks
    List<Todo> findByUser(User user);

    // Added this for the Reminder Scheduler
    List<Todo> findByDueDateAndCompleted(LocalDate dueDate, boolean completed);

    // Useful for showing newest tasks first on the UI
    List<Todo> findByUserOrderByIdDesc(User user);
}