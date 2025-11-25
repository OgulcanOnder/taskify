package com.ogulcanonder.taskify.dto.task;

import com.ogulcanonder.taskify.dto.user.DtoUserResponse;
import com.ogulcanonder.taskify.enums.TaskStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DtoTaskResponse {

    private TaskStatus taskStatus;
    private String taskGroup;
    private String taskTitle;
    private String taskDetail;
    private List<DtoUserResponse> dtoUserResponseList = new ArrayList<DtoUserResponse>();
}
