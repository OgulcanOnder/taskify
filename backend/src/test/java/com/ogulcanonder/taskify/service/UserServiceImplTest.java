package com.ogulcanonder.taskify.service;

import com.ogulcanonder.taskify.dto.user.DtoUserRequest;
import com.ogulcanonder.taskify.dto.user.DtoUserResponse;
import com.ogulcanonder.taskify.dto.user.DtoUserUpdateRequest;
import com.ogulcanonder.taskify.entities.User;
import com.ogulcanonder.taskify.exception.ResourceNotFoundException;
import com.ogulcanonder.taskify.exception.ResourceNotUniqueExcepiton;
import com.ogulcanonder.taskify.mapper.UserMapper;
import com.ogulcanonder.taskify.repository.UserRepository;
import com.ogulcanonder.taskify.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private DtoUserRequest dtoUserRequest;
    private DtoUserResponse dtoUserResponse;
    private DtoUserUpdateRequest dtoUserUpdateRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUsername("testuser");
        user.setEmail("test@email.com");
        user.setSurname("Doe");

        dtoUserRequest = new DtoUserRequest();
        dtoUserRequest.setUsername("testuser");
        dtoUserRequest.setEmail("test@email.com");

        dtoUserResponse = new DtoUserResponse();
        dtoUserResponse.setUsername("testuser");
        dtoUserResponse.setEmail("test@email.com");

        dtoUserUpdateRequest = new DtoUserUpdateRequest();
        dtoUserUpdateRequest.setEmail("new@email.com");
    }

    @Test
    void saveUser_success() {
        when(userMapper.toEntityUserRequest(dtoUserRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDtoUserRequest(user)).thenReturn(dtoUserRequest);

        DtoUserRequest result = userService.saveUser(dtoUserRequest);

        assertNotNull(result);
        assertEquals(dtoUserRequest.getUsername(), result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void saveUser_duplicateUsername_throwsException() {
        when(userMapper.toEntityUserRequest(dtoUserRequest)).thenReturn(user);
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        when(ex.getMostSpecificCause()).thenReturn(new Throwable("user_username_key"));
        doThrow(ex).when(userRepository).save(user);

        ResourceNotUniqueExcepiton exception = assertThrows(ResourceNotUniqueExcepiton.class,
                () -> userService.saveUser(dtoUserRequest));

        assertTrue(exception.getMessage().contains("Username"));
    }

    @Test
    void getAllUser_success() {
        List<User> userList = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.toDtoUserListResponse(userList)).thenReturn(Arrays.asList(dtoUserResponse));

        List<DtoUserResponse> result = userService.getAllUser();

        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }

    @Test
    void findByUsername_success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userMapper.toDtoUserResponse(user)).thenReturn(dtoUserResponse);

        DtoUserResponse result = userService.findByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void findByUsername_notFound_throwsException() {
        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findByUsername("notfound"));
    }

    @Test
    void updateUser_success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        doNothing().when(userMapper).updateUserFromDTO(dtoUserUpdateRequest, user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDtoUserResponse(user)).thenReturn(dtoUserResponse);

        DtoUserResponse result = userService.updateUser(dtoUserUpdateRequest, "testuser");

        assertNotNull(result);
        verify(userMapper, times(1)).updateUserFromDTO(dtoUserUpdateRequest, user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        String result = userService.deleteUser("testuser");

        assertEquals("Doe Successfully Deleted", result);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_notFound_throwsException() {
        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser("notfound"));
    }
}