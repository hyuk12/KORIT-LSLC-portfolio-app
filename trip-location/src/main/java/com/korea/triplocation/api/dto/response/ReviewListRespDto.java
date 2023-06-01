package com.korea.triplocation.api.dto.response;

import java.time.LocalDate;
import java.util.List;

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
	private String reviewContents;
	private int reviewRating;
	private LocalDate reviewCreateDate;
	
	private String reviewImgUrl;
	private List<String> reviewImgUrls;
	private String startDate;
	private String endDate;

}
