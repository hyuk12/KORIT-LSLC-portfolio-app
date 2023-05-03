package com.korea.triplocation.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.korea.triplocation.api.dto.request.LoginReqDto;
import com.korea.triplocation.api.dto.request.UserReqDto;
import com.korea.triplocation.api.dto.response.JwtRespDto;
import com.korea.triplocation.entity.Authority;
import com.korea.triplocation.entity.User;
import com.korea.triplocation.exception.CustomException;
import com.korea.triplocation.exception.ErrorMap;
import com.korea.triplocation.repository.UserRepository;
import com.korea.triplocation.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	
	public void checkDuplicatedByEmail(String email) {
		User userEntity = userRepository.findUserByEmail(email);
		
		if(userEntity != null) {
			throw new CustomException("Duplicated Email", 
					ErrorMap.builder()
					.put("email","이미 사용중인 이메일입니다.").build());
		}
	}
	
	public void signup(UserReqDto userReqDto) {
		User userEntity = userReqDto.toEntity();

		userRepository.saveUser(userEntity);
		
		userRepository.saveAuthority(
				Authority.builder().userId(userEntity.getUserId()).roleId(1).build());
		
		

	}

	public JwtRespDto signin(LoginReqDto loginReqDto) {
		
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());
		Authentication authentication = 
				authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		return jwtTokenProvider.generateToken(authentication);
		
	}
	
	
	
}
