package com.ogulcanonder.taskify.service;

import com.ogulcanonder.taskify.dto.task.DtoTaskRequest;
import com.ogulcanonder.taskify.dto.task.DtoTaskResponse;
import com.ogulcanonder.taskify.entities.Task;
import com.ogulcanonder.taskify.entities.User;
import com.ogulcanonder.taskify.enums.TaskStatus;
import com.ogulcanonder.taskify.exception.ResourceNotFoundException;
import com.ogulcanonder.taskify.mapper.TaskMapper;
import com.ogulcanonder.taskify.repository.TaskRepository;
import com.ogulcanonder.taskify.repository.UserRepository;
import com.ogulcanonder.taskify.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTask_ShouldSaveTaskAndAssignUsers() {
        DtoTaskRequest request = new DtoTaskRequest();
        request.setTaskStatus(TaskStatus.valueOf("DONE"));
        request.setTaskGroup("Accounting");
        request.setTaskTitle("Years End Meeting");
        request.setTaskDetail("Years end meeting");
        request.setUsernames(Arrays.asList("miaonder", "deneme2", "deneme3"));

        // Mapper mock
        when(taskMapper.toEntityTaskRequest(any(DtoTaskRequest.class))).thenAnswer(invocation -> {
            DtoTaskRequest dto = invocation.getArgument(0);
            Task task = new Task();
            task.setTaskStatus(TaskStatus.valueOf("DONE")); // TaskStatus
            task.setTaskTitle(dto.getTaskTitle());
            task.setTaskDetail(dto.getTaskDetail());
            task.setTaskGroup(dto.getTaskGroup());
            task.setUserList(new ArrayList<>());
            return task;
        });

        // Repository save mock
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        // Kullanıcılar
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        List<User> users = Arrays.asList(user1, user2, user3);
        when(userRepository.findByUsernameIn(request.getUsernames())).thenReturn(users);

        when(taskMapper.toDtoTaskResponse(any(Task.class))).thenReturn(new DtoTaskResponse());

        // Service'i tek sefer çalıştır
        DtoTaskResponse result = taskService.saveTask(request);

        // Kaydedilen task nesnesini yakala
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();

        // Kullanıcıların task listesine eklenip eklenmediğini kontrol et
        for (User user : users) {
            assertTrue(user.getTasks().contains(savedTask));
        }

        // Mapper sonucu kontrol
        assertNotNull(result);

        // Repository doğrulamaları
        verify(userRepository).findByUsernameIn(request.getUsernames());
        verify(userRepository).saveAll(users);
    }

    @Test
    void getAllTask_ShouldReturnAllTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        List<DtoTaskResponse> responses = Arrays.asList(new DtoTaskResponse(), new DtoTaskResponse());

        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.toDtoTaskListResponse(tasks)).thenReturn(responses);

        List<DtoTaskResponse> result = taskService.getAllTask();

        verify(taskRepository).findAll();
        assertEquals(responses, result);
    }

    @Test
    void findByTask_ShouldReturnTask_WhenExists() {
        Task task = new Task();
        DtoTaskResponse response = new DtoTaskResponse();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDtoTaskResponse(task)).thenReturn(response);

        DtoTaskResponse result = taskService.findByTask(1L);

        assertEquals(response, result);
    }

    @Test
    void findByTask_ShouldThrow_WhenNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.findByTask(1L));
    }

    @Test
    void deleteTask_ShouldRemoveTaskFromUsersAndDelete() {
        Task task = new Task();
        User user1 = new User();
        User user2 = new User();
        task.setUserList(Arrays.asList(user1, user2));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        String result = taskService.deleteTask(1L);

        verify(taskRepository).delete(task);
        assertTrue(user1.getTasks().isEmpty());
        assertTrue(user2.getTasks().isEmpty());
        assertEquals("1 Number Text Deleted Successfully", result);
    }
}