package com.korea.triplocation.repository;

import com.korea.triplocation.domain.travel.entity.Location;
import com.korea.triplocation.domain.travel.entity.Schedule;
import com.korea.triplocation.domain.travel.entity.TravelRoutes;
import com.korea.triplocation.domain.travel.entity.Travels;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface TravelRepository {
    Travels findTravelAllByUser(int userId);
    int callInsertTravelData(String travelName, String addr, double lat, double lng, int userId, LocalDate scheduleDate);

}
