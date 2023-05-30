package com.korea.triplocation.api.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReviewListRespDto {
	
	private int reviewId;
	private int travelId;
	private String reviewTitle;
	private int reviewRating;
	private LocalDate reviewCreateDate;
	
	private String reviewImgUrl;
	
	private String startDate;
	private String endDate;

}
