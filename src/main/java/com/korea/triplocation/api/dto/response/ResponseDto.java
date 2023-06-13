package com.korea.triplocation.api.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ResponseDto {
    private final boolean success;
    private final int code;
    private final String message;

    public static ResponseDto ofDefault() {
        return new ResponseDto(true, 200, "요청에 성공했습니다.");
    }

    public static ResponseDto of(boolean success, int code, String message) {
        return new ResponseDto(success, code, message);
    }

}
