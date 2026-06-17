package com.todoapp20.TodoApplication.Service;

import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    // 1. FETCH: Check Redis first. If MISS, hit MySQL.
    // The cache key is the user's email, so every user gets their own private cache list!
    @Cacheable(value = "userTodos", key = "#email", sync = true)
    public List<Todo> getTodosByUserEmail(String email) {
        System.out.println("Cache MISS! Fetching from MySQL for user: " + email);
        return todoRepository.findByUserEmail(email);
    }

    // 2. CREATE: Wipes the cache for this specific user so they get fresh data next time
    @CacheEvict(value = "userTodos", key = "#email")
    public Todo createTodo(Todo todo, String email) {
        return todoRepository.save(todo);
    }

    // 3. UPDATE: Wipes the cache when a task is checked/unchecked
    @CacheEvict(value = "userTodos", key = "#email")
    public void toggleTodo(Long id, String email) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
    }

    // 4. DELETE: Wipes the cache when a task is deleted
    @CacheEvict(value = "userTodos", key = "#email")
    public void deleteTodo(Long id, String email) {
        todoRepository.deleteById(id);
    }
}
