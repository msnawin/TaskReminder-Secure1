package com.todoapp20.TodoApplication.Controller;

import com.todoapp20.TodoApplication.Model.Priority;
import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Model.User;
import com.todoapp20.TodoApplication.Repository.TodoRepository;
import com.todoapp20.TodoApplication.Repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoController(TodoRepository tr, UserRepository ur) {
        this.todoRepository = tr;
        this.userRepository = ur;
    }

    @GetMapping
    public List<Todo> getTodos(Authentication auth) {
        return todoRepository.findByUserOrderByIdDesc(getAuthUser(auth));
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo, Authentication auth) {
        todo.setUser(getAuthUser(auth));
        // Ensure a default if none provided
        if (todo.getPriority() == null) todo.setPriority(Priority.LOW);
        return todoRepository.save(todo);
    }

    @PatchMapping("/{id}")
    public void toggle(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
    }
    @PatchMapping("/{id}/priority")
    public void updatePriority(@PathVariable Long id, @RequestParam Priority priority) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setPriority(priority);
        todoRepository.save(todo);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }

    private User getAuthUser(Authentication auth) {
        OAuth2User oUser = (OAuth2User) auth.getPrincipal();
        return userRepository.findByEmail(oUser.getAttribute("email")).orElseThrow();
    }
}