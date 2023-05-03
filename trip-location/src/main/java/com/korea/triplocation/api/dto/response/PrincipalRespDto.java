package com.korea.triplocation.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PrincipalRespDto {
	private int userId;
	private String email;
	private String name;
	private String authorities;
}