package com.korea.triplocation.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korea.triplocation.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
	
	private final UserService userService;
	
	// MyPage 로그인된 user
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId) {
    	return ResponseEntity.ok().body(null);
    }
    
    // user 전체 조회
    @GetMapping("/all")
    public ResponseEntity<?> users() {
    	return ResponseEntity.ok().body(null);
    }
    
    // user 조회 - email, phone
    // type 1 - email
    // type 2 - phone
    @GetMapping("/search")
    public ResponseEntity<?> searchUser(int type, String searchValue) {
    	return ResponseEntity.ok().body(null);
    }
    
    
    // user 정보 수정
    @PutMapping("/{userId}")
    public ResponseEntity<?> modifyUser(@PathVariable int userId) {
    	return ResponseEntity.ok().body(null);
    }
    
    // user 정보 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
    	return ResponseEntity.ok().body(null);
    }
}
