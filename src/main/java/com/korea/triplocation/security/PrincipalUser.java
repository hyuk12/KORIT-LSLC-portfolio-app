package com.korea.triplocation.security;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.korea.triplocation.domain.user.entity.Authority;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Builder
@Data
public class PrincipalUser implements UserDetails, OAuth2User {

	private static final long serialVersionUID = -6984381303716862634L;
	private int userId;
	private String email;
	private String password;
	private int postsImgId;
	private List<Authority> authorities;
	private Map<String, Object> attributes;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		this.authorities.forEach(authority -> {
			authorities.add(new SimpleGrantedAuthority(authority.getRole().getRoleName()));
		});
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getName() {
		return email;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return new HashMap<>();
	}
}
