package com.korea.triplocation.service;

import com.korea.triplocation.api.dto.request.MainImageSaveDto;
import com.korea.triplocation.domain.travel.entity.MainImage;
import com.korea.triplocation.domain.travel.entity.Region;
import com.korea.triplocation.domain.user.entity.PostsImg;
import com.korea.triplocation.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
}
