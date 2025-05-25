package com.example.taskmanagementapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Opcional: Marca la excepción con un código de estado HTTP por defecto
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(Long taskId) {
        super("No se encontró la tarea con ID: " + taskId);
    }
}