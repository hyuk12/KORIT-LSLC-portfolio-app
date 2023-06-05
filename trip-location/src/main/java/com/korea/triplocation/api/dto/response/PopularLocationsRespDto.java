package com.korea.triplocation.api.dto.response;

import lombok.Data;

@Data
public class PopularLocationsRespDto {
    private String regionName;
    private int travelCount;
}
