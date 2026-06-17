package com.todoapp20.TodoApplication.Controller;

import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Model.User;
import com.todoapp20.TodoApplication.Repository.UserRepository;
import com.todoapp20.TodoApplication.Service.TodoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService; // <-- Now uses TodoService
    private final UserRepository userRepository;

    public TodoController(TodoService todoService, UserRepository userRepository) {
        this.todoService = todoService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Todo> getTodos() {
        String email = getAuthenticatedUserEmail();
        // Calls the cached service method
        return todoService.getTodosByUserEmail(email);
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        String email = getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        todo.setUser(user);
        // Calls the service to save and evict cache
        return todoService.createTodo(todo, email);
    }

    @PostMapping("/{id}/toggle")
    public void toggleTodo(@PathVariable Long id) {
        String email = getAuthenticatedUserEmail();
        // Calls the service to update and evict cache
        todoService.toggleTodo(id, email);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        String email = getAuthenticatedUserEmail();
        // Calls the service to delete and evict cache
        todoService.deleteTodo(id, email);
    }

    private String getAuthenticatedUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof OAuth2User oAuth2User) {
            return oAuth2User.getAttribute("email");
        }
        return auth.getName();
    }
}