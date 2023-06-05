package com.korea.triplocation.domain.review.entity;

import java.time.LocalDate;
import java.util.List;

import com.korea.triplocation.api.dto.response.ReviewListRespDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
	private int reviewId;
	private int userId;
	private int travelId;
	private String reviewTitle;
	private String reviewContents;
	private int reviewRating;
	
	private LocalDate reviewCreateDate;
	
	private String startDate;
	private String endDate;
	private List<ReviewImg> reviewImgs;

	private String convertFilePathToUrl(String tempName) {
		return "http://localhost:8080/image/review/" + tempName;
	}

	public ReviewListRespDto toDto() {
		
		String firstTempName = null;
		
		if (reviewImgs != null && !reviewImgs.isEmpty()) {
		    ReviewImg firstReviewImg = reviewImgs.get(0);
		    firstTempName = firstReviewImg.getTempName();
		} else {
			firstTempName = "default.gif";
		}
		
		return ReviewListRespDto.builder()
				.reviewId(reviewId)
				.travelId(travelId)
				.userId(userId)
				.reviewTitle(reviewTitle)
				.reviewRating(reviewRating)
				.reviewContents(reviewContents) 
				.reviewImgUrl(convertFilePathToUrl(firstTempName))
				.reviewCreateDate(reviewCreateDate)
				.startDate(startDate)
				.endDate(endDate)
				.build();
	}
}
