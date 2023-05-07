package com.korea.triplocation.api.controller;

import javax.validation.Valid;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.korea.triplocation.aop.annotation.ValidAspect;
import com.korea.triplocation.api.dto.request.LoginReqDto;
import com.korea.triplocation.api.dto.request.UserReqDto;
import com.korea.triplocation.api.dto.response.DataRespDto;
import com.korea.triplocation.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	private final UserService userService;
	
	@ValidAspect
    @PostMapping("/user")
    public ResponseEntity<?> signup(@Valid @RequestBody UserReqDto userReqDto, BindingResult bindingResult) {
//    	System.out.println(userReqDto.getEmail() + "\t" + userReqDto.getPassword());
    	userService.checkDuplicatedByEmail(userReqDto.getEmail());
    	userService.signup(userReqDto);
        return ResponseEntity.ok(DataRespDto.ofDefault());
    }
	
	@ValidAspect
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReqDto loginReqDto, BindingResult bindingResult) {
		System.out.println(loginReqDto.getEmail() + "\t" + loginReqDto.getPassword());
		System.out.println(bindingResult.toString());
//		userService.signin(loginReqDto);
        return ResponseEntity.ok(userService.signin(loginReqDto));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<?> authenticated(String accessToken) {
        return ResponseEntity.ok(userService.authenticated(accessToken));
    }

    @GetMapping("/principal")
    public ResponseEntity<?> principal(String accessToken) {
        return ResponseEntity.ok(userService.getPrincipal(accessToken));
    }
}
