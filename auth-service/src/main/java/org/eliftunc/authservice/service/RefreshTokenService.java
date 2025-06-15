package org.eliftunc.authservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.authservice.dto.request.RefreshTokenRequestDto;
import org.eliftunc.authservice.dto.response.LoginResponseDto;
import org.eliftunc.authservice.entity.AuthUserDetails;
import org.eliftunc.authservice.entity.RefreshToken;
import org.eliftunc.authservice.jwt.JwtService;
import org.eliftunc.authservice.repository.RefreshTokenRepository;
import org.eliftunc.client.UserServiceClient;
import org.eliftunc.dto.UserResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenManager refreshTokenManager;
    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public boolean isTokenExpired(Date expiredDate){
        return expiredDate.before(new Date());
    }

    public LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest) {
        Optional<RefreshToken> optional = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken());

        if (optional.isEmpty()) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        RefreshToken refreshToken = optional.get();
        if (isTokenExpired(refreshToken.getExpiredDate())) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        UserResponseDto userDto = userServiceClient.getUserById(refreshToken.getUserId());
        AuthUserDetails authUserDetails = new AuthUserDetails(userDto, passwordEncoder);

        String accessToken = jwtService.generatedToken(authUserDetails);
        RefreshToken newRefreshToken = refreshTokenRepository.save(refreshTokenManager.createRefreshToken(refreshToken.getUserId()));

        return new LoginResponseDto(accessToken, newRefreshToken.getRefreshToken());
    }

}
