package com.korea.triplocation.repository;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.entity.User;

@Mapper
public interface UserRepository {

	public int saveUser(User user);
	public User findUserByEmail(String email);
}
