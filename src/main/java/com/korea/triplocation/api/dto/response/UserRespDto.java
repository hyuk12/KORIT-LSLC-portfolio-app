package com.korea.triplocation.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRespDto {
	
	private int userId;
	private int postsImgId;
	private String name;
	private String email;
	private String phone;
	private String address;
	private String postsImgUrl;

}
