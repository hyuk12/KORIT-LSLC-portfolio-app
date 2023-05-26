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
    
    private String convertFilePathToUrl(String tempName) {
  		return "http://localhost:8080/image/region/" + tempName;
  	}
    
    public RegionRespDto toDto() {
    	return RegionRespDto.builder()
    			.regionId(regionId)
    			.regionName(regionName)
    			.regionEngName(regionEngName)
    			.regionDescription(regionDescription)
    			.regionImgId(mainImage.getRegionImgId())
    			.regionImgUrl(convertFilePathToUrl(mainImage.getTempName()))
    			.build();
    }
}
