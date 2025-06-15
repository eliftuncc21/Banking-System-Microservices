package org.eliftunc.userservice.mapper;

import org.eliftunc.userservice.dto.UserRequestDto;
import org.eliftunc.dto.UserResponseDto;
import org.eliftunc.userservice.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRequestDto.getPassword()))")
    User toUser(UserRequestDto userRequestDto, @Context BCryptPasswordEncoder passwordEncoder);

    UserResponseDto toUserResponseDto(User user);

    @Mapping(target = "userId",ignore = true)
    void updateUser(UserRequestDto userRequestDto, @MappingTarget User user);

}
