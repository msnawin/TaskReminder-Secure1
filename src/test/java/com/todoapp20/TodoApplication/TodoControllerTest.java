package com.todoapp20.TodoApplication.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Model.User;
import com.todoapp20.TodoApplication.Repository.TodoRepository;
import com.todoapp20.TodoApplication.Repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TodoController todoController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();

        // Mock Security Context - ADDED lenient() TO FIX STRICTNESS ERROR
        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.isAuthenticated()).thenReturn(true);
        lenient().when(authentication.getName()).thenReturn("test@example.com");
        lenient().when(authentication.getPrincipal()).thenReturn("StandardUser");
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getTodos_ShouldReturnListOfTodos() throws Exception {
        Todo t1 = new Todo(); t1.setTitle("Task 1");
        Todo t2 = new Todo(); t2.setTitle("Task 2");
        when(todoRepository.findByUserEmail("test@example.com")).thenReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"));
    }

    @Test
    void createTodo_ShouldSaveAndReturnTodo() throws Exception {
        User mockUser = new User();
        mockUser.setEmail("test@example.com");

        Todo newTodo = new Todo();
        newTodo.setTitle("New Task");

        Todo savedTodo = new Todo();
        savedTodo.setId(1L);
        savedTodo.setTitle("New Task");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void toggleTodo_ShouldFlipCompletedStatus() throws Exception {
        Todo existingTodo = new Todo();
        existingTodo.setId(1L);
        existingTodo.setCompleted(false);

        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));

        mockMvc.perform(post("/api/todos/1/toggle"))
                .andExpect(status().isOk());

        verify(todoRepository, times(1)).save(argThat(Todo::isCompleted));
    }

    @Test
    void deleteTodo_ShouldCallRepositoryDelete() throws Exception {
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isOk());

        verify(todoRepository, times(1)).deleteById(1L);
    }
}