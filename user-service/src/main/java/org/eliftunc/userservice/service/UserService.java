package org.eliftunc.userservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.events.UserCreatedEvent;
import org.eliftunc.userservice.dto.UserRequestDto;
import org.eliftunc.dto.UserResponseDto;
import org.eliftunc.userservice.entity.User;
import org.eliftunc.userservice.mapper.UserMapper;
import org.eliftunc.userservice.repository.UserProjection;
import org.eliftunc.userservice.repository.UserRepository;
import org.eliftunc.userservice.utils.Caches;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    @Cacheable(value = Caches.USER, key="#email")
    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        return userMapper.toUserResponseDto(user);
    }

    @Cacheable(value = Caches.USER, key = "#userId")
    public UserResponseDto getUserById(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        return userMapper.toUserResponseDto(user);
    }

    @Cacheable(value = Caches.USERS, key = "'all'")
    public Page<UserResponseDto> getAllUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(userMapper::toUserResponseDto);
    }

    @CacheEvict(value = Caches.USERS, allEntries = true)
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userMapper.toUser(userRequestDto, encoder);
        userRepository.save(user);

        UserCreatedEvent event = userMapper.toUserCreatedEvent(user);
        kafkaTemplate.send("user-service", event);

        return userMapper.toUserResponseDto(user);
    }

    @CachePut(value = Caches.USER, key = "#userId")
    @CacheEvict(value = Caches.USERS, allEntries = true)
    public UserResponseDto updateUser (UserRequestDto userRequestDto, Long userId){
        User user = userRepository.findById(userId).orElse(null);
        userMapper.updateUser(userRequestDto, user);
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = Caches.USERS, allEntries = true),
                    @CacheEvict(value = Caches.USER, key="#userId")
            }
    )
    public void deleteUser(Long userId){
        if (!userRepository.existsById(userId)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    @Cacheable(value = Caches.USER_BASIC, key = "#userId")
    public UserProjection findByUser(Long userId){
        return userRepository.findProjectionByUserId(userId);
    }
}
