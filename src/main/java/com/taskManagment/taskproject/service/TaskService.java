package com.taskManagment.taskproject.service;
import com.taskManagment.taskproject.payload.Taskdto;

import java.util.List;

public interface TaskService {
    Taskdto saveTask(long userId, Taskdto taskDto);
    List<Taskdto> getAllTasks(long userId);
    public Taskdto getTask(long userid,long taskid);
    public void deleteTask(long userid,long taskid);
}
