package com.korea.triplocation.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.korea.triplocation.api.dto.request.MainImageSaveDto;
import com.korea.triplocation.service.AdminService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/post/register")
    public ResponseEntity<?> mainImageSave(MainImageSaveDto mainImageSaveDto) {
        return ResponseEntity.ok(adminService.insertRegion(mainImageSaveDto));
    }
    
    @GetMapping("/post")
    public ResponseEntity<?> getRegions(){
    	return ResponseEntity.ok(adminService.getRegions());
    }

    @GetMapping("/locations/popular")
    public ResponseEntity<?> getPopularLocations() {
        return ResponseEntity.ok(adminService.getPopularLocations());
    }
}
