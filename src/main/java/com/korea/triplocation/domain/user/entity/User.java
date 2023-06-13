package com.korea.triplocation.domain.user.entity;

import com.korea.triplocation.api.dto.response.UserRespDto;
import com.korea.triplocation.security.PrincipalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private int userId;
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
		PostsImg postsImg = new PostsImg();
		return PrincipalUser.builder()
				.userId(userId)
				.email(email)
				.password(password)
				.postsImgId(postsImg.getPostsImgId())
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
				.postsImgId(postsImg.getPostsImgId())
				.build();
	}
}
