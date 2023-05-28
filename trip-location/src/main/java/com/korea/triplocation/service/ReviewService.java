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
	private final TravelRepository travelRepository;
	
	private String getRegionByTravelId(int travelId) {
		String address = reviewRepository.getRegionByTravelId(travelId);
		String regionName = null;
		System.out.println(address);
		
		String[] words = address.split(" ");
		for (String word : words) {
			String comparisonWord = word.substring(0, 2);
			System.out.println(comparisonWord);
			regionName = travelRepository.getRegion(comparisonWord);
			if(regionName != null) {
				break;
			}
			System.out.println(regionName);
		}
		
		return regionName;
	}
	
	private String convertFilePathToUrl(String tempName) {
		return "http://localhost:8080/image/rivew/" + tempName;
	}
	
	public List<ReviewListRespDto> getUserReviewListAll(int userId) {
		
		List<ReviewListRespDto> dtos = new ArrayList<>();
		String imgUrl = null;
		
		for (Review entity : reviewRepository.getReviewListByUserId(userId)) {
			List<ReviewImg> imgs = reviewRepository.getReviewImgListByReviewId(entity.getReviewId());
	        if(!imgs.isEmpty()) {
	        	ReviewImg firstimg = imgs.get(0);
	        	imgUrl = convertFilePathToUrl(firstimg.getTempName());

	        } else { 
	        	imgUrl = null;
	        }
			
			String regionName = getRegionByTravelId(entity.getTravelId());
	        ReviewListRespDto dto = entity.toDto();
	        dto.setRegionName(regionName);
	        dto.setReviewImgUrl(imgUrl);
	        dtos.add(dto);
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
