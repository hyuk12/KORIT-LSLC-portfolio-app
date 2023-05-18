package com.korea.triplocation.api.dto.request;

import lombok.Data;

@Data
public class LocationReqDto {
    private String addr;
    private double lat;
    private double lng;


}
