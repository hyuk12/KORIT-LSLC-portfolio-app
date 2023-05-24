package com.korea.triplocation.domain.review.entity;

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
	private int reviewImgId;
	private String reviewContents;
	private int reviewRating;
}
