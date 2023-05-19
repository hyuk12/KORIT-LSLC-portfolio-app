package com.korea.triplocation.domain.travel.entity;

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
}
