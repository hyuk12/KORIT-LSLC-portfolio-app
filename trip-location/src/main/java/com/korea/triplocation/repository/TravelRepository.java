package com.korea.triplocation.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.domain.travel.entity.MainImage;
import com.korea.triplocation.domain.travel.entity.Region;
import com.korea.triplocation.domain.travel.entity.Travels;

@Mapper
public interface TravelRepository {

	Region findMainImageByTravelName(String travelName);
    MainImage getMainImgById(int regionImgId);
    List<Travels> findTravelAllByUser(int userId);
    Travels findTravelByTravelId(int userId, int travelId);
    int callInsertTravelData(String travelName, String addr, double lat, double lng, int userId, LocalDate scheduleDate);
    int callUpdateTravelData();

}
