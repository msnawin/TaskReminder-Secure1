package com.todoapp20.TodoApplication;

import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Repository.TodoRepository;
import com.todoapp20.TodoApplication.Service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void save_ShouldReturnSavedTodo() {
        Todo todoToSave = new Todo();
        todoToSave.setTitle("Test Task");

        Todo savedTodo = new Todo();
        savedTodo.setId(1L);
        savedTodo.setTitle("Test Task");

        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);

        Todo result = todoService.save(todoToSave);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Task", result.getTitle());
        verify(todoRepository, times(1)).save(todoToSave);
    }
}