package com.todoapp20.TodoApplication.Config;

import com.todoapp20.TodoApplication.Model.*;
import com.todoapp20.TodoApplication.Repository.UserRepository;
import com.todoapp20.TodoApplication.Service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Optional;

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

        HttpSession session = request.getSession();
        String intent = (String) session.getAttribute("AUTH_INTENT");

        Optional<User> existingUser = userRepository.findByEmail(email);

        if ("REGISTER".equals(intent)) {
            // REGISTRATION FLOW: Create user if missing, then allow entry
            if (existingUser.isEmpty()) {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setAuthProvider(AuthProvider.GOOGLE);
                userRepository.save(newUser);
                emailService.sendWelcomeEmail(email, name);
            }
            response.sendRedirect("/dashboard");
        } else {
            // LOGIN FLOW: Strict database check
            if (existingUser.isPresent()) {
                response.sendRedirect("/dashboard");
            } else {
                request.getSession().invalidate();
                response.sendRedirect("/login.html?error=not_registered");
            }
        }
    }
}