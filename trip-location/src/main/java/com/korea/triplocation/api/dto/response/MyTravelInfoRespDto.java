package com.korea.triplocation.api.dto.response;

import com.korea.triplocation.domain.travel.entity.Location;
import com.korea.triplocation.domain.travel.entity.Schedule;
import com.korea.triplocation.domain.travel.entity.TravelRoutes;
import com.korea.triplocation.domain.travel.entity.Travels;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyTravelInfoRespDto {
   private List<Schedule> schedules;
}

