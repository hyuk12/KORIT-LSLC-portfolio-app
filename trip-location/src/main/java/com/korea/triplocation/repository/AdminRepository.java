package com.korea.triplocation.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.domain.travel.entity.MainImage;
import com.korea.triplocation.domain.travel.entity.Region;

//@Mapper
public interface AdminRepository {
	public int saveRegion(Region region);
	public Region selectedRegion(String regionName);
	public int saveImage(MainImage mainImage);
	public List<Region> getRegions();
	public List<MainImage> getMainImages();
}
