package com.ogulcanonder.taskify.dto.user;

import com.ogulcanonder.taskify.dto.task.DtoTaskRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DtoUserRequest {

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Enter a valid email address")
    private String email;
    private List<Long> taskIds;
}
