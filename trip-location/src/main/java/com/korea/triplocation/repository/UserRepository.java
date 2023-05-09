package com.korea.triplocation.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.domain.user.entity.User;

@Mapper
public interface UserRepository {
	
	public List<User> getUserAll();


}
