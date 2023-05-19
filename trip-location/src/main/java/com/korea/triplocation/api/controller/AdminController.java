package com.korea.triplocation.api.controller;

import com.korea.triplocation.api.dto.request.MainImageSaveDto;
import com.korea.triplocation.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/post/register")
    public ResponseEntity<?> mainImageSave(MainImageSaveDto mainImageSaveDto) {
        return ResponseEntity.ok(adminService.insertRegion(mainImageSaveDto));
    }
}
