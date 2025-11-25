package com.ogulcanonder.taskify.service.impl;

import com.ogulcanonder.taskify.dto.user.DtoUserRequest;
import com.ogulcanonder.taskify.dto.user.DtoUserResponse;
import com.ogulcanonder.taskify.dto.user.DtoUserUpdateRequest;
import com.ogulcanonder.taskify.entities.User;
import com.ogulcanonder.taskify.exception.ResourceNotFoundException;
import com.ogulcanonder.taskify.exception.ResourceNotUniqueExcepiton;
import com.ogulcanonder.taskify.mapper.UserMapper;
import com.ogulcanonder.taskify.repository.UserRepository;
import com.ogulcanonder.taskify.service.IUserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public DtoUserRequest saveUser(DtoUserRequest dtoUserRequest) {
        try {
            User user = userMapper.toEntityUserRequest(dtoUserRequest);
            User saveUser = userRepository.save(user);
            return userMapper.toDtoUserRequest(saveUser);
        } catch (DataIntegrityViolationException ex) {
            String message = "Duplicate resource detected";
            if (ex.getMostSpecificCause().getMessage().contains("user_username_key")) {
                message = "This Username Not Unique: " + dtoUserRequest.getEmail();
            } else if (ex.getMostSpecificCause().getMessage().contains("user_email_key")) {
                message = "This Email Not Unique: " + dtoUserRequest.getEmail();
            }
            throw new ResourceNotUniqueExcepiton(message);
        }

    }

    @Override
    public List<DtoUserResponse> getAllUser() {
        List<User> userList = userRepository.findAll();
        return userMapper.toDtoUserListResponse(userList);
    }

    @Override
    public DtoUserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found: " + username));
        return userMapper.toDtoUserResponse(user);
    }

    @Override
    @Transactional
    public DtoUserResponse updateUser(DtoUserUpdateRequest dtoUserUpdateRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found: " + username));
        userMapper.updateUserFromDTO(dtoUserUpdateRequest, user);
        User saveUser = userRepository.save(user);
        return userMapper.toDtoUserResponse(saveUser);
    }

    @Override
    public String deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found: " + username));
        userRepository.delete(user);
        return user.getSurname() + " Successfully Deleted";
    }

}
