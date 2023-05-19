package com.korea.triplocation.api.dto.request;

import com.korea.triplocation.domain.travel.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainImageSaveDto {
    private String title;
    private String name;
    private String content;
    private MultipartFile imgFiles;

    public Region toEntity() {
        return Region.builder()
                .regionName(title)
                .regionEngName(name)
                .regionDescription(content)
                .build();
    }
}
