package com.example.taskmanagementapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio.")
    @Size(min = 3, max = 255, message = "El título debe tener entre 3 y 255 caracteres.")
    @Column(nullable = false)
    private String title;

    @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres.")
    private String description;

    @PastOrPresent(message = "La fecha de vencimiento no puede ser futura.")
    private LocalDate dueDate;

    @NotBlank(message = "La prioridad es obligatoria.")
    private String priority;

    @NotBlank(message = "El estado es obligatorio.")
    private String status;
}