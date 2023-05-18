package com.korea.triplocation.service;

import com.korea.triplocation.api.dto.request.LocationReqDto;
import com.korea.triplocation.api.dto.request.TravelPlanReqDto;
import com.korea.triplocation.domain.travel.entity.Location;
import com.korea.triplocation.domain.travel.entity.Schedule;
import com.korea.triplocation.domain.travel.entity.TravelRoutes;
import com.korea.triplocation.domain.travel.entity.Travels;
import com.korea.triplocation.repository.contents.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;

    public void travelSave(List<TravelPlanReqDto> travels) {
        for (TravelPlanReqDto dto : travels) {
            System.out.println(dto.getLocations());
            for (LocationReqDto locationData :dto.getLocations()) {
                travelRepository.callInsertTravelData(
                        "test", // Travel name
                        locationData.getAddr(),
                        locationData.getLat(),
                        locationData.getLng(),
                        dto.getId(), // Schedule ID
                        dto.getDate() // Visit date
                );
            }
        }
    }

}
