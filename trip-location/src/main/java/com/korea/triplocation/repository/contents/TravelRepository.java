package com.korea.triplocation.repository.contents;

import com.korea.triplocation.domain.travel.entity.Location;
import com.korea.triplocation.domain.travel.entity.Schedule;
import com.korea.triplocation.domain.travel.entity.TravelRoutes;
import com.korea.triplocation.domain.travel.entity.Travels;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelRepository {
    int insertTravelData(Travels travels);
    int insertScheduleData(Schedule schedule);
    int insertLocationData(Location location);
    int insertRouteData(TravelRoutes travelRoutes);

    void callInsertTravelData(String test, String addr, double lat, double lng, int id, String date);
}
