package com.korea.triplocation.service;

import java.util.ArrayList;
import java.util.List;

import com.korea.triplocation.security.jwt.JwtTokenProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.korea.triplocation.api.dto.request.ResetPasswordReqDto;
import com.korea.triplocation.api.dto.request.UserModifyReqDto;
import com.korea.triplocation.api.dto.response.UserRespDto;
import com.korea.triplocation.domain.user.entity.User;
import com.korea.triplocation.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;
	
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
	
	public boolean modifyUser(int userId, UserModifyReqDto userModifyReqDto) {
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
        
		return userRepository.modifyUser(user) != 0;

	}
	
	public boolean resetPassword(ResetPasswordReqDto resetPasswordReqDto) {
		User user = userRepository.searchUserByEmail(resetPasswordReqDto.getEmail());
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		if (resetPasswordReqDto.getPassword() != null) {
			user.setPassword(new BCryptPasswordEncoder().encode(resetPasswordReqDto.getPassword()));
		}
		
		return userRepository.resetPassword(user) != 0;
	}
	
	public void deleteUser(int userId) {
		userRepository.deleteUser(userId);
	}
	
	

}
