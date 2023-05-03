package com.korea.triplocation.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.korea.triplocation.api.dto.request.LoginReqDto;
import com.korea.triplocation.api.dto.request.UserReqDto;
import com.korea.triplocation.api.dto.response.JwtRespDto;
import com.korea.triplocation.api.dto.response.PrincipalRespDto;
import com.korea.triplocation.entity.Authority;
import com.korea.triplocation.entity.User;
import com.korea.triplocation.exception.CustomException;
import com.korea.triplocation.exception.ErrorMap;
import com.korea.triplocation.repository.UserRepository;
import com.korea.triplocation.security.JwtTokenProvider;

import io.jsonwebtoken.Claims;
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
	
	public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException{
		User userEntity = userRepository.findUserByEmail(username);
		
		if(userEntity != null) {
			throw new CustomException("로그인 실패",
					ErrorMap.builder()
					.put("email", "사용자 정보를 확인하세요.")
					.build());
		}
		
		return userEntity.toPrincipal();
	}
	
	public boolean authenticated(String accessToken) {
		return jwtTokenProvider.validateToken(jwtTokenProvider.getToken(accessToken));
	}
	
	public PrincipalRespDto getPrincipal(String accessToken) {
		Claims claims = jwtTokenProvider.getClaims(jwtTokenProvider.getToken(accessToken));
		User userEntity = userRepository.findUserByEmail(claims.getSubject());
		
		return PrincipalRespDto.builder()
				.userId(userEntity.getUserId())
				.email(userEntity.getEmail())
				.name(userEntity.getName())
				.authorities((String) claims.get("auth"))
				.build();
		
	}
	
}
