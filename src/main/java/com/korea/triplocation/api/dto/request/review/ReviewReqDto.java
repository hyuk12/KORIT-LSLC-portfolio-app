package com.korea.triplocation.api.dto.request.review;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.korea.triplocation.domain.review.entity.Review;

import lombok.Data;

@Data
public class ReviewReqDto {
	private int userId;
	private int travelId;
	private String title;
	private List<MultipartFile> imgFiles;
	private String review;
	private int rating;

	public Review toEntity() {
		return Review.builder()
							.userId(userId)
							.travelId(travelId)
							.reviewTitle(title)
							.reviewContents(review)
							.reviewRating(rating)
							.build();

	}
}
