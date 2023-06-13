package com.korea.triplocation.api.controller;

import java.util.List;

import com.korea.triplocation.api.dto.request.travel.TravelUpdateReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.korea.triplocation.api.dto.request.travel.TravelPlanReqDto;
import com.korea.triplocation.api.dto.response.DataRespDto;
import com.korea.triplocation.service.TravelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/travel/plan")
@RequiredArgsConstructor
public class TravelPlanController {

    private final TravelService travelService;

    @PostMapping("/save")
    public ResponseEntity<?> plan(@RequestBody List<TravelPlanReqDto> travels) {
    	
        travelService.travelSave(travels);
        return ResponseEntity.ok(DataRespDto.ofDefault());
    }

    @GetMapping("/list")//principal에서 자신의 userId로 자신의 모든 여행일정과 img가져오기
    public ResponseEntity<?> view() {
        return ResponseEntity.ok(travelService.findTravelByUser());
    }

    @GetMapping("/info")
    public ResponseEntity<?> myTravelInfo(int travelId) {
        return ResponseEntity.ok(travelService.findTravelInfoByTravelId(travelId));
    }
    
    @GetMapping("/info/copy")
    public ResponseEntity<?> reviewTravelInfo(@RequestParam("reviewId") int reviewId, @RequestParam("travelId") int travelId) {
    	return ResponseEntity.ok(travelService.findReviewTravelInfoByTravelIdAndReviewId(reviewId, travelId));
    }

    @GetMapping("/info/review")
    public ResponseEntity<?> myTravelInfoReview(int travelId) {
        return ResponseEntity.ok(travelService.findTravelByTravelId(travelId));
    }

    @PutMapping("/update/{travelId}")
    public ResponseEntity<?> updateTravel(@PathVariable int travelId, @RequestBody TravelUpdateReqDto travelUpdateReqDto) {
        travelService.updateTravel(travelId, travelUpdateReqDto);
        return ResponseEntity.ok(DataRespDto.ofDefault());
    }

    @DeleteMapping("/{travelId}")
    public ResponseEntity<?> deleteTravel(@PathVariable int travelId) {
        System.out.println(travelId);
        return ResponseEntity.ok(travelService.deleteTravelPlan(travelId));
    }
    
    
}
