package com.example.taskmanagementapi.service;

import com.example.taskmanagementapi.model.Task;
import com.example.taskmanagementapi.repository.TaskRepository;
import com.example.taskmanagementapi.exception.TaskNotFoundException;
import com.example.taskmanagementapi.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<Task> getAllTasks(Pageable pageable, String status, String priority, LocalDate dueDate) {
        Specification<Task> spec = new TaskSpecification(status, priority, dueDate);
        return taskRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return taskRepository.findById(id)
                .filter(task -> task.getOwner().equals(currentUsername));
    }

    @Override
    public Task createTask(Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        task.setOwner(currentUsername);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<Task> existingTaskOptional = taskRepository.findById(id)
                .filter(t -> t.getOwner().equals(currentUsername)); // Solo busca tareas del usuario actual
        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setDueDate(task.getDueDate());
            existingTask.setPriority(task.getPriority());
            existingTask.setStatus(task.getStatus());
            return taskRepository.save(existingTask);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    @Override
    public void deleteTask(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<Task> taskToDeleteOptional = taskRepository.findById(id)
                .filter(t -> t.getOwner().equals(currentUsername)); // Solo permite eliminar tareas del usuario actual
        if (taskToDeleteOptional.isPresent()) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    // Nuevo método para obtener el conteo de tareas por estado usando el Stored Procedure
    public Integer getTaskCountByStatus(String status) {
        return taskRepository.getTaskCountByStatus(status);
    }
}