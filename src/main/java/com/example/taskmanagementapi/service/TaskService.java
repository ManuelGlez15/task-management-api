package com.example.taskmanagementapi.service;

import com.example.taskmanagementapi.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    Page<Task> getAllTasks(Pageable pageable);
    Page<Task> getAllTasks(Pageable pageable, String status, String priority, LocalDate dueDate);
    Optional<Task> getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    Integer getTaskCountByStatus(String status);
}