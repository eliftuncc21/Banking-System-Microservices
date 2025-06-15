package org.eliftunc.authservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.authservice.entity.RefreshToken;
import org.eliftunc.authservice.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenManager {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        return refreshTokenRepository.save(refreshToken);
    }
}
