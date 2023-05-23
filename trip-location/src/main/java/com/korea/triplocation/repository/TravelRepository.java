package com.korea.triplocation.repository;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.domain.travel.entity.MainImage;
import com.korea.triplocation.domain.travel.entity.Travels;

@Mapper
public interface TravelRepository {

	
    MainImage findMainImageByTravelName(String travelName);
    Travels findTravelAllByUser(int userId);
    Travels findTravelByTravelId(int travelId);
    int callInsertTravelData(String travelName, String addr, double lat, double lng, int userId, LocalDate scheduleDate);


}
