package com.korea.triplocation.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.korea.triplocation.api.dto.request.UserReqDto;
import com.korea.triplocation.entity.User;
import com.korea.triplocation.exception.CustomException;
import com.korea.triplocation.exception.ErrorMap;
import com.korea.triplocation.repository.UserRepository;

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
		
		userRepository.saveUser(User.builder()
					.userId(userEntity.getUserId())
					.roleId(1)
					.email(userEntity.getEmail())
					.password(userEntity.getPassword())
					.name(userEntity.getName())
					.phone(userEntity.getPhone())
					.address(userEntity.getAddress())
					.profileImage(userEntity.getProfileImage())
					.createDate(LocalDate.now())
					.build());
	}

	
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// authenticationManager가 하는 일이다. 여기서는 username은 email이다.
//		User userEntity = userRepository.findUserByEmail(username);
//		
//		if(userEntity == null) {
//			throw new CustomException("로그인 실패",
//					ErrorMap.builder()
//					.put("email", "사용자 정보를 확인하세요.")
//					.build());
//		}
//		
//		return userEntity.;
//	}
	
	
}
