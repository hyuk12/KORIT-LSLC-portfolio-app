package com.korea.triplocation.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegionRespDto {
	private int regionId;
	private String regionName;
	private String regionEngName;
	private String regionDescription;
	private String regionImgUrl;
}
