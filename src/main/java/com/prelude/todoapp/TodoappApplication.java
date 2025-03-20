package com.prelude.todoapp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TodoappApplication {

    public static void main(String[] args) {
        // Load biến môi trường từ .env
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // Hoặc absolute path: "D:/droppii/todoapp"
                .load();

        SpringApplication.run(TodoappApplication.class, args);
    }
}
