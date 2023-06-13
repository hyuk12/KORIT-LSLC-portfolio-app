package com.korea.triplocation.domain.travel.entity;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participant {
    private int participantId;
    private int travelId;
    private int userId;
}
