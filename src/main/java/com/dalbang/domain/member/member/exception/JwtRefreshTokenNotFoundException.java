package com.dalbang.domain.member.member.exception;

public class JwtRefreshTokenNotFoundException extends RuntimeException {
    public JwtRefreshTokenNotFoundException(String message) {
        super(message);
    }
}
