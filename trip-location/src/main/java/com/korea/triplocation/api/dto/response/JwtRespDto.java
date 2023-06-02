package com.korea.triplocation.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtRespDto {
	private String accessToken;
}
