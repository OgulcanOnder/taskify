package com.ogulcanonder.taskify.mapper;

import com.ogulcanonder.taskify.dto.user.DtoUserRequest;
import com.ogulcanonder.taskify.dto.user.DtoUserResponse;
import com.ogulcanonder.taskify.dto.user.DtoUserUpdateRequest;
import com.ogulcanonder.taskify.entities.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "taskIds", ignore = true)
    DtoUserRequest toDtoUserRequest(User user); // User -> DTO

    @Mapping(target = "tasks", ignore = true)
    User toEntityUserRequest(DtoUserRequest dtoUserRequest); // DTO -> User

    @Mapping(target = "taskIds", ignore = true)
    List<DtoUserRequest> toDtoUserListRequest(List<User> userList);

    @Mapping(target = "tasks", ignore = true)
    List<User> toEntityUserListRequest(List<DtoUserRequest> dtoUserRequestList);

    @Mapping(source = "tasks", target = "dtoTaskResponseList")
    DtoUserResponse toDtoUserResponse(User user);

    @Mapping(target = "tasks", ignore = true)
    User toEntityUserResponse(DtoUserResponse dtoUserResponse);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    List<DtoUserResponse> toDtoUserListResponse(List<User> userList);

    @Mapping(target = "tasks", ignore = true)
    List<User> toEntityUserListResponse(List<DtoUserResponse> dtoUserResponseList);

    @Mapping(target = "tasks", ignore = true)
    void updateUserFromDTO(DtoUserUpdateRequest dtoUserUpdateRequest, @MappingTarget User user);

    @BeforeMapping
    default void cleanEmptyFields(DtoUserUpdateRequest dtoUserUpdateRequest) {
        if (dtoUserUpdateRequest.getName() != null && dtoUserUpdateRequest.getName().trim().isEmpty())
            dtoUserUpdateRequest.setName(null);
        if (dtoUserUpdateRequest.getName() != null && dtoUserUpdateRequest.getName().trim().isEmpty())
            dtoUserUpdateRequest.setName(null);
        if (dtoUserUpdateRequest.getSurname() != null && dtoUserUpdateRequest.getSurname().trim().isEmpty())
            dtoUserUpdateRequest.setSurname(null);
        if (dtoUserUpdateRequest.getUsername() != null && dtoUserUpdateRequest.getUsername().trim().isEmpty())
            dtoUserUpdateRequest.setUsername(null);
        if (dtoUserUpdateRequest.getEmail() != null && dtoUserUpdateRequest.getEmail().trim().isEmpty())
            dtoUserUpdateRequest.setEmail(null);
    }
}
