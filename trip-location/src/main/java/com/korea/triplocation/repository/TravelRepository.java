package com.korea.triplocation.repository;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.domain.travel.entity.MainImage;
import com.korea.triplocation.domain.travel.entity.Travels;

@Mapper
public interface TravelRepository {

	
    MainImage findMainImageByTravelName(String travelName);
    Travels findTravelAllByUser(int userId);
    Travels findTravelByTravelIdAndUserId(int userId, int travelId);
    int callInsertTravelData(String travelName, String addr, double lat, double lng, int userId, LocalDate scheduleDate);
    int callUpdateTravelData(int travelId, String travelName, String addr, double lat, double lng, int scheduleId, LocalDate scheduleDate);

    Travels findTravelByTravelId(String travelId);
}
