package com.korea.triplocation.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter
@Setter
public class UserRespDto {
	
	private int userId;
	private String email;
	private String name;
	private String phone;
	private String address;
	private int postsImgId;

}
