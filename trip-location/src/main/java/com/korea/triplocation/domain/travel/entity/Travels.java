package com.korea.triplocation.domain.travel.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Travels {
    private int travelId;
    private String travelName;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<TravelRoutes> travelRoutes;
    private List<Participant> participants;

}
