package com.korea.triplocation.api.dto.request;

import com.korea.triplocation.domain.travel.entity.Schedule;
import lombok.Data;

import java.util.List;

@Data
public class TravelUpdateReqDto {
    private List<Schedule> schedules;
}
