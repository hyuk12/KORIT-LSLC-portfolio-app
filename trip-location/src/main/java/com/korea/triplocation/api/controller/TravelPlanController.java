package com.korea.triplocation.api.controller;

import com.korea.triplocation.api.dto.request.TravelPlanReqDto;
import com.korea.triplocation.api.dto.response.DataRespDto;
import com.korea.triplocation.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/travel")
@RequiredArgsConstructor
public class TravelPlanController {

    private final TravelService travelService;

    @PostMapping("/plan")
    public ResponseEntity<?> plan(@RequestBody List<TravelPlanReqDto> travels) {
        travelService.travelSave(travels);
        return ResponseEntity.ok(DataRespDto.ofDefault());
    }
}
