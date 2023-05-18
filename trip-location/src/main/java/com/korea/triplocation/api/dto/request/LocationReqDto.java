package com.korea.triplocation.api.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class LocationReqDto {
    private String addr;
    private double lat;
    private double lng;

}
