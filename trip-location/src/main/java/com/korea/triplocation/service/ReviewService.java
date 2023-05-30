package com.korea.triplocation.service;

import java.util.ArrayList;
import java.util.List;

import com.korea.triplocation.api.dto.response.ReviewRespDto;
import org.springframework.stereotype.Service;

import com.korea.triplocation.api.dto.response.ReviewListRespDto;
import com.korea.triplocation.domain.review.entity.Review;
import com.korea.triplocation.domain.review.entity.ReviewImg;
import com.korea.triplocation.repository.ReviewRepository;
import com.korea.triplocation.repository.TravelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	
	
	public List<ReviewListRespDto> getUserReviewListAll(int userId) {
		List<ReviewListRespDto> dtos = new ArrayList<>();
		
		for (Review entity : reviewRepository.getReviewListByUserId(userId)) {
			dtos.add(entity.toDto());
			
	    }
		
		return dtos;
		
	}

	public List<ReviewListRespDto> getReviewListByRating() {
		List<Review> reviews = reviewRepository.getReviewListByRating();
		List<ReviewListRespDto>	reviewListRespDtos = new ArrayList<>();

		for (Review review : reviews) {
			System.out.println(review.getReviewRating());
			reviewListRespDtos.add(review.toDto());
		}


		return reviewListRespDtos;
	}
	

}
