package com.korea.triplocation.service;

import com.korea.triplocation.api.dto.request.LocationReqDto;
import com.korea.triplocation.api.dto.request.PartyDataReqDto;
import com.korea.triplocation.api.dto.request.TravelPlanReqDto;
import com.korea.triplocation.domain.travel.entity.MainImage;
import com.korea.triplocation.domain.travel.entity.Region;
import com.korea.triplocation.domain.travel.entity.Travels;
import com.korea.triplocation.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;

    public void travelSave(List<TravelPlanReqDto> travels) {
        if (travels != null && !travels.isEmpty()) {
            String travelName = UUID.randomUUID().toString();
            Integer travelId = null;
            PartyDataReqDto partyDataReqDto = null;

            for (TravelPlanReqDto dto : travels) {
                if (dto != null && dto.getLocation() != null) {
                    List<LocationReqDto> locations = dto.getLocation();
                    List<PartyDataReqDto> partyData = dto.getPartyData();

                    for (int i = 0; i < partyData.size(); i++) {
                        partyDataReqDto = partyData.get(i);

                    }
                    for (int i = 0; i < locations.size(); i++) {
                        LocationReqDto locationReqDto = locations.get(i);
                        if (i == 0 && travelId == null) {
                            travelId = travelRepository.callInsertTravelData(
                                    travelName,
                                    locationReqDto.getAddr(),
                                    locationReqDto.getLat(),
                                    locationReqDto.getLng(),
                                    partyDataReqDto.getUserId(),
                                    dto.getDate() // Visit date
                            );
                        } else {
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

    public Travels findTravelByUserId(int userId) {
        return travelRepository.findTravelAllByUser(userId);
    }

    public MainImage findMainImageByTravelName(String travalName) {
    	return travelRepository.findMainImageByTravelName(travalName);
    }



}
