package com.korea.triplocation.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.korea.triplocation.api.dto.response.PrincipalRespDto;
import com.korea.triplocation.security.PrincipalUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.korea.triplocation.api.dto.request.login.LoginReqDto;
import com.korea.triplocation.api.dto.request.user.ResetPasswordReqDto;
import com.korea.triplocation.api.dto.request.user.UserModifyReqDto;
import com.korea.triplocation.api.dto.response.UserRespDto;
import com.korea.triplocation.domain.user.entity.PostsImg;
import com.korea.triplocation.domain.user.entity.User;
import com.korea.triplocation.exception.CustomException;
import com.korea.triplocation.repository.UserRepository;
import com.korea.triplocation.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;

	@Value("${file.path}")
	private String filePath;

	public List<UserRespDto> getUserAll() {
		List<UserRespDto> dtos = new ArrayList<>();
		
		userRepository.getUserAll().forEach(entity -> {
			dtos.add(entity.toDto());
		});
		return dtos;
	}
	
	private String convertFilePathToUrl(String tempName) {
		return "http://43.202.21.26/image/user/" + tempName;
	}
	
	public UserRespDto searchUser(int type, String value) {
	    User user = null;
	    PostsImg postsImg = null;
	    String imgUrl = null;
	    int postsImgId = 0;
	    if (type == 1) {
	        user = userRepository.searchUserByEmail(value);
	        if (user != null) {
	            postsImgId = user.getPostsImgId();
	            if (postsImgId != -1) {
	                postsImg = userRepository.getPostsImgById(postsImgId);
	                if (postsImg != null) {
	                    imgUrl = convertFilePathToUrl(postsImg.getTempName());
	                } else {
	                    imgUrl = convertFilePathToUrl("default.png");
	                }
	            } else {
	                imgUrl = convertFilePathToUrl("default.png");
	            }
	        }
	    } else if (type == 2) {
	        user = userRepository.searchUserByPhone(value);
	        if (user != null) {
	            postsImgId = user.getPostsImgId();
	            if (postsImgId != -1) {
	                postsImg = userRepository.getPostsImgById(postsImgId);
	                if (postsImg != null) {
	                    imgUrl = convertFilePathToUrl(postsImg.getTempName());
	                } else {
	                    imgUrl = convertFilePathToUrl("default.png");
	                }
	            } else {
	                imgUrl = convertFilePathToUrl("default.png");
	            }
	        }
	    } else {
	        throw new IllegalArgumentException("Invalid type");
	    }
	    
	    if (user == null) {
	        return null;
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

	
	public boolean modifyUser(UserModifyReqDto userModifyReqDto) {
		PrincipalUser principal = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.getUserById(principal.getUserId());

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
//			System.out.println(userId);
			PostsImg currentPostsImg = userRepository.getPostsImgByUserId(principal.getUserId());
			// If user has a profile image, delete it
			if (currentPostsImg != null) {
				try {
					deleteFile(currentPostsImg.getTempName());

				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				userRepository.deletePostsImg(currentPostsImg.getPostsImgId());

				PostsImg postsImg = uploadFile(principal.getUserId(), userModifyReqDto.getProfileImg());
				userRepository.postsImg(postsImg);
				user.setPostsImgId(postsImg.getPostsImgId());
			}

			if (currentPostsImg == null && user.getPostsImgId() == -1) {
				PostsImg postsImg = uploadFile(principal.getUserId(), userModifyReqDto.getProfileImg());
				userRepository.postsImg(postsImg);
				user.setPostsImgId(postsImg.getPostsImgId());
			}
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

	private void deleteFile(String filename) throws IOException {
		Path uploadPath = Paths.get(filePath + "user/" + filename);

		if (Files.exists(uploadPath)) {
			try {
				Files.delete(uploadPath);
			} catch (IOException e) {
				throw new IOException("Failed to delete file: " + uploadPath, e);
			}
		} else {
			throw new FileNotFoundException("File not found: " + uploadPath);
		}
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
	
	public boolean deleteUser( LoginReqDto loginReqDto) {
		boolean flag = false;

		// 한 번더 로그인하는 것으로 본인 확인
		if(authService.signin(loginReqDto) != null) {
			PrincipalUser principal = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userRepository.getUserById(principal.getUserId());
			int postsImgId = user.getPostsImgId();
			
			if (postsImgId != -1) {
				PostsImg currentPostsImg = userRepository.getPostsImgById(postsImgId);
				
				if (currentPostsImg != null) {
					try {
						deleteFile(currentPostsImg.getTempName());
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}	
			userRepository.deleteUser(principal.getUserId());
			flag = true;
		} else {
			throw new CustomException("이메일 또는 비밀번호를 확인해주세요");
		}
			
		return flag;
	}

	public PrincipalRespDto getPrincipal() {
		PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userEntity = userRepository.getUserById(principalUser.getUserId());
		PostsImg postsImage = null;
		String imageUrl = null;
		
		if(userEntity.getPostsImgId() != -1) {
			postsImage = userRepository.getPostsImgByUserId(userEntity.getUserId());
			if(postsImage != null) {
				imageUrl = convertFilePathToUrl(postsImage.getTempName());
			}else {
				imageUrl = convertFilePathToUrl("default.png");
			}
		}else {
			imageUrl = convertFilePathToUrl("default.png");
		}

		StringBuilder roles = new StringBuilder();
		principalUser.getAuthorities().forEach(authority -> {
			roles.append(authority.getAuthority() + ",");
		});
		roles.delete(roles.length() - 1, roles.length());

		return PrincipalRespDto.builder()
				.userId(userEntity.getUserId())
				.email(userEntity.getEmail())
				.name(userEntity.getName())
				.phone(userEntity.getPhone())
				.address(userEntity.getAddress())
				.postsImgId(userEntity.getPostsImgId())
				.postsImgUrl(imageUrl)
				.authorities(roles.toString())
				.provider(userEntity.getProvider())
				.build();

	}
}
