package com.korea.triplocation.domain.user.entity;

import java.time.LocalDate;
import java.util.List;

import com.korea.triplocation.api.dto.response.UserRespDto;
import com.korea.triplocation.security.PrincipalUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private int userId;
//	role_id => roleId 수정
	private String email;
	private String password;
	private String name;
	private String phone;
	private String address;
	private int postsImgId;
	private String provider;

	private LocalDate createDate;	// 계정 생성 일자
	
	private List<Authority> authorities;
	
	public PrincipalUser toPrincipal() {
		return PrincipalUser.builder()
				.userId(userId)
				.email(email)
				.password(password)
				.authorities(authorities)
				.build();
	}
	
	public UserRespDto toDto() {
		PostsImg postsImg = new PostsImg();
		
		return UserRespDto.builder()
				.userId(userId)
				.email(email)
				.name(name)
				.phone(phone)
				.address(address)
				.postsImgId(postsImgId)
				.originName(postsImg.getOriginName())
				.tempName(postsImg.getTempName())
				.imgSize(postsImg.getImgSize())
				.build();
	}
	
	
	
}
