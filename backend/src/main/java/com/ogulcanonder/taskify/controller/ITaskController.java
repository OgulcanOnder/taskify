package com.ogulcanonder.taskify.controller;

import com.ogulcanonder.taskify.dto.task.DtoTaskRequest;
import com.ogulcanonder.taskify.dto.task.DtoTaskResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskController {
    public ResponseEntity<DtoTaskResponse> saveTask(DtoTaskRequest dtoTaskRequest);
    public ResponseEntity<List<DtoTaskResponse>> getAllTask();
    public ResponseEntity<DtoTaskResponse> findByTask(Long id);
    public ResponseEntity<DtoTaskResponse> updateTask(DtoTaskRequest dtoTaskRequest,Long id);
    public ResponseEntity<String>deleteTask(Long id);
}
