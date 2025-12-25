package com.todoapp20.TodoApplication.Controller;

import com.todoapp20.TodoApplication.Model.*;
import com.todoapp20.TodoApplication.Repository.UserRepository;
import com.todoapp20.TodoApplication.Service.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    public AuthController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository; this.passwordEncoder = passwordEncoder; this.emailService = emailService;
    }
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) return "Email exists";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthProvider(AuthProvider.LOCAL);
        userRepository.save(user);
        emailService.sendWelcomeEmail(user.getEmail(), user.getName());
        return "Success";
    }
}
