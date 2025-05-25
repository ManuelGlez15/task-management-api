package com.example.taskmanagementapi.specification;

import com.example.taskmanagementapi.model.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class TaskSpecification implements Specification<Task> {

    private final String status;
    private final String priority;
    private final LocalDate dueDate;

    public TaskSpecification(String status, String priority, LocalDate dueDate) {
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction(); // Inicialmente, no hay filtros (AND)

        if (status != null && !status.isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
        }

        if (priority != null && !priority.isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("priority"), priority));
        }

        if (dueDate != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dueDate"), dueDate));
        }

        return predicate;
    }
}