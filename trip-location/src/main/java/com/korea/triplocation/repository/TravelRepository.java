package com.korea.triplocation.repository;

import java.time.LocalDate;
import java.util.List;

import com.korea.triplocation.domain.travel.entity.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelRepository {
	public Region findRegionByTravelName(String travelName);
    public MainImage getMainImgById(int regionImgId);
    public List<Travels> findTravelAllByUser(int userId);
    public Travels findTravelByTravelId(int travelId);
    public int callInsertTravelData(String travelName, String addr, double lat, double lng, int userId, LocalDate scheduleDate);
    public int updateTravelData(int locationId, String address, double lat, double lng);
    public Travels findTravelByTravelIdAndUserId(int userId, int travelId);
    public Travels findReviewTravelByReviewIdAndTravelId(int reviewId, int travelId);

    public Participant findParticipantIdByUserIdAndTravelId(int userId, int travelId);
    public void deleteTravelPlanByParty(int participantId);
}
