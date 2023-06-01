package com.korea.triplocation.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.domain.travel.entity.Location;
import com.korea.triplocation.domain.travel.entity.MainImage;
import com.korea.triplocation.domain.travel.entity.Region;
import com.korea.triplocation.domain.travel.entity.Travels;

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
}
