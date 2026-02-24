package com.taskManagment.taskproject.serviceImpl;


import com.taskManagment.taskproject.entity.Users;
import com.taskManagment.taskproject.exception.APIException;
import com.taskManagment.taskproject.exception.TaskNotFound;
import com.taskManagment.taskproject.exception.UserNotFound;
import com.taskManagment.taskproject.payload.Taskdto;
import com.taskManagment.taskproject.repository.TaskRepository;
import com.taskManagment.taskproject.entity.Task;
import com.taskManagment.taskproject.repository.UserRepository;
import com.taskManagment.taskproject.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    public Taskdto saveTask(long userid,Taskdto taskdto)
    {
        Users user=userRepository.findById(userid).orElseThrow(
                ()->new UserNotFound(String.format("User id not found",userid))
        );
        Task task=modelMapper.map(taskdto,Task.class);
        task.setUsers(user);
        //After setting the user,we are storing in the db
        Task savedTask=taskRepository.save(task);
        return modelMapper.map(savedTask,Taskdto.class);
    }
    @Override
    public List<Taskdto> getAllTasks(long userid) {
        userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User id not found", userid))
        );

        List<Task> tasks = taskRepository.findAllByUsersId(userid);

        return tasks.stream()
                .map(task -> modelMapper.map(task, Taskdto.class))
                .collect(Collectors.toList());
    }

    public Taskdto getTask(long userid,long taskid)
    {
        Users users =userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User id not found", userid))
        );
        Task task=taskRepository.findById(taskid).orElseThrow(
                ()->new TaskNotFound(String.format("Task Id %d not found",taskid))
        );
        if(users.getId()!=task.getUsers().getId())
        {
            throw new APIException(String.format("Task Id %d is not belongs to user id %d",taskid,userid));
        }

        return modelMapper.map(task,Taskdto.class);
    }
    public void deleteTask(long userid,long taskid)
    {
        Users users =userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User id not found", userid))
        );
        Task task=taskRepository.findById(taskid).orElseThrow(
                ()->new TaskNotFound(String.format("Task Id %d not found",taskid))
        );
        if(users.getId()!=task.getUsers().getId())
        {
            throw new APIException(String.format("Task Id %d is not belongs to user id %d",taskid,userid));
        }

   taskRepository.deleteById(taskid);

    }

}