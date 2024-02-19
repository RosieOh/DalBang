package com.dalbang.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //서버
    INTERNAL_SERVER_ERROR("IN001", "예기치 못한 오류가 발생했습니다."),

    //공용
    INVALID_INPUT_VALUE("C001", "잘못된 값을 입력하셨습니다."),
    INVALID_LAST_PAGE_UPDATED_AT_FORMAT("C002", "유효하지 않은 이전 페이지의 업데이트 날짜 형식입니다."),
    INVALID_SORT_TYPE("COO3", "유효하지 않은 정렬 타입 입니다."),
    INVALID_LAST_PAGE_EXPIRATION_DATE_FORMAT("C004", "유효하지 않은 이전 페이지의 날짜 형식입니다."),
    INVALID_LAST_PAGE_CREATED_AT_FORMAT("C005", "유효하지 않은 이전 페이지의 생성 날짜 형식입니다."),

    //회원
    ALREADY_EXIST_USERNAME("M001", "이미 존재하는 아이디입니다."),
    LOGIN_FAILURE("M002", "로그인에 실패했습니다."),
    NOT_FOUND_MEMBER("M003", "회원을 찾을 수 없습니다."),
    WRONG_PASSWORD("M004", "비밀번호가 일치하지 않습니다."),

    //인증, 인가
    INVALID_TOKEN("A001", "토큰의 유효성(형식, 서명 등)이 올바르지 않습니다."),
    EXPIRED_TOKEN("A002", "토큰이 만료되었습니다."),
    BAD_CREDENTIALS("A003", "아이디 또는 비밀번호가 일치하지 않습니다."),
    DISABLE_ACCOUNT("A004", "계정이 비활성화 되었습니다."),
    CREDENTIALS_EXPIRED("A005", "계정 유효기간이 만료 되었습니다."),
    NOT_FOUND_ACCOUNT("A006", "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요."),
    NOT_FOUND_AUTHENTICATION_CREDENTIALS("A007", "인증 요청이 거부되었습니다."),

    //게시글
    NOT_FOUND_POST("P001", "게시글이 존재하지 않습니다."),

    //댓글
    NOT_FOUND_COMMENT("CM001", "댓글이 존재하지 않습니다."),

    //이미지
    NOT_FOUND_IMAGE("I001", "이미지가 존재하지 않습니다."),
    EMPTY_IMAGE("I002", "이미지가 비어있습니다."),
    INVALID_IMAGE_FORMAT("I003", "지원하지 않는 이미지 파일 형식입니다."),
    EXCESSIVE_DELETE_IMAGE_COUNT("I004", "이미지는 1개씩 삭제 가능합니다."),

    //알림
    INVALID_NOTIFICATION_TYPE("N001", "유효하지 않은 알림 타입입니다.");

    private final String code;
    private final String message;
}
