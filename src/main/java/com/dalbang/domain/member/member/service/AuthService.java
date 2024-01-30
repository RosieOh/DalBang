package com.dalbang.domain.member.member.service;

import com.dalbang.domain.member.member.dto.JoinRequestDto;
import com.dalbang.domain.member.member.dto.LoginRequestDto;
import com.dalbang.domain.member.member.dto.MemberDto;
import com.dalbang.domain.member.member.entity.Member;
import com.dalbang.domain.member.member.dto.MemberResponseDto;
import com.dalbang.domain.member.member.exception.InvalidTokenException;
import com.dalbang.domain.member.member.exception.UserNotFoundException;
import com.dalbang.domain.member.member.repository.MemberRepository;
import com.dalbang.global.security.jwt.JwtTokenProvider;
import com.dalbang.global.security.jwt.dto.JwtResponse;
import com.dalbang.global.security.jwt.entity.JwtRefreshToken;
import com.dalbang.global.security.jwt.repository.JwtRefreshTokenRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtRefreshTokenRepository refreshTokenRepository;

    /**
     * 회원가입
     */

    @Transactional
    public MemberResponseDto addMember(final JoinRequestDto joinDto) {
        MemberDto memberDto = MemberDto.builder()
                .email(joinDto.getEmail())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .nickname(joinDto.getNickname())
                .build();

        Member memberEntity = memberRepository.save(MemberDto.toEntity(memberDto));

        return new MemberResponseDto(new MemberDto(memberEntity));
    }

    /**
     * 로그인
     */
    @Transactional
    public JwtResponse loginMember(final LoginRequestDto loginDto) {
        Member memberEntity = Member.builder()
                .email(loginDto.getEmail())
                .password(loginDto.getPassword())
                .build();

        Authentication authentication = authenticateMember(memberEntity);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtResponse jwtResponse = jwtTokenProvider.createAccessToken(authentication);
        JwtRefreshToken refreshToken = checkAndCreateRefreshToken(authentication);

        return buildJwtResponse(refreshToken, refreshToken.getMember(), jwtResponse);
    }

    private Authentication authenticateMember(final Member member) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        member.getEmail(), member.getPassword()
                )
        );
    }

    // 리프레시 토큰이 있는지 확인, 없으면 생성
    @Transactional
    public JwtRefreshToken checkAndCreateRefreshToken(final Authentication authentication) {
        // 사용자 조회
        Member authenticateMember = findMember(authentication.getName());

        // 리프레시 토큰 조회
        JwtRefreshToken refreshToken = findRefreshToken(authenticateMember);

        // 리프레시 토큰 검증 및 생성
        refreshToken = validateAndCreateRefreshToken(authentication, authenticateMember, refreshToken);

        return refreshToken;
    }

    // 새로운 액세스 토큰 발급
    public JwtResponse newAccessToken(final String requestRefreshToken) {
        validateRefreshToken(requestRefreshToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(requestRefreshToken);
        Member member = findMember(authentication.getName());
        JwtRefreshToken refreshToken = findRefreshToken(member);

        validateTokenMatch(requestRefreshToken, refreshToken);

        JwtResponse jwtResponse = jwtTokenProvider.createAccessToken(authentication);

        return buildJwtResponse(refreshToken, member, jwtResponse);
    }

    private void validateRefreshToken(final String requestRefreshToken) {
        if (!jwtTokenProvider.validateToken(requestRefreshToken)) {
            throw new InvalidTokenException("Refresh Token 검증 실패");
        }
    }

    private JwtRefreshToken findRefreshToken(final Member authenticateMember) {
        return refreshTokenRepository.findByMember_Id(authenticateMember.getId()).orElse(null);
    }

    private void validateTokenMatch(final String requestRefreshToken, JwtRefreshToken refreshToken) {
        if (!refreshToken.getRefreshToken().equals(requestRefreshToken)) {
            throw new InvalidTokenException("토큰이 일치하지 않습니다.");
        }
    }

    private JwtRefreshToken validateAndCreateRefreshToken(final Authentication authentication,
                                                          final Member authenticateMember,
                                                          JwtRefreshToken refreshToken) {
        if (isTokenInvalid(refreshToken)) {
            refreshToken = recreateAndSaveRefreshToken(authentication, authenticateMember);
        }

        return refreshToken;
    }

    private boolean isTokenInvalid(final JwtRefreshToken refreshToken) {
        return refreshToken == null || !jwtTokenProvider.validateToken(refreshToken.getRefreshToken());
    }

    private JwtRefreshToken recreateAndSaveRefreshToken(final Authentication authentication,
                                                        final Member authenticateMember) {
        log.info("유효하지 않을 때");

        JwtRefreshToken newRefreshToken = jwtTokenProvider.createRefreshToken(authentication);
        return refreshTokenRepository.save(newRefreshToken.toBuilder().member(authenticateMember).build());
    }

    private JwtResponse buildJwtResponse(final JwtRefreshToken refreshToken,
                                         final Member member,
                                         final JwtResponse jwtResponse) {
        return jwtResponse.toBuilder()
                .refreshToken(refreshToken.getRefreshToken())
                .member(new MemberResponseDto(new MemberDto(member)))
                .build();
    }

    private Member findMember(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없어요."));
    }
}
