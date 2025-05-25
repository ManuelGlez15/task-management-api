package com.example.taskmanagementapi.service;

import com.example.taskmanagementapi.model.Task;
import com.example.taskmanagementapi.repository.TaskRepository;
import com.example.taskmanagementapi.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.example.taskmanagementapi.specification.TaskSpecification;
import java.time.LocalDate;

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
        return taskRepository.findById(id);
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id); 
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
        taskRepository.deleteById(id);
    }
}