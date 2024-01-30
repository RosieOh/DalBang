package com.dalbang.domain.member.member.dto;

import com.dalbang.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public MemberDto(final Member entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.nickname = entity.getNickname();
        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
    }

    public static Member toEntity(final MemberDto dto) {
        return Member.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .createdDate(dto.getCreatedDate())
                .updatedDate(dto.getUpdatedDate())
                .build();
    }
}
