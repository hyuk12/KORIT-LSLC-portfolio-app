package com.korea.triplocation.domain.travel.entity;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelRoutes {
    private int routeId;
    private int scheduleId;
    private int locationId;

}
