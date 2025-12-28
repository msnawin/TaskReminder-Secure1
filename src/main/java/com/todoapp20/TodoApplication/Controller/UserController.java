package com.todoapp20.TodoApplication.Controller;


import com.todoapp20.TodoApplication.Model.User;
import com.todoapp20.TodoApplication.Model.AuthProvider;
import com.todoapp20.TodoApplication.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/users/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String username,
                               @RequestParam String password) {

        // 1. Check if user already exists
        if (userRepository.findByEmail(username).isPresent()) {
            return "redirect:/register.html?error=already_exists";
        }

        // 2. Create and Save new User
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(username);

        // CRITICAL: Encode the password before saving!
        newUser.setPassword(passwordEncoder.encode(password));

        newUser.setAuthProvider(AuthProvider.LOCAL); // Identify as manual reg

        userRepository.save(newUser);

        // 3. Redirect to login with success message
        return "redirect:/login.html?registered=true";
    }
}
