package com.korea.triplocation.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.korea.triplocation.domain.user.entity.PostsImg;
import com.korea.triplocation.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.korea.triplocation.api.dto.request.ResetPasswordReqDto;
import com.korea.triplocation.api.dto.request.UserModifyReqDto;
import com.korea.triplocation.api.dto.response.UserRespDto;
import com.korea.triplocation.domain.user.entity.User;
import com.korea.triplocation.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;
	@Value("${file.path}")
	private String filePath;
	
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
			String newProfileImgPath = uploadFile(userModifyReqDto.getProfileImg());
			user.setProfileImgPath(newProfileImgPath);
		}
        
		return userRepository.modifyUser(user) != 0;

	}

	private String uploadFile(MultipartFile file) {
		if(file == null) {
			return null;
		}

		String originFileName = file.getOriginalFilename();
		String extension = originFileName.substring(originFileName.lastIndexOf("."));
		String tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;

		Path uploadPath = Paths.get(filePath + "user/" + tempFileName);

		File f = new File(filePath + "user");

		if(!f.exists()) {
			f.mkdirs();
		}

		try {
			Files.write(uploadPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return uploadPath.toString();
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
