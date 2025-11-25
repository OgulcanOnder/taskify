package com.ogulcanonder.taskify.service.impl;

import com.ogulcanonder.taskify.dto.task.DtoTaskRequest;
import com.ogulcanonder.taskify.dto.task.DtoTaskResponse;
import com.ogulcanonder.taskify.entities.Task;
import com.ogulcanonder.taskify.entities.User;
import com.ogulcanonder.taskify.exception.ResourceNotFoundException;
import com.ogulcanonder.taskify.mapper.TaskMapper;
import com.ogulcanonder.taskify.repository.TaskRepository;
import com.ogulcanonder.taskify.repository.UserRepository;
import com.ogulcanonder.taskify.service.ITaskService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    @Override
    public DtoTaskResponse saveTask(DtoTaskRequest dtoTaskRequest) {
        System.out.println(dtoTaskRequest.getTaskStatus());
        Task task = taskMapper.toEntityTaskRequest(dtoTaskRequest);
        Task saveTask = taskRepository.save(task);
        List<User> users = userRepository.findByUsernameIn(dtoTaskRequest.getUsernames());
        users.forEach(user -> user.getTasks().add(saveTask));
        userRepository.saveAll(users);
        saveTask.setUserList(users);
        return taskMapper.toDtoTaskResponse(saveTask);
    }

    @Override
    public List<DtoTaskResponse> getAllTask() {
        List<Task> getAllTask = taskRepository.findAll();
        return taskMapper.toDtoTaskListResponse(getAllTask);
    }

    @Override
    public DtoTaskResponse findByTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found: " + id));
        return taskMapper.toDtoTaskResponse(task);
    }


    @Transactional
    @Override
    public DtoTaskResponse updateTask(DtoTaskRequest dtoTaskRequest, Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found: " + id));
        taskMapper.updateTaskFromDTO(dtoTaskRequest, task);
        if (dtoTaskRequest.getUsernames() != null && !dtoTaskRequest.getUsernames().isEmpty()) {
            List<User> newUserList = userRepository.findByUsernameIn(dtoTaskRequest.getUsernames());
            List<User> existingUsers = task.getUserList();
            List<User> toRemove = existingUsers.stream()
                    .filter(user -> !newUserList.contains(user))
                    .toList();
            existingUsers.removeAll(toRemove);

            List<User> toAdd = newUserList.stream()
                    .filter(user -> !existingUsers.contains(user))
                    .toList();
            existingUsers.addAll(toAdd);
            task.setUserList(existingUsers);
            for (User user : existingUsers) {
                if (!user.getTasks().contains(task)) {
                    user.getTasks().add(task);
                }
            }
        }

        Task updateTask = taskRepository.save(task);

        return taskMapper.toDtoTaskResponse(updateTask);
    }

    @Override
    public String deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found: " + id));
        List<User> userList = task.getUserList();
        for (User user : userList) {
            user.getTasks().remove(task);
        }
        taskRepository.delete(task);
        return id + " Number Text Deleted Successfully";
    }


}
