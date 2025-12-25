package com.todoapp20.TodoApplication.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(Authentication authentication) {
        // If the user is already authenticated, send them to the task page
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        // If not logged in, go to login.html (stored in static)
        return "redirect:/login.html";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        // This serves 'index.html' from src/main/resources/templates
        // DO NOT add .html here. Thymeleaf handles the extension.
        return "index";
    }
}
