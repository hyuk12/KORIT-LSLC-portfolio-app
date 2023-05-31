package com.korea.triplocation.api.controller;

import java.util.List;

import com.korea.triplocation.api.dto.request.TravelUpdateReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")//principal에서 자신의 userId로 자신의 모든 여행일정과 img가져오기
    public ResponseEntity<?> view(@RequestParam("userId") int userId) {
        return ResponseEntity.ok(travelService.findTravelByUser(userId));
    }

    @GetMapping("/info")
    public ResponseEntity<?> myTravelInfo(int userId, int travelId) {
        return ResponseEntity.ok(travelService.findTravelInfoByTravelId(userId, travelId));
    }

    @PutMapping("/update/{travelId}")
    public ResponseEntity<?> updateTravel(@PathVariable int travelId, @RequestBody TravelUpdateReqDto travelUpdateReqDto) {
        travelService.updateTravel(travelId, travelUpdateReqDto);
        return ResponseEntity.ok(DataRespDto.ofDefault());
    }
}
