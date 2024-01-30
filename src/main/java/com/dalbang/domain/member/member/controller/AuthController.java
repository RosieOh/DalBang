package com.dalbang.domain.member.member.controller;

import com.dalbang.domain.member.member.dto.JoinRequestDto;
import com.dalbang.domain.member.member.dto.LoginRequestDto;
import com.dalbang.domain.member.member.dto.MemberResponseDto;
import com.dalbang.domain.member.member.exception.PasswordNotMatchException;
import com.dalbang.domain.member.member.service.AuthService;
import com.dalbang.global.security.jwt.dto.JwtResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> memberLogin(@Valid @RequestBody LoginRequestDto loginDto) {
        return ResponseEntity.ok(
                authService.loginMember(loginDto)
        );
    }

    /**
     * 회원가입
     */

    @GetMapping("/join")
    public String joinForm() {
        return "join"; // login.html을 렌더링
    }


    @PostMapping("/join")
    public ResponseEntity<MemberResponseDto> memberAdd(@Valid @RequestBody JoinRequestDto joinDto) {

        if (!joinDto.isPasswordCheck()) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.addMember(joinDto));
    }
}
