package com.korea.triplocation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.korea.triplocation.api.dto.request.UserModifyReqDto;
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
	
	public UserRespDto modifyUser(int userId, UserModifyReqDto userModifyReqDto) {
		User user = userRepository.getUserById(userId);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
        if (userModifyReqDto.getEmail() != null) {
            user.setEmail(userModifyReqDto.getEmail());
        }
        if (userModifyReqDto.getName() != null) {
            user.setName(userModifyReqDto.getName());
        }
        if (userModifyReqDto.getPhone() != null) {
            user.setPhone(userModifyReqDto.getPhone());
        }
        if (userModifyReqDto.getAddress() != null) {
            user.setAddress(userModifyReqDto.getAddress());
        }
        if (userModifyReqDto.getProfileImg() != null) {
            user.setProfileImg(userModifyReqDto.getProfileImg());
        }
        
        userRepository.modifyUser(user);
        
        return UserRespDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .profileImg(user.getProfileImg())
                .build();
	}
	
	public void deleteUser(int userId) {
		userRepository.deleteUser(userId);
	}
	
	

}
