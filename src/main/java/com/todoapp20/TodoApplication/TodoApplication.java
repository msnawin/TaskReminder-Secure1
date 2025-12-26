package com.todoapp20.TodoApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class TodoApplication {

	public static void main(String[] args) {

        SpringApplication.run(TodoApplication.class, args);
        System.out.println("Spring Boot");
	}

}
