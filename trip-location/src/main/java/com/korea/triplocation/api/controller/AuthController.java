package com.korea.triplocation.api.controller;

import com.korea.triplocation.api.dto.request.LoginReqDto;
import com.korea.triplocation.api.dto.request.UserReqDto;
import com.korea.triplocation.api.dto.response.DataRespDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/user")
    public ResponseEntity<?> signup(UserReqDto userReqDto) {
        return ResponseEntity.ok(DataRespDto.ofDefault());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(LoginReqDto loginReqDto) {
        return ResponseEntity.ok(DataRespDto.of(null));
    }
}
