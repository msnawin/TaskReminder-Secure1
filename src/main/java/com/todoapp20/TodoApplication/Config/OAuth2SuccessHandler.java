package com.todoapp20.TodoApplication.Config;

import com.todoapp20.TodoApplication.Model.*;
import com.todoapp20.TodoApplication.Repository.UserRepository;
import com.todoapp20.TodoApplication.Service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public OAuth2SuccessHandler(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException {
        OAuth2User oUser = (OAuth2User) auth.getPrincipal();
        String email = oUser.getAttribute("email");
        String name = oUser.getAttribute("name");

        // Logic: If user doesn't exist, this IS a "Sign-Up" event.
        if (userRepository.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(null); // No password for OAuth users
            user.setAuthProvider(AuthProvider.GOOGLE);
            userRepository.save(user);

            // Send welcome email to new Google sign-ups
            emailService.sendWelcomeEmail(email, name);
        }

        // Redirect to dashboard (works for both existing and new users)
        response.sendRedirect("/");
    }
}