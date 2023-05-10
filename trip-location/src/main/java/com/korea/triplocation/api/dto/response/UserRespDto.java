package com.korea.triplocation.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRespDto {
	
	private int userId;
	private String email;
	private String name;
	private String phone;
	private String address;
	private String profileImg; 

}
