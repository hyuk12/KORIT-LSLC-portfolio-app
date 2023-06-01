package com.korea.triplocation.service;

import com.korea.triplocation.api.dto.request.LocationReqDto;
import com.korea.triplocation.api.dto.request.PartyDataReqDto;
import com.korea.triplocation.api.dto.request.TravelPlanReqDto;
import com.korea.triplocation.api.dto.request.TravelUpdateReqDto;
import com.korea.triplocation.api.dto.response.MyTravelInfoRespDto;
import com.korea.triplocation.domain.travel.entity.*;
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
    
    private String convertFilePathToUrl(String tempName) {
  		return "http://localhost:8080/image/region/" + tempName;
  	}
    
    public List<Travels> findTravelByUser(int userId) {
    	if(userId == 0) {
    		return null;
    	}

    	List<Travels> travelsList = travelRepository.findTravelAllByUser(userId);
    	for (Travels travel : travelsList) {
    	    List<Region> regions = travel.getRegions();
    	    for (Region region : regions) {
    	        MainImage mainImage = null;
    	        String imgUrl = null;

    	        if (region.getRegionImgId() != -1) {
    	            mainImage = travelRepository.getMainImgById(region.getRegionImgId());
    	            if (mainImage != null) {
    	                imgUrl = convertFilePathToUrl(mainImage.getTempName());
    	                region.setRegionImgUrl(imgUrl);
    	            }
    	        } else {
    	            imgUrl = convertFilePathToUrl("default.png");
    	            region.setRegionImgUrl(imgUrl);
    	        }
    	    }
    	}
    	return travelsList;
    }

    public MyTravelInfoRespDto findTravelInfoByTravelId(int userId, int travelId) {
        Travels travelByTravelId = travelRepository.findTravelByTravelIdAndUserId(userId, travelId);

        return MyTravelInfoRespDto.builder()
                .schedules(travelByTravelId.getSchedules())
                .build();
    }

    public MyTravelInfoRespDto findTravelByTravelId( int travelId) {
        Travels travelByTravelId = travelRepository.findTravelByTravelId( travelId);

        return MyTravelInfoRespDto.builder()
                .schedules(travelByTravelId.getSchedules())
                .build();
    }

    public void updateTravel(int travelId, TravelUpdateReqDto travelUpdateReqDto) {
        Travels travels = travelRepository.findTravelByTravelId(travelId);
        if(travels != null) {
            for(Schedule schedule : travelUpdateReqDto.getSchedules()) {
                for (Location location: schedule.getLocations()) {
                    System.out.println(location.getAddr());
                    travelRepository.updateTravelData(location.getLocationId(), location.getAddr(), location.getLat(), location.getLng());
                }
            };
        };

    }
}
