package com.korea.triplocation.api.dto.request;

import com.korea.triplocation.domain.user.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelPlanReqDto {
    private String travelName;
    private LocalDate startDate;
    private LocalDate endDate;

    private String destinationName;
    private String locationX;
    private String locationY;
    private LocalDate visitDate;
    private User user;
}
