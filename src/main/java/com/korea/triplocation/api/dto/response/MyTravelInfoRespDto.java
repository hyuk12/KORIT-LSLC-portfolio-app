package com.korea.triplocation.api.dto.response;

import com.korea.triplocation.domain.travel.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyTravelInfoRespDto {
   private List<Schedule> schedules;
}

