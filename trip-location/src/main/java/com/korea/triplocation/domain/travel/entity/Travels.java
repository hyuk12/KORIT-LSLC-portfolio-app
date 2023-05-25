package com.korea.triplocation.domain.travel.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Travels {
    private int travelId;
    private String travelName;

    private List<Participant> participants;
    private List<Schedule> schedules;
    
    private Region region;
}
