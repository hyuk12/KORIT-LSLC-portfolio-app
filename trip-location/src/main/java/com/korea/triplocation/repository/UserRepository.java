package com.korea.triplocation.repository;

import java.util.List;

import com.korea.triplocation.domain.user.entity.PostsImg;
import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.domain.user.entity.User;

@Mapper
public interface UserRepository {
	
	public User getUserById(int userId);
	public List<User> getUserAll();
	public User searchUserByEmail(String email);
	public User searchUserByPhone(String phone);
	public int modifyUser(User user);
	public int postsImg(PostsImg postsImg);
	public int resetPassword(User user);
	public int deleteUser(int userId);


}
