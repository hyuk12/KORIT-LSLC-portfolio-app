package com.korea.triplocation.api.dto.request.travel;

import com.korea.triplocation.domain.travel.entity.Schedule;
import lombok.Data;

import java.util.List;

@Data
public class TravelUpdateReqDto {
    private List<Schedule> schedules;
}
