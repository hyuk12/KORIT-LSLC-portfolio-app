package com.korea.triplocation.domain.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private int locationId;
    private String addr;
    private double lat;
    private double lng;
    private int scheduleId;
}
