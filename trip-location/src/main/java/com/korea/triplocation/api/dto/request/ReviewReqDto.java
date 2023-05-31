package com.korea.triplocation.api.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReviewReqDto {
    private int userId;
    private int travelId;
    private String date;
    private String title;
    private List<MultipartFile> imgFiles;
    private String review;
}
