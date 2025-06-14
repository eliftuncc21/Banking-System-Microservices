package org.eliftunc.userservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.userservice.dto.UserRequestDto;
import org.eliftunc.userservice.dto.UserResponseDto;
import org.eliftunc.userservice.entity.User;
import org.eliftunc.userservice.mapper.UserMapper;
import org.eliftunc.userservice.repository.UserProjection;
import org.eliftunc.userservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto getUserById(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        return userMapper.toUserResponseDto(user);
    }

    public Page<UserResponseDto> getAllUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(userMapper::toUserResponseDto);
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        User user = userMapper.toUser(userRequestDto);
        userRepository.save(user);

        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto updateUser (UserRequestDto userRequestDto, Long userId){
        User user = userRepository.findById(userId).orElse(null);
        userMapper.updateUser(userRequestDto, user);
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }

    public void deleteUser(Long userId){
        if (!userRepository.existsById(userId)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    public UserProjection findByUser(Long userId){
        return userRepository.findProjectionByUserId(userId);
    }
}
