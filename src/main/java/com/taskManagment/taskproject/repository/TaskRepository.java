package com.taskManagment.taskproject.repository;

import com.taskManagment.taskproject.entity.Task;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByUsersId(long userid);
}
