package com.todoapp20.TodoApplication.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/auth/google/login")
    public String initiateLogin(HttpSession session) {
        // Tag this session as a Login attempt
        session.setAttribute("AUTH_INTENT", "LOGIN");
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/auth/google/register")
    public String initiateRegister(HttpSession session) {
        // Tag this session as a Registration attempt
        session.setAttribute("AUTH_INTENT", "REGISTER");
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "redirect:/login.html";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "index";
    }
}
