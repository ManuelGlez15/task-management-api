package com.example.taskmanagementapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagementApiApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        //String databaseUrl = dotenv.get("DATABASE_URL");
        //String databaseUsername = dotenv.get("DATABASE_USERNAME");
        //String databasePassword = dotenv.get("DATABASE_PASSWORD");
        //System.out.println("Database URL: " + databaseUrl);

        SpringApplication.run(TaskManagementApiApplication.class, args);
    }
}