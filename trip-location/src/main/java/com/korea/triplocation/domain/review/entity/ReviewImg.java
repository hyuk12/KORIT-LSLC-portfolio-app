package com.korea.triplocation.domain.review.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImg {
	private int reviewImgId;
	private int reviewId;
	private String originName;
	private String tempName;
	private String imgSize;
}
