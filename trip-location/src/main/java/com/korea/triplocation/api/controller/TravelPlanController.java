package com.korea.triplocation.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korea.triplocation.api.dto.request.TravelPlanReqDto;
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

    @GetMapping("/list")//principal에서 자신의 userId로 자신의 모든 여행일정을 가져옴.
    public ResponseEntity<?> view(@RequestParam("userId") int userId) {
        return ResponseEntity.ok(travelService.findTravelByUserId(userId));
    }
    
    @GetMapping("/region")
    public ResponseEntity<?> findMainImage(@RequestParam("travelName") String travelName){
    	return ResponseEntity.ok(travelService.findMainImageByTravelName(travelName));
    }
    
    @GetMapping("/info")
    public ResponseEntity<?> myTravelInfo(int travelId) {
        return ResponseEntity.ok(travelService.findTravelInfoByTravelId(travelId));

    }
}
