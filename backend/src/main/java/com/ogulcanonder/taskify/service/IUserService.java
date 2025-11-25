package com.ogulcanonder.taskify.service;

import com.ogulcanonder.taskify.dto.user.DtoUserRequest;
import com.ogulcanonder.taskify.dto.user.DtoUserResponse;
import com.ogulcanonder.taskify.dto.user.DtoUserUpdateRequest;

import java.util.List;

public interface IUserService {
    public DtoUserRequest saveUser(DtoUserRequest dtoUserRequest);
    public List<DtoUserResponse>getAllUser();
    public DtoUserResponse findByUsername(String username);
    public DtoUserResponse updateUser(DtoUserUpdateRequest dtoUserUpdateRequest, String username);
    public String deleteUser(String username);
}
