package com.bezrukov.microserviceorders.repository;

import com.bezrukov.microserviceorders.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    RefreshToken findByToken(String token);

    void deleteByUserId(UUID userId);

    RefreshToken findByUserId(UUID userId);
}
