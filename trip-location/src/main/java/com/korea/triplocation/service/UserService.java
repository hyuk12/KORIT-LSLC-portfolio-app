package com.korea.triplocation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.korea.triplocation.api.dto.response.UserRespDto;
import com.korea.triplocation.domain.user.entity.User;
import com.korea.triplocation.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
//	public UserRespDto getUser(int userId) {
//		return userRepository.getUser(userId).toDto();
//	}
	
	public List<UserRespDto> getUserAll() {
		List<UserRespDto> dtos = new ArrayList<>();
		
		userRepository.getUserAll().forEach(entity -> {
			dtos.add(entity.toDto());
		});
		return dtos;
	}
	
	public UserRespDto searchUser(int type, String value) {
		if (type == 1) {
			return userRepository.searchUserByEmail(value).toDto();
		} else if (type == 2) {
			return userRepository.searchUserByPhone(value).toDto();
		} else {
			throw new IllegalArgumentException("Invalid type");
		}
		
	}
	
	

}
