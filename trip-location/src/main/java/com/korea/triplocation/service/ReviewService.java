package com.korea.triplocation.service;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.korea.triplocation.api.dto.request.ReviewReqDto;
import com.korea.triplocation.api.dto.response.ReviewListRespDto;
import com.korea.triplocation.domain.review.entity.Review;
import com.korea.triplocation.domain.review.entity.ReviewImg;
import com.korea.triplocation.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	@Value("${file.path}")
	private String filePath;

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
		List<ReviewListRespDto> reviewListRespDtos = new ArrayList<>();

		for (Review review : reviews) {
			reviewListRespDtos.add(review.toDto());
		}


		return reviewListRespDtos;
	}


	
	public int saveReviews(ReviewReqDto reviewReqDto) {
		
		Review reviews = reviewReqDto.toEntity();
		reviewRepository.registerReviews(reviews);
		
		return reviewRepository.registerReviewImgs(
				uploadFiles(reviews.getReviewId(), reviewReqDto.getImgFiles()));
	}

	private List<ReviewImg> uploadFiles(int reviewId, List<MultipartFile> files) {
		if (files == null) {
			return null;
		}

		List<ReviewImg> reviewFiles = new ArrayList<>();

		files.forEach(file -> {
			String originFileName = file.getOriginalFilename();
			String extension = originFileName.substring(originFileName.lastIndexOf("."));
			String tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;

			Path uploadPath = Paths.get(filePath + "review/" + tempFileName);

			File f = new File(filePath + "review");
			if (!f.exists()) {
				f.mkdirs();
			}
				try {
					Files.write(uploadPath, file.getBytes());
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
			reviewFiles.add(ReviewImg.builder()
					   .reviewId(reviewId)
					   .originName(originFileName)
					   .tempName(tempFileName)
					   .imgSize(Long.toString(file.getSize()))
					   .build());
		});

		return reviewFiles;
	}

}
