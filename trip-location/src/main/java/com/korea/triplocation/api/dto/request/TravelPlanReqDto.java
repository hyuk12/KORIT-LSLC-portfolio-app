package com.korea.triplocation.api.dto.request;

import com.korea.triplocation.domain.travel.entity.Schedule;
import com.korea.triplocation.domain.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelPlanReqDto {
    private int id;
    private String date;
    private List<LocationReqDto> locations;



}
