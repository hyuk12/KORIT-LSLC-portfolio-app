package com.korea.triplocation.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import com.korea.triplocation.repository.UserRepository;

import com.korea.triplocation.api.dto.request.UserReqDto;
import com.korea.triplocation.entity.Authority;
import com.korea.triplocation.entity.User;
import com.korea.triplocation.exception.CustomException;
import com.korea.triplocation.exception.ErrorMap;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
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
		
//		userRepository.saveUser(User.builder()
//					.userId(userEntity.getUserId())
//					.email(userEntity.getEmail())
//					.password(userEntity.getPassword())
//					.name(userEntity.getName())
//					.phone(userEntity.getPhone())
//					.address(userEntity.getAddress())
//					.profileImage(userEntity.getProfileImage())
//					.createDate(LocalDate.now())
//					.build());
	}
	
	
	
}
