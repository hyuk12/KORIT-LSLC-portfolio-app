package com.korea.triplocation.api.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter @Setter
public class ErrorRespDto<T> extends ResponseDto{

    Map<String, String> errorData;

    public ErrorRespDto(HttpStatus status) {
        super(false, status.value(), "Failed");
    }

    public ErrorRespDto(HttpStatus status, Exception e) {
        super(false, status.value(), e.getMessage());
    }

    private ErrorRespDto(HttpStatus status, Exception e,  Map<String, String> errorData) {
        super(false, status.value(), e.getMessage());
        this.errorData = errorData;
    }

    public static <T> ErrorRespDto<T> of(HttpStatus status) {
        return new ErrorRespDto<>(status);
    }

    public static <T> ErrorRespDto<T> of(HttpStatus status, Exception e) {
        return new ErrorRespDto<>(status, e);
    }

    public static  ErrorRespDto of(HttpStatus status, Exception e, Map<String, String> errorData) {
        return new ErrorRespDto(status, e, errorData);
    }

    public static <T> ErrorRespDto<T> empty() {
        return new ErrorRespDto<T>(null);
    }
}
