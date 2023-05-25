package com.korea.triplocation.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReviewListRespDto {
	
	private int reviewId;
	private int userId;
	private int travelId;
	private int reviewRating;
	
	private String regionName;
	
	private String startDate;
	private String endDate;
	private String reviewImgUrl;

}
