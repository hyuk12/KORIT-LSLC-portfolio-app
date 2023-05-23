package com.korea.triplocation.domain.travel.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelRoutes {
    private int routeId;
    private int scheduleId;
    private int locationId;

}
