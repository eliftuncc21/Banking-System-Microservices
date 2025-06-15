package org.eliftunc.client;

import org.eliftunc.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserServiceClient {
    @GetMapping("/api/users/{id}")
    UserResponseDto getUserById(@PathVariable("id") Long id);

    @GetMapping("/api/users/email/{email}")
    UserResponseDto getUserByEmail(@PathVariable("email") String email);
}