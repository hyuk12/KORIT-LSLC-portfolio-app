package com.korea.triplocation.api.controller;

import com.korea.triplocation.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.korea.triplocation.api.dto.request.ResetPasswordReqDto;
import com.korea.triplocation.api.dto.request.UserModifyReqDto;
import com.korea.triplocation.api.dto.response.DataRespDto;
import com.korea.triplocation.api.dto.response.UserRespDto;
import com.korea.triplocation.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
	
	private final UserService userService;

    // user 전체 조회
    @GetMapping("/all")
    public ResponseEntity<?> getUserAll() {
    	return ResponseEntity.ok().body(DataRespDto.of(userService.getUserAll()));
    }
    
    // user 조회 - email, phone
    // type 1 - email
    // type 2 - phone
    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestParam("type") int type,
            							@RequestParam("value") String value) {
        System.out.println(value);
    	return ResponseEntity.ok().body(DataRespDto.of(userService.searchUser(type, value)));
    }

    // user 정보 수정
    @PutMapping("/{userId}")
    public ResponseEntity<?> modifyUser(@PathVariable int userId, UserModifyReqDto userModifyReqDto) {
        return ResponseEntity.ok(DataRespDto.of(userService.modifyUser(userId, userModifyReqDto)));
    }
    
    // user 비밀번호 수정
    @PutMapping("/password/reset")
    public ResponseEntity<?> passwordReset(@RequestBody ResetPasswordReqDto resetPasswordReqDto) {
    	return ResponseEntity.ok(DataRespDto.of(userService.resetPassword(resetPasswordReqDto)));
    }
    
    // user 정보 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
    	userService.deleteUser(userId);
    	return ResponseEntity.ok(DataRespDto.ofDefault());
    }
}
