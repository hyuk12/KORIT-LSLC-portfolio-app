package com.korea.triplocation.repository;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.domain.user.entity.Authority;
import com.korea.triplocation.domain.user.entity.User;

@Mapper
public interface AuthRepository {

	public int saveUser(User user);
	public int saveAuthority(Authority authority);
	public User findUserByEmail(String email);

    int updateProvider(User userEntity);
}
