package com.taskManagment.taskproject.controller;

import com.taskManagment.taskproject.payload.Taskdto;
import com.taskManagment.taskproject.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    // Constructor injection (recommended)
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Save the task
    @PostMapping("/{userid}/tasks")
    public ResponseEntity<Taskdto> saveTask(@PathVariable(name = "userid") long userid,
                                            @RequestBody Taskdto taskdto) {
        return new ResponseEntity<>(taskService.saveTask(userid, taskdto), HttpStatus.CREATED);
    }
//@PreAuthorize(value="ROLE_USER")
    // get all tasks

    @GetMapping("/{userid}/tasks")
    public ResponseEntity<List<Taskdto>> getAllTasks(@PathVariable(name = "userid") long userid) {
        return new ResponseEntity<>(taskService.getAllTasks(userid), HttpStatus.OK);
    }

    // get individual task
    @GetMapping("/{userid}/tasks/{taskid}")
    public ResponseEntity<Taskdto> getTask(@PathVariable(name = "userid") long userid,
                                           @PathVariable(name = "taskid") long tasksid){
        return new ResponseEntity<>(taskService.getTask(userid,tasksid),HttpStatus.OK);

    }
   // @PreAuthorize(value="ROLE_ADMIN")
    // delete individual task
    @DeleteMapping("/{userid}/tasks/{taskid}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "userid") long userid,
                                           @PathVariable(name = "taskid") long tasksid){
        taskService.deleteTask(userid,tasksid);
        return new ResponseEntity<>("Task deleted  successfully!!",HttpStatus.OK);

    }
}
