package org.eliftunc.userservice.mapper;

import org.eliftunc.userservice.dto.UserRequestDto;
import org.eliftunc.dto.UserResponseDto;
import org.eliftunc.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequestDto userRequestDto);

    UserResponseDto toUserResponseDto(User user);

    @Mapping(target = "userId",ignore = true)
    void updateUser(UserRequestDto userRequestDto, @MappingTarget User user);

}
