package com.ogulcanonder.taskify.dto.user;

import com.ogulcanonder.taskify.dto.task.DtoTaskRequest;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DtoUserUpdateRequest {

    private String name;
    private String surname;
    private String username;
    @Email(message = "Enter a valid email address")
    private String email;
    private List<Long> taskIds;
}
