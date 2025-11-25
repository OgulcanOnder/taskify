package com.ogulcanonder.taskify.service;

import com.ogulcanonder.taskify.dto.task.DtoTaskRequest;
import com.ogulcanonder.taskify.dto.task.DtoTaskResponse;

import java.util.List;

public interface ITaskService {
    public DtoTaskResponse saveTask(DtoTaskRequest dtoTaskRequest);
    public List<DtoTaskResponse> getAllTask();
    public DtoTaskResponse findByTask(Long id);
    public DtoTaskResponse updateTask(DtoTaskRequest dtoTaskRequest,Long id);
    public String deleteTask(Long id);
}
