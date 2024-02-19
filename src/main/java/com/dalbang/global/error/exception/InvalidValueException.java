package com.dalbang.global.error.exception;

import com.dalbang.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidValueException extends RuntimeException{

    private final ErrorCode errorCode;
}
