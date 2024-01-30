package com.dalbang.domain.member.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public MemberResponseDto(MemberDto dto) {
        this.id = dto.getId();
        this.email = dto.getEmail();
        this.nickname = dto.getNickname();
        this.createdDate = dto.getCreatedDate();
        this.updatedDate = dto.getUpdatedDate();
    }
}
