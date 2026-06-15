package com.todoapp20.TodoApplication;

import com.todoapp20.TodoApplication.Controller.UserController;
import com.todoapp20.TodoApplication.Model.User;
import com.todoapp20.TodoApplication.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void registerUser_ShouldRedirectToLogin_WhenSuccessful() throws Exception {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("secret")).thenReturn("hashedSecret");

        mockMvc.perform(post("/api/users/register")
                .param("name", "Test User")
                .param("username", "test@example.com")
                .param("password", "secret"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login.html?registered=true"));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ShouldRedirectToError_WhenEmailExists() throws Exception {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/api/users/register")
                .param("name", "Test User")
                .param("username", "test@example.com")
                .param("password", "secret"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register.html?error=already_exists"));

        verify(userRepository, never()).save(any(User.class));
    }
}