package com.korea.triplocation.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRespDto {
	
	private int userId;
	private String email;
	private String name;
	private String phone;
	private String address;
	private String profileImg; 

}
