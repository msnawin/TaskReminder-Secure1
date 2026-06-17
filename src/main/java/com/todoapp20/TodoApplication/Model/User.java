package com.todoapp20.TodoApplication.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable { // <-- ADDED Serializable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;
}