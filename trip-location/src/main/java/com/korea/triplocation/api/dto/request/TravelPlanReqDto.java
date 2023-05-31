package com.korea.triplocation.api.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelPlanReqDto {
    private int id;
    private LocalDate date;
    private List<LocationReqDto> location;
    private List<PartyDataReqDto> partyData;

    public void forEachLocation(Consumer<LocationReqDto> action) {
        for (LocationReqDto locationReqDto : location) {
            if (locationReqDto != null) {
                action.accept(locationReqDto);
            }
        }
    }

}
