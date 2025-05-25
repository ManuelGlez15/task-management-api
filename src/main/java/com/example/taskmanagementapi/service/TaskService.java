package com.example.taskmanagementapi.service;

import com.example.taskmanagementapi.model.Task;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

public interface TaskService {
    List<Task> getAllTasks();
    Page<Task> getAllTasks(Pageable pageable);
    Page<Task> getAllTasks(Pageable pageable, String status, String priority, LocalDate dueDate); // Nuevo método para el filtrado
    Optional<Task> getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
}