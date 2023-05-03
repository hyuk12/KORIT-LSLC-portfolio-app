package com.korea.triplocation.api.dto.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DataRespDto<T> extends ResponseDto{

    private final T data;

    public DataRespDto(T data) {
        super(true, HttpStatus.OK.value(), "Successfully");
        this.data = data;
    }

    public DataRespDto(T data, String message) {
        super(true, HttpStatus.OK.value(), message);
        this.data = data;
    }

    public static <T> DataRespDto<T> of(T data) {
        return new DataRespDto<>(data);
    }

    public static <T> DataRespDto<T> of(T data, String message) {
        return new DataRespDto<>(data, message);
    }

    public static <T> DataRespDto<T> empty() {
        return new DataRespDto<T>(null);
    }
}