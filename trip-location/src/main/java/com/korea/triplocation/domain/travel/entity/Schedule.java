package com.korea.triplocation.domain.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private int scheduleId;
    private int travelId;
    private LocalDate scheduleDate;

}
