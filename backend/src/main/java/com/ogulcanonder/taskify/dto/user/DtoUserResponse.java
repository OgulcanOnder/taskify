package com.ogulcanonder.taskify.dto.user;

import com.ogulcanonder.taskify.dto.task.DtoTaskResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DtoUserResponse {

    private String name;
    private String surname;
    private String username;
    private String email;
    private List<DtoTaskResponse>dtoTaskResponseList=new ArrayList<DtoTaskResponse>();
}
