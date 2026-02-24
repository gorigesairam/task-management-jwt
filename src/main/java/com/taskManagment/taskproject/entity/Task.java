package com.taskManagment.taskproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor


@Entity
@Table(name="task")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;
    @Column(name="taskname",nullable = false)
    private String taskName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;



}
