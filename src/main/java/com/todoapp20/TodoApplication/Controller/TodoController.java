package com.todoapp20.TodoApplication.Controller;

import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Model.User;
import com.todoapp20.TodoApplication.Repository.UserRepository;
import com.todoapp20.TodoApplication.Repository.TodoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoController(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all tasks for the correctly identified logged-in user.
     */
    @GetMapping
    public List<Todo> getTodos() {
        String email = getAuthenticatedUserEmail();
        return todoRepository.findByUserEmail(email);
    }

    /**
     * Creates a task and links it to the user found by their email.
     * Fixes the NoSuchElementException by ensuring email is used for lookup.
     */
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        String email = getAuthenticatedUserEmail();

        // Find the user by the email extracted from the security context
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        todo.setUser(user);
        return todoRepository.save(todo);
    }

    /**
     * Toggles the completion status of a task.
     */
    @PostMapping("/{id}/toggle")
    public void toggleTodo(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
    }

    /**
     * Deletes a task by ID.
     */
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }

    /**
     * HELPER: Extracts the email from the Security Context.
     * Handles both standard (UsernamePasswordAuthenticationToken)
     * and OAuth2 (OAuth2AuthenticationToken) principals.
     */
    private String getAuthenticatedUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof OAuth2User oAuth2User) {
            // For Google OAuth2
            return oAuth2User.getAttribute("email");
        }

        // For Custom Email/Password Login
        return auth.getName();
    }
}