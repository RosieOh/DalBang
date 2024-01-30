package com.dalbang.global.security.jwt.repository;

import com.dalbang.global.security.jwt.entity.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, Long> {
    Optional<JwtRefreshToken> findByMember_Id(Long id);
}
