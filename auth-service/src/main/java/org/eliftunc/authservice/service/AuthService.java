package org.eliftunc.authservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.authservice.dto.request.LoginRequestDto;
import org.eliftunc.authservice.dto.response.LoginResponseDto;
import org.eliftunc.authservice.entity.AuthUserDetails;
import org.eliftunc.authservice.entity.RefreshToken;
import org.eliftunc.authservice.jwt.JwtService;
import org.eliftunc.authservice.repository.RefreshTokenRepository;
import org.eliftunc.client.UserServiceClient;
import org.eliftunc.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserServiceClient userServiceClient;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final RefreshTokenManager refreshTokenManager;
    private final PasswordEncoder passwordEncoder;


    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {
        try{
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getEmail(), loginRequestDto.getPassword());
            authenticationProvider.authenticate(auth);

            UserResponseDto userDto = userServiceClient.getUserByEmail(loginRequestDto.getEmail());
            AuthUserDetails authUserDetails = new AuthUserDetails(userDto, passwordEncoder);

            String accessToken = jwtService.generatedToken(authUserDetails);
            RefreshToken refreshToken = refreshTokenRepository.save(refreshTokenManager.createRefreshToken(userDto.getUserId()));

            return new LoginResponseDto(accessToken, refreshToken.getRefreshToken());
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException(e);

        }
    }

    public ResponseEntity<String> validateToken(String authHeader) {

        try {
            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header missing or malformed");
            }

            String token = authHeader.replace("Bearer ", "");
            String email = jwtService.getEmail(token);
            boolean isExpired = jwtService.isTokenExpired(token);

            if (isExpired) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
            }

            UserResponseDto userDto = userServiceClient.getUserByEmail(email);
            if (userDto == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            return ResponseEntity.ok("Valid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
