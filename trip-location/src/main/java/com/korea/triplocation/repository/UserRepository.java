package com.korea.triplocation.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.domain.user.entity.User;

@Mapper
public interface UserRepository {
	
	public User getUserById(int userId);
	public List<User> getUserAll();
	public User searchUserByEmail(String email);
	public User searchUserByPhone(String phone);
	public void modifyUser(User user);
	public void deleteUser(int userId);


}
