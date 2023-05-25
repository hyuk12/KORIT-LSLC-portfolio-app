package com.korea.triplocation.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRespDto {
	
	private int travelId;
	private String startDate;
	private String endDate;
}
