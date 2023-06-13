package com.korea.triplocation.domain.travel.entity;

import com.korea.triplocation.api.dto.response.RegionRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Region {
    private int regionId;
    private String regionName;
    private String regionEngName;
    private String regionDescription;
    private int regionImgId;
    private String regionImgUrl;
    
    private MainImage mainImage;

    public RegionRespDto toDto() {
        return RegionRespDto.builder()
                .regionId(regionId)
                .regionName(regionName)
                .regionEngName(regionEngName)
                .regionDescription(regionDescription)
                .build();
    }

}
