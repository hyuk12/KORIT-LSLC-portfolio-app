package com.korea.triplocation.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.api.dto.response.ScheduleRespDto;
import com.korea.triplocation.domain.travel.entity.MainImage;
import com.korea.triplocation.domain.travel.entity.Region;
import com.korea.triplocation.domain.travel.entity.Travels;

@Mapper
public interface TravelRepository {
	Region findRegionByTravelName(String travelName);
	String getRegion(String regionName);

    MainImage getMainImgById(int regionImgId);
    List<Travels> findTravelAllByUser(int userId);
    Travels findTravelByTravelId( String travelId);
    int callInsertTravelData(String travelName, String addr, double lat, double lng, int userId, LocalDate scheduleDate);
    int updateTravelData(int locationId, String address, double lat, double lng);
    Travels findTravelByTravelIdAndUserId(int userId, int travelId);
}
