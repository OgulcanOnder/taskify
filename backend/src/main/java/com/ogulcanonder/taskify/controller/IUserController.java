package com.ogulcanonder.taskify.controller;

import com.ogulcanonder.taskify.dto.user.DtoUserRequest;
import com.ogulcanonder.taskify.dto.user.DtoUserResponse;
import com.ogulcanonder.taskify.dto.user.DtoUserUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserController {
    public ResponseEntity<DtoUserRequest>saveUser(DtoUserRequest dtoUserRequest);
    public ResponseEntity<List<DtoUserResponse>>getAllUser();
    public ResponseEntity<DtoUserResponse> findByUsername(String username);
    public ResponseEntity<DtoUserResponse> updateUser(DtoUserUpdateRequest dtoUserUpdateRequest, String username);
    public ResponseEntity<String>deleteUser(String username);
}
