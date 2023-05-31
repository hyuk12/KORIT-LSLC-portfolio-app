package com.korea.triplocation.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.korea.triplocation.api.dto.request.MainImageSaveDto;
import com.korea.triplocation.api.dto.response.RegionRespDto;
import com.korea.triplocation.domain.travel.entity.MainImage;
import com.korea.triplocation.domain.travel.entity.Region;
import com.korea.triplocation.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    @Value("${file.path}")
    private String filePath;

    public int insertRegion(MainImageSaveDto mainImageSaveDto) {
        adminRepository.saveRegion(mainImageSaveDto.toEntity());
        Region region = adminRepository.selectedRegion(mainImageSaveDto.getTitle());
        uploadFile(region.getRegionId(), mainImageSaveDto.getImgFiles());
        adminRepository.saveImage(uploadFile(region.getRegionId(), mainImageSaveDto.getImgFiles()));
        return 0;
    }

    private MainImage uploadFile(int regionId, MultipartFile file) {
        if(file == null) {
            return null;
        }

        String originFileName = file.getOriginalFilename();
        String extension = originFileName.substring(originFileName.lastIndexOf("."));
        String tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;

        Path uploadPath = Paths.get(filePath + "region/" + tempFileName);

        File f = new File(filePath + "region");

        if(!f.exists()) {
            f.mkdirs();
        }

        try {
            Files.write(uploadPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MainImage.builder()
                .regionId(regionId)
                .originName(originFileName)
                .tempName(tempFileName)
                .imgSize(Long.toString(file.getSize()))
                .build();
    }

    private String convertFilePathToUrl(String tempName) {
        return "http://localhost:8080/image/region/" + tempName;
    }

    public List<RegionRespDto> getRegions() {
        List<Region> regions = adminRepository.getRegions();
        List<MainImage> mainImages = adminRepository.getMainImages();
        List<RegionRespDto> dtos = new ArrayList<>();

        String imgUrl = null;

        for (Region region : regions) {
            for (MainImage image : mainImages) {
                if(region.getRegionId() == image.getRegionId()) {
                    RegionRespDto dto = region.toDto();
                    imgUrl = convertFilePathToUrl(image.getTempName());
                    dto.setRegionImgUrl(imgUrl);
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }
}
