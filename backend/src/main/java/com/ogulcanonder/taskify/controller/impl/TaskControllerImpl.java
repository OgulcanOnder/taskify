package com.ogulcanonder.taskify.controller.impl;

import com.ogulcanonder.taskify.controller.ITaskController;
import com.ogulcanonder.taskify.dto.task.DtoTaskRequest;
import com.ogulcanonder.taskify.dto.task.DtoTaskResponse;
import com.ogulcanonder.taskify.service.ITaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/taskify/task")
public class TaskControllerImpl implements ITaskController {

    private final ITaskService taskService;

    public TaskControllerImpl(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/save")
    @Override
    public ResponseEntity<DtoTaskResponse> saveTask(@RequestBody DtoTaskRequest dtoTaskRequest) {
        DtoTaskResponse saveTask=taskService.saveTask(dtoTaskRequest);
        return ResponseEntity.status(HttpStatus.OK).body(saveTask);
    }

    @GetMapping("/list")
    @Override
    public ResponseEntity<List<DtoTaskResponse>> getAllTask() {
        List<DtoTaskResponse> getAllTask=taskService.getAllTask();
        return ResponseEntity.status(HttpStatus.OK).body(getAllTask);
    }

    @GetMapping("{id}")
    @Override
    public ResponseEntity<DtoTaskResponse> findByTask(@PathVariable Long id){
        DtoTaskResponse dtoTaskResponse=taskService.findByTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(dtoTaskResponse);
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<DtoTaskResponse> updateTask(@RequestBody DtoTaskRequest dtoTaskRequest,@PathVariable Long id) {
        DtoTaskResponse dtoTaskResponse=taskService.updateTask(dtoTaskRequest,id);
        return ResponseEntity.status(HttpStatus.OK).body(dtoTaskResponse);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(id+" Number Task Deleted Successfully");
    }


}
