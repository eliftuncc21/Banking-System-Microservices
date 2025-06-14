package org.eliftunc.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eliftunc.userservice.dto.UserRequestDto;
import org.eliftunc.dto.UserResponseDto;
import org.eliftunc.userservice.repository.UserProjection;
import org.eliftunc.userservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/create-user")
    public UserResponseDto createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @GetMapping("/email/{email}")
    public UserResponseDto getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/list-user/{page}/{size}")
    public Page<UserResponseDto> getUsers(@PathVariable int page, @PathVariable int size) {
        return userService.getAllUsers(page, size);
    }

    @PutMapping("/update-user/{id}")
    public UserResponseDto updateUser(@RequestBody @Valid UserRequestDto userRequestDto, @PathVariable Long id) {
        return userService.updateUser(userRequestDto, id);
    }

    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/user/{id}")
    public UserProjection findUserById(@PathVariable Long id) {
        return userService.findByUser(id);
    }
}