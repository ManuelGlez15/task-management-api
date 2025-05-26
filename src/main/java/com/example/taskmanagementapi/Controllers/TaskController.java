package com.example.taskmanagementapi.Controllers;

import jakarta.validation.Valid;
import com.example.taskmanagementapi.exception.TaskNotFoundException;
import com.example.taskmanagementapi.model.Task;
import com.example.taskmanagementapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy != null && !sortBy.isEmpty() ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Task> taskPage = taskService.getAllTasks(pageable, status, priority, dueDate);
        return new ResponseEntity<>(taskPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        if (updatedTask != null) {
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Nuevo endpoint para llamar al Stored Procedure
    @GetMapping("/tasks/countByStatus")
    public ResponseEntity<Integer> getTaskCountByStatus(@RequestParam String status) {
        Integer count = taskService.getTaskCountByStatus(status);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<String>> getAllUsersForAdmin() {
        // Implementa la lógica para obtener todos los usuarios (solo para admin)
        return new ResponseEntity<>(List.of("user1", "user2"), HttpStatus.OK); // Placeholder
    }

    @DeleteMapping("/admin/users/{username}")
    public ResponseEntity<Void> deleteUserByAdmin(@PathVariable String username) {
        // Implementa la lógica para eliminar un usuario por nombre de usuario (solo para admin)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Placeholder
    }

    @PutMapping("/admin/users/{username}")
    public ResponseEntity<String> editUserByAdmin(@PathVariable String username, @RequestBody String userData) {
        // Implementa la lógica para editar la información de un usuario (solo para admin)
        return new ResponseEntity<>("User updated", HttpStatus.OK); // Placeholder
    }

    @DeleteMapping("/admin/tasks/{id}")
    public ResponseEntity<Void> deleteTaskByAdmin(@PathVariable Long id) {
        // Implementa la lógica para eliminar una tarea por ID (solo para admin)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Placeholder
    }

    @PutMapping("/admin/tasks/{id}")
    public ResponseEntity<Task> editTaskByAdmin(@PathVariable Long id, @Valid @RequestBody Task task) {
        // Implementa la lógica para editar una tarea por ID (solo para admin)
        return new ResponseEntity<>(task, HttpStatus.OK); // Placeholder
    }

    @GetMapping("/admin/tasks")
    public ResponseEntity<Page<Task>> getAllTasksForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy != null && !sortBy.isEmpty() ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Task> taskPage = taskService.getAllTasks(pageable, status, priority, dueDate); // Reutiliza el método del servicio o crea uno específico para admin
        return new ResponseEntity<>(taskPage, HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> editMyProfile(@RequestBody String userData) {
        // Implementa la lógica para editar el perfil del usuario autenticado
        return new ResponseEntity<>("Profile updated", HttpStatus.OK); // Placeholder
    }
}