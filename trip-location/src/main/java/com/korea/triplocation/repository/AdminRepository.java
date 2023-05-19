package com.korea.triplocation.repository;

import com.korea.triplocation.domain.travel.entity.MainImage;
import com.korea.triplocation.domain.travel.entity.Region;
import com.korea.triplocation.domain.user.entity.PostsImg;
import com.korea.triplocation.domain.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminRepository {
	public int saveRegion(Region region);
	public Region selectedRegion(String regionName);
	public int saveImage(MainImage mainImage);
}
