package com.korea.triplocation.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korea.triplocation.api.dto.request.LoginReqDto;
import com.korea.triplocation.api.dto.request.ResetPasswordReqDto;
import com.korea.triplocation.api.dto.request.UserModifyReqDto;
import com.korea.triplocation.api.dto.response.DataRespDto;
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
    public ResponseEntity<?> deleteUser(@PathVariable int userId, @RequestBody LoginReqDto loginReqDto) {
    	return ResponseEntity.ok(DataRespDto.of(userService.deleteUser(userId, loginReqDto)));
    }
}
