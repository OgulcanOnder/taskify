package com.ogulcanonder.taskify.mapper;

import com.ogulcanonder.taskify.dto.task.DtoTaskRequest;
import com.ogulcanonder.taskify.dto.task.DtoTaskResponse;
import com.ogulcanonder.taskify.entities.Task;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {

    @Mapping(source = "userList", target = "dtoUserResponseList")
    @Mapping(source = "taskStatus",target = "taskStatus")
    DtoTaskResponse toDtoTaskResponse(Task task);

    List<DtoTaskResponse> toDtoTaskListResponse(List<Task> taskList);

    @Mapping(target = "userList", ignore = true)
    @Mapping(source = "taskStatus",target = "taskStatus")
    Task toEntityTaskRequest(DtoTaskRequest dtoTaskRequest);

    List<Task> toEntityTaskListRequest(List<DtoTaskRequest> dtoTaskRequestList);

    @Mapping(target = "usernames", source = "userList", ignore = true)
    DtoTaskRequest toDtoTaskRequest(Task task);

    void updateTaskFromDTO(DtoTaskRequest dtoTaskRequest, @MappingTarget Task task);

    @BeforeMapping
    default void cleanEmptyFields(DtoTaskRequest dtoTaskRequest) {

        if (dtoTaskRequest.getTaskGroup() != null && dtoTaskRequest.getTaskGroup().trim().isEmpty())
            dtoTaskRequest.setTaskGroup(null);

        if (dtoTaskRequest.getTaskTitle() != null && dtoTaskRequest.getTaskTitle().trim().isEmpty())
            dtoTaskRequest.setTaskTitle(null);

        if (dtoTaskRequest.getTaskDetail() != null && dtoTaskRequest.getTaskDetail().trim().isEmpty())
            dtoTaskRequest.setTaskDetail(null);

        if (dtoTaskRequest.getUsernames() != null && dtoTaskRequest.getUsernames().isEmpty())
            dtoTaskRequest.setUsernames(null);

    }

}
