package com.example.taskmanagementapi.repository;

import com.example.taskmanagementapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    Optional<Task> findByTitle(String title);

    @Procedure("get_task_count_by_status")
    Integer getTaskCountByStatus(@Param("status_val") String status);
}