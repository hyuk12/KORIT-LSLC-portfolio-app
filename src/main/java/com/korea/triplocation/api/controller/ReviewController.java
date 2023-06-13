package com.korea.triplocation.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.korea.triplocation.api.dto.request.review.ReviewReqDto;
import com.korea.triplocation.api.dto.response.DataRespDto;
import com.korea.triplocation.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	
	@GetMapping("/mylist")
	public ResponseEntity<?> getUserReviewList() {
		return ResponseEntity.ok(DataRespDto.of(reviewService.getUserReviewListAll()));
	}

	@GetMapping("/list/{reviewId}")
	public ResponseEntity<?> getReviewByReviewId(@PathVariable int reviewId) {
		return ResponseEntity.ok(DataRespDto.of(reviewService.getReviewByReviewId(reviewId)));
	}

	@GetMapping("/list")
	public ResponseEntity<?> getReviewList() {
		return ResponseEntity.ok(reviewService.getReviewListByRating());
	}

	@GetMapping("/list/all")
	public ResponseEntity<?> getAllReviewList() {
		return ResponseEntity.ok(reviewService.getAllReviewList());
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveReview(ReviewReqDto reviewReqDto) {
		return ResponseEntity.ok(reviewService.saveReviews(reviewReqDto));
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<?> updateReview(@PathVariable int reviewId, ReviewReqDto reviewReqDto) {
		return ResponseEntity.ok(reviewService.updateReview(reviewId, reviewReqDto));
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<?> deleteReview(@PathVariable int reviewId) {
		return ResponseEntity.ok(reviewService.deleteReview(reviewId));
	}
}
