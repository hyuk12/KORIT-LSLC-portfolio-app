package com.korea.triplocation.api.controller;

import com.korea.triplocation.security.JwtTokenProvider;
import com.korea.triplocation.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final JwtTokenProvider jwtTokenProvider;
	
//	// MyPage 로그인된 user
//    @GetMapping("/{userId}")
//    public ResponseEntity<?> getUser(@PathVariable int userId) {
//    	return ResponseEntity.ok().body(null);
//    }
    
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
    public ResponseEntity<?> modifyUser(@PathVariable int userId, @RequestBody UserModifyReqDto userModifyReqDto) {

        return ResponseEntity.ok(DataRespDto.of(userService.modifyUser(userId, userModifyReqDto)));
    }
    
    // user 정보 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
    	userService.deleteUser(userId);
    	return ResponseEntity.ok(DataRespDto.ofDefault());
    }
}
