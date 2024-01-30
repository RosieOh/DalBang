package com.dalbang.domain.member.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JoinRequestDto {
    @NotBlank(message = "아이디는 비워둘 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 비워둘 수 없습니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인란은 비워둘 수 없습니다.")
    private String passwordCheck;

    @NotBlank(message = "닉네임은 비워둘 수 없습니다.")
    private String nickname;

    public boolean isPasswordCheck() {
        return password.equals(passwordCheck);
    }
}
