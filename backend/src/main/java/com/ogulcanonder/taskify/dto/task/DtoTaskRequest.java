package com.ogulcanonder.taskify.dto.task;


import com.ogulcanonder.taskify.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


import java.util.List;

@Data
public class DtoTaskRequest {

    private Long id;
    @NotBlank(message = "Task Status cannot be empty")
    private TaskStatus taskStatus;
    @NotBlank(message = "Task Group cannot be empty")
    private String taskGroup;
    @NotBlank(message = "Task Title cannot be empty")
    private String taskTitle;
    private String taskDetail;
    private List<String> usernames;
}
