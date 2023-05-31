package com.korea.triplocation.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.korea.triplocation.api.dto.request.ReviewReqDto;
import com.korea.triplocation.api.dto.response.DataRespDto;
import com.korea.triplocation.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserReviewList(@PathVariable int userId) {
		return ResponseEntity.ok(DataRespDto.of(reviewService.getUserReviewListAll(userId)));
	}

	@GetMapping("/list")
	public ResponseEntity<?> getReviewList() {
		return ResponseEntity.ok(reviewService.getReviewListByRating());
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveReview(ReviewReqDto reviewReqDto) {
		System.out.println(reviewReqDto);
		return ResponseEntity.ok(null);
	}
}
