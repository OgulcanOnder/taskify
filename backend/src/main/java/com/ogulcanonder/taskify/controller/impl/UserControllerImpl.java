package com.ogulcanonder.taskify.controller.impl;

import com.ogulcanonder.taskify.controller.IUserController;
import com.ogulcanonder.taskify.dto.user.DtoUserRequest;
import com.ogulcanonder.taskify.dto.user.DtoUserResponse;
import com.ogulcanonder.taskify.dto.user.DtoUserUpdateRequest;
import com.ogulcanonder.taskify.exception.ResourceNotFoundException;
import com.ogulcanonder.taskify.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/taskify/user")
public class UserControllerImpl implements IUserController {

    private final IUserService userService;

    public UserControllerImpl(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    @Override
    public ResponseEntity<DtoUserRequest> saveUser(@Valid @RequestBody DtoUserRequest dtoUserRequest) {
        DtoUserRequest savedUser = userService.saveUser(dtoUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/list")
    @Override
    public ResponseEntity<List<DtoUserResponse>> getAllUser() {
        List<DtoUserResponse> dtoUserResponseList = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(dtoUserResponseList);
    }

    @GetMapping("/{username}")
    @Override
    public ResponseEntity<DtoUserResponse> findByUsername(@PathVariable(name = "username") String username) {
        DtoUserResponse dtoUserResponse = userService.findByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(dtoUserResponse);
    }

    @PutMapping("/update/{username}")
    @Override
    public ResponseEntity<DtoUserResponse> updateUser(@Valid @RequestBody DtoUserUpdateRequest dtoUserUpdateRequest,
                                                      @PathVariable(name = "username") String username) {
        DtoUserResponse dtoUserResponse = userService.updateUser(dtoUserUpdateRequest, username);
        return ResponseEntity.status(HttpStatus.OK).body(dtoUserResponse);
    }

    @DeleteMapping("/delete/{username}")
    @Override
    public ResponseEntity<String> deleteUser(@PathVariable(name = "username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(username + " Successfully Deleted");
    }
}
