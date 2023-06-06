package com.korea.triplocation.service;


import com.korea.triplocation.api.dto.request.review.ReviewReqDto;
import com.korea.triplocation.api.dto.response.ReviewListRespDto;
import com.korea.triplocation.domain.review.entity.Review;
import com.korea.triplocation.domain.review.entity.ReviewImg;
import com.korea.triplocation.exception.CustomException;
import com.korea.triplocation.repository.ReviewRepository;
import com.korea.triplocation.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

	@Value("${file.path}")
	private String filePath;

	private final ReviewRepository reviewRepository;


	public List<ReviewListRespDto> getUserReviewListAll() {
		PrincipalUser principal = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<ReviewListRespDto> dtos = new ArrayList<>();

		for (Review entity : reviewRepository.getReviewListByUserId(principal.getUserId())) {
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
	
	public List<ReviewListRespDto> getAllReviewList() {
		List<Review> reviews = reviewRepository.getAllReviewList();
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

	private String convertFilePathToUrl(String tempName) {
		return "http://localhost:8080/image/review/" + tempName;
	}

	public ReviewListRespDto getReviewByReviewId(int reviewId) {

		List<ReviewImg> reviewImgListByReviewId = reviewRepository.getReviewImgListByReviewId(reviewId);
		Review reviewByReviewId = reviewRepository.getReviewByReviewId(reviewId);
		List<String> imgsList = new ArrayList<>();

		for (ReviewImg reviewImg : reviewImgListByReviewId) {
			imgsList.add(convertFilePathToUrl(reviewImg.getTempName()));
		}

		ReviewListRespDto reviewData = ReviewListRespDto.builder()
				.reviewId(reviewByReviewId.getReviewId())
				.travelId(reviewByReviewId.getTravelId())
				.userId(reviewByReviewId.getUserId())
				.reviewTitle(reviewByReviewId.getReviewTitle())
				.reviewContents(reviewByReviewId.getReviewContents())
				.reviewRating(reviewByReviewId.getReviewRating())
				.reviewCreateDate(reviewByReviewId.getReviewCreateDate())
				.reviewImgUrls(imgsList)
				.build();
		return reviewData;
	};

	@Transactional
	public int updateReview(int reviewId, ReviewReqDto reviewReqDto) {
		Review reviewByReviewId = reviewRepository.getReviewByReviewId(reviewId);
		if(reviewByReviewId == null) {
			throw new CustomException("리뷰가 없어요");
		}

		if (reviewReqDto.getTitle() != null ) {
			reviewByReviewId.setReviewTitle(reviewReqDto.getTitle());
		}
		if (reviewReqDto.getReview() != null ) {
			reviewByReviewId.setReviewContents(reviewReqDto.getReview());
		}
		if (reviewReqDto.getRating() != 0 ) {
			reviewByReviewId.setReviewRating(reviewReqDto.getRating());
		}

		if (reviewReqDto.getImgFiles() != null) {
			List<ReviewImg> reviewImgListByReviewId = reviewRepository.getReviewImgListByReviewId(reviewId);
			if(reviewImgListByReviewId != null) {
				try {
					for (ReviewImg reviewImg: reviewImgListByReviewId) {
						reviewRepository.deleteReviewImg(reviewImg.getReviewImgId());
						deleteFile(reviewImg.getTempName());
					}
				}catch (IOException e) {
					throw new RuntimeException(e);
				}
				List<ReviewImg> reviewImgs = uploadFiles(reviewId, reviewReqDto.getImgFiles());
				reviewRepository.registerReviewImgs(reviewImgs);
			}else {
				List<ReviewImg> reviewImgs = uploadFiles(reviewId, reviewReqDto.getImgFiles());
				reviewRepository.registerReviewImgs(reviewImgs);
			}
		}
		return reviewRepository.modifyReview(reviewByReviewId);
	}

	private void deleteFile(String filename) throws IOException {
		Path uploadPath = Paths.get(filePath + "review/" + filename);

		if (Files.exists(uploadPath)) {
			try {
				Files.delete(uploadPath);
			} catch (IOException e) {
				throw new IOException("Failed to delete file: " + uploadPath, e);
			}
		} else {
			throw new FileNotFoundException("File not found: " + uploadPath);
		}
	}

	@Transactional
	public int deleteReview(int reviewId) {
		List<ReviewImg> reviewImgListByReviewId = reviewRepository.getReviewImgListByReviewId(reviewId);
		if (reviewImgListByReviewId != null) {
			try {
				for (ReviewImg reviewImg : reviewImgListByReviewId) {
					reviewRepository.deleteReviewImages(reviewImg.getReviewImgId());
					deleteFile(reviewImg.getTempName());
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		int deleteReview = reviewRepository.deleteReview(reviewId);
		return deleteReview ;
	}
}
