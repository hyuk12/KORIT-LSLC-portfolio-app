package com.korea.triplocation.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReviewRespDto {
	
	private int reviewId;
	private int userId;
	private int travelId;
	
}
