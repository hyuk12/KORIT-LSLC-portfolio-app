package com.korea.triplocation.domain.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainImage {
    private int regionImgId;
    private int regionId;
    private String originName;
    private String tempName;
    private String imgSize;


}
