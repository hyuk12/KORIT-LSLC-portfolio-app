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
	
	private String convertFilePathToUrl(String tempName) {
		return "http://localhost:8080/image/user/" + tempName;
	}
	
	public UserRespDto searchUser(int type, String value) {
		
		User user = null;
		PostsImg postsImg = null;
		
		if (type == 1) {
			user = userRepository.searchUserByEmail(value);
			postsImg =userRepository.getPostsImgById(user.getPostsImgId());
		} else if (type == 2) {
			user = userRepository.searchUserByPhone(value);
			postsImg =userRepository.getPostsImgById(user.getPostsImgId());
		} else {
			throw new IllegalArgumentException("Invalid type");
		}
		
		if(user == null) {
			return null;
		}
		
		String imgUrl = null;
		
		if(postsImg != null) {
			imgUrl = convertFilePathToUrl(postsImg.getTempName());
		}
		return UserRespDto.builder()
				.userId(user.getUserId())
				.postsImgId(user.getPostsImgId())
				.email(user.getEmail())
				.name(user.getName())
				.phone(user.getPhone())
				.postsImgUrl(imgUrl)
				.build();
		
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
			PostsImg postsImg = uploadFile(userId, userModifyReqDto.getProfileImg());
			userRepository.postsImg(postsImg);
			user.setPostsImgId(postsImg.getPostsImgId());
		}
        
		return userRepository.modifyUser(user) != 0;

	}
	

	private PostsImg uploadFile(int userId, MultipartFile file) {
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



		return PostsImg.builder()
				.userId(userId)
				.originName(originFileName)
				.tempName(tempFileName)
				.imgSize(Long.toString(file.getSize()))
				.build();
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
