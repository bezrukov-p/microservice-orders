package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.entity.RefreshToken;
import com.bezrukov.microserviceorders.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final Long expirationMs;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            @Value("${jwt.refreshExpiration}") Long expirationMs) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.expirationMs = expirationMs;
    }

    @Transactional
    public RefreshToken createRefreshToken(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(generateSecureToken());
        refreshToken.setUserId(userId);
        refreshToken.setExpiresAt(Date.from(
                Instant.ofEpochMilli(System.currentTimeMillis() + expirationMs)
        ).toInstant());
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    private String generateSecureToken() {
        return UUID.randomUUID().toString().replace("-", "") +
                "-" +
                Long.toString(System.currentTimeMillis(), 36);
    }

    public boolean validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken == null) {
            return false;
        }
        return !refreshToken.getRevoked() &&
                refreshToken.getExpiresAt().isAfter(Instant.now());
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken findByUserId(UUID userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteByUserId(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}