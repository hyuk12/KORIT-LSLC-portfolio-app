package com.korea.triplocation.service;

import com.korea.triplocation.api.dto.request.LocationReqDto;
import com.korea.triplocation.api.dto.request.PartyDataReqDto;
import com.korea.triplocation.api.dto.request.TravelPlanReqDto;

import com.korea.triplocation.api.dto.request.TravelUpdateReqDto;
import com.korea.triplocation.domain.travel.entity.*;

import com.korea.triplocation.api.dto.response.MyTravelInfoRespDto;

import com.korea.triplocation.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;

    public void travelSave(List<TravelPlanReqDto> travels) {
        if (travels != null && !travels.isEmpty()) {
            String travelName = UUID.randomUUID().toString();
            Integer travelId = null;

            for (TravelPlanReqDto dto : travels) {
                if (dto != null && dto.getLocation() != null && !dto.getPartyData().isEmpty()) {
                    List<LocationReqDto> locations = dto.getLocation();
                    List<PartyDataReqDto> partyData = dto.getPartyData();

                    for (int i = 0; i < locations.size(); i++) {
                        LocationReqDto locationReqDto = locations.get(i);
                        for (PartyDataReqDto partyDataReqDto : partyData) {
                            System.out.println(partyDataReqDto.getUserId());

                            if (i == 0 && travelId == null) {
                                // Save the travel with the first location and first participant and get the travel ID
                                travelId = travelRepository.callInsertTravelData(
                                        travelName,
                                        locationReqDto.getAddr(),
                                        locationReqDto.getLat(),
                                        locationReqDto.getLng(),
                                        partyDataReqDto.getUserId(),
                                        dto.getDate() // Visit date
                                );
                            } else {
                                // Save the other locations with the same travel ID and all participants
                                travelRepository.callInsertTravelData(
                                        null,
                                        locationReqDto.getAddr(),
                                        locationReqDto.getLat(),
                                        locationReqDto.getLng(),
                                        partyDataReqDto.getUserId(),
                                        dto.getDate() // Visit date
                                );
                            }
                        }
                    }
                }
            }

        }
    }

    public Travels findTravelByUserId(int userId) {
        return travelRepository.findTravelAllByUser(userId);
    }


    public MainImage findMainImageByTravelName(String travalName) {
    	return travelRepository.findMainImageByTravelName(travalName);
    }

    public MyTravelInfoRespDto findTravelInfoByTravelId(int userId, int travelId) {
        Travels travelByTravelId = travelRepository.findTravelByTravelIdAndUserId(userId, travelId);

        return MyTravelInfoRespDto.builder()
                .schedules(travelByTravelId.getSchedules())
                .build();
    }


    public void updateTravel(String travelId, TravelUpdateReqDto travelUpdateReqDto) {
        Travels travels = travelRepository.findTravelByTravelId(travelId);
        if(travelRepository.findTravelByTravelId(travelId) != null) {
            for(Schedule schedule : travelUpdateReqDto.getSchedules()) {
                for (Location location: schedule.getLocations()) {
                    System.out.println(location.getAddr());
                    travelRepository.callUpdateTravelData(travels.getTravelId(), travels.getTravelName(), location.getAddr(), location.getLat(), location.getLng(), schedule.getScheduleId(), schedule.getScheduleDate());
                }
            };
        };

    }
}
