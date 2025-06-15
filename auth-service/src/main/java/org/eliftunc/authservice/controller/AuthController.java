package org.eliftunc.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eliftunc.authservice.dto.request.LoginRequestDto;
import org.eliftunc.authservice.dto.request.RefreshTokenRequestDto;
import org.eliftunc.authservice.dto.response.LoginResponseDto;
import org.eliftunc.authservice.service.AuthService;
import org.eliftunc.authservice.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/authenticate")
    public LoginResponseDto authenticate(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return authService.authenticate(loginRequestDto);
    }

    @PostMapping("/refreshToken")
    public LoginResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        return refreshTokenService.refreshToken(refreshTokenRequestDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        return authService.validateToken(authHeader);
    }
}