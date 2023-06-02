package com.korea.triplocation.api.controller;

import javax.validation.Valid;

import com.korea.triplocation.api.dto.request.OAuth2ProviderMergeReqDto;
import com.korea.triplocation.api.dto.request.OAuth2RegisterReqDto;
import com.korea.triplocation.api.dto.request.ResetPasswordReqDto;
import com.korea.triplocation.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.korea.triplocation.aop.annotation.ValidAspect;
import com.korea.triplocation.api.dto.request.LoginReqDto;
import com.korea.triplocation.api.dto.request.UserReqDto;
import com.korea.triplocation.api.dto.response.DataRespDto;
import com.korea.triplocation.service.AuthService;
import com.korea.triplocation.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;
	private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
	
	@ValidAspect
    @PostMapping("/user")
    public ResponseEntity<?> signup(@Valid @RequestBody UserReqDto userReqDto, BindingResult bindingResult) {

		authService.checkDuplicatedByEmail(userReqDto.getEmail());
		authService.signup(userReqDto);
        return ResponseEntity.ok(DataRespDto.ofDefault());
    }

    @ValidAspect
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReqDto loginReqDto, BindingResult bindingResult) {
        return ResponseEntity.ok(authService.signin(loginReqDto));
    }
    
    @PutMapping("/password/reset")
    public ResponseEntity<?> passwordReset(@RequestBody ResetPasswordReqDto resetPasswordReqDto) {
    	return ResponseEntity.ok(DataRespDto.of(userService.resetPassword(resetPasswordReqDto)));
    }
    

    @GetMapping("/authenticated")
    public ResponseEntity<?> authenticated(@RequestHeader(value = "Authorization") String accessToken) {
        return ResponseEntity.ok(authService.authenticated(accessToken));

    }



    @PostMapping("/oauth2/register")
    public ResponseEntity<?> oauth2Register(
            @RequestHeader(value = "registerToken") String registerToken,
            @RequestBody OAuth2RegisterReqDto oAuth2RegisterReqDto) {

        Boolean validatedToken = jwtTokenProvider.validateToken(jwtTokenProvider.getToken(registerToken));

        if(!validatedToken) {
            // token 이 유효하지 않음
            return ResponseEntity.badRequest().body("회원가입 요청 시간이 초과되었습니다.");
        }
        return ResponseEntity.ok(authService.oauth2Register(oAuth2RegisterReqDto));
    }

    @PutMapping("/oauth2/merge")
    public ResponseEntity<?> providerMerge(@RequestBody OAuth2ProviderMergeReqDto oAuth2ProviderMergeReqDto) {
        if(!authService.checkPassword(oAuth2ProviderMergeReqDto.getEmail(), oAuth2ProviderMergeReqDto.getPassword())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }
        return ResponseEntity.ok(authService.oAuth2ProviderMerge(oAuth2ProviderMergeReqDto));
    }

}
