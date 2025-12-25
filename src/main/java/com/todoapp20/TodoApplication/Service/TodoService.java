package com.todoapp20.TodoApplication.Service;

import com.todoapp20.TodoApplication.Model.Todo;
import com.todoapp20.TodoApplication.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    public Todo save(Todo todo){ return todoRepository.save(todo); }
}
