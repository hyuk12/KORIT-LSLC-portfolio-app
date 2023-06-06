package com.korea.triplocation.service;

import com.korea.triplocation.api.dto.request.travel.LocationReqDto;
import com.korea.triplocation.api.dto.request.travel.PartyDataReqDto;
import com.korea.triplocation.api.dto.request.travel.TravelPlanReqDto;
import com.korea.triplocation.api.dto.request.travel.TravelUpdateReqDto;
import com.korea.triplocation.api.dto.response.MyTravelInfoRespDto;
import com.korea.triplocation.domain.travel.entity.*;
import com.korea.triplocation.repository.TravelRepository;
import com.korea.triplocation.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelService {
    private static final String IMAGE_URL = "http://localhost:8080/image/region/";

    private final TravelRepository travelRepository;

    public void travelSave(List<TravelPlanReqDto> travels) {
        String travelName = UUID.randomUUID().toString();
        Integer travelId = null;

        for (TravelPlanReqDto dto : travels) {
            if (dto == null || dto.getLocation() == null || dto.getPartyData().isEmpty()) continue;

            travelId = saveTravelData(travelName, travelId, dto);
        }
    }

    private Integer saveTravelData(String travelName, Integer travelId, TravelPlanReqDto dto){
        for (LocationReqDto location : dto.getLocation()) {
            for (PartyDataReqDto party : dto.getPartyData()) {
                travelId = travelRepository.callInsertTravelData(
                        (travelId == null) ? travelName : null,
                        location.getAddr(),
                        location.getLat(),
                        location.getLng(),
                        party.getUserId(),
                        dto.getDate()
                );
            }
        }
        return travelId;
    }

    private String convertFilePathToUrl(String tempName) {
        return IMAGE_URL + tempName;
    }

    public List<Travels> findTravelByUser() {
        PrincipalUser principal = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getUserId() == 0) return null;

        return setTravelImages(travelRepository.findTravelAllByUser(principal.getUserId()));
    }

    private List<Travels> setTravelImages(List<Travels> travels){
        for (Travels travel : travels) {
            for (Region region : travel.getRegions()) {
                region.setRegionImgUrl(getImgUrl(region));
            }
        }
        return travels;
    }

    private String getImgUrl(Region region){
        MainImage mainImage = null;
        String imgUrl = null;

        if (region.getRegionImgId() != -1) {
            mainImage = travelRepository.getMainImgById(region.getRegionImgId());
            if (mainImage != null) {
                imgUrl = convertFilePathToUrl(mainImage.getTempName());
            }
        } else {
            imgUrl = convertFilePathToUrl("default.png");
        }

        return imgUrl;
    }

    private MyTravelInfoRespDto getTravelInfo(Travels travels){
        return MyTravelInfoRespDto.builder()
                .schedules(travels.getSchedules())
                .build();
    }

    public MyTravelInfoRespDto findTravelInfoByTravelId(int travelId) {
        PrincipalUser principal = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getTravelInfo(travelRepository.findTravelByTravelIdAndUserId(principal.getUserId(), travelId));
    }

    public MyTravelInfoRespDto findReviewTravelInfoByTravelIdAndReviewId(int reviewId, int travelId) {
        return getTravelInfo(travelRepository.findReviewTravelByReviewIdAndTravelId(reviewId, travelId));
    }

    public MyTravelInfoRespDto findTravelByTravelId(int travelId) {
        return getTravelInfo(travelRepository.findTravelByTravelId(travelId));
    }

    public void updateTravel(int travelId, TravelUpdateReqDto travelUpdateReqDto) {
        Travels travels = travelRepository.findTravelByTravelId(travelId);
        if(travels == null) return;

        updateTravelData(travelUpdateReqDto);
    }

    private void updateTravelData(TravelUpdateReqDto travelUpdateReqDto){
        for(Schedule schedule : travelUpdateReqDto.getSchedules()) {
            for (Location location: schedule.getLocations()) {
                travelRepository.updateTravelData(location.getLocationId(), location.getAddr(), location.getLat(), location.getLng());
            }
        }
    }

    public int deleteTravelPlan(int travelId) {
        PrincipalUser principal = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return deleteTravelPlanForUser(principal, travelId);
    }

    private int deleteTravelPlanForUser(PrincipalUser principal, int travelId){
        for (Travels travels : travelRepository.findTravelAllByUser(principal.getUserId())) {
            if(deleteTravelPlanForParticipant(travels, principal, travelId)) return 1;
        }
        return -1;
    }

    private boolean deleteTravelPlanForParticipant(Travels travels, PrincipalUser principal, int travelId){
        for (Participant participant: travels.getParticipants()) {
            if(participant.getTravelId() == travelId && participant.getUserId() == principal.getUserId()) {
                Participant participantIdByUserIdAndTravelId = travelRepository.findParticipantIdByUserIdAndTravelId(principal.getUserId(), travelId);
                if (participantIdByUserIdAndTravelId != null) {
                    travelRepository.deleteTravelPlanByParty(participantIdByUserIdAndTravelId.getParticipantId());
                    return true;
                }
            }
        }
        return false;
    }
}
