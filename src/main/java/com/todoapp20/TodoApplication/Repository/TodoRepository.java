package com.todoapp20.TodoApplication.Repository;

import com.todoapp20.TodoApplication.Model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserEmail(String email);
    List<Todo> findByDueDateAndCompleted(LocalDate date, boolean completed);
}