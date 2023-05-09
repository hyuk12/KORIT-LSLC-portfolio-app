package com.korea.triplocation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.korea.triplocation.api.dto.response.UserRespDto;
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

}
