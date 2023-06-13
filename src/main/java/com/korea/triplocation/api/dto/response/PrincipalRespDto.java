package com.korea.triplocation.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PrincipalRespDto {
	private int userId;
	private String email;
	private String name;
	private String phone;
	private String address;
	private int postsImgId;
	private String postsImgUrl;
	private String authorities;
	private String provider;
}