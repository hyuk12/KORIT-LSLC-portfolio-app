package com.korea.triplocation.security.jwt;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.korea.triplocation.security.PrincipalUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.korea.triplocation.api.dto.response.JwtRespDto;
import com.korea.triplocation.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;


@Component
@Slf4j
public class JwtTokenProvider {
	
	private final Key key;

	public JwtTokenProvider(@Value("${jwt.secret}")  String secretKey) {
		key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	public JwtRespDto generateToken(Authentication authentication) {
		
		StringBuilder builder = new StringBuilder();
		
		authentication.getAuthorities().forEach(authority -> {
			builder.append(authority.getAuthority() + ",");
		});
		builder.delete(builder.length() - 1 , builder.length());
		
		String authorities = builder.toString();
		
		Date tokenExpireDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));		//프로젝트 완성후 만료시간 수정하기
		
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim("auth", authorities)
				.setExpiration(tokenExpireDate)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		return	JwtRespDto.builder().grantType("Bearer").accessToken(accessToken).build(); 
	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			
			return true;
		} catch (SecurityException | MalformedJwtException e) {	
			log.info("Invalid JWT Token", e);
			
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
			
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
			
		} catch (IllegalArgumentException e) {
			log.info("IllegalArgument JWT Token", e);
			
		} catch (Exception e) {
			log.info("JWT Token Error", e);
		}
		
		return false;
					
	}
	
	public String getToken(String token) {
		System.out.println(token);
		String type = "Bearer ";
		if(StringUtils.hasText(token) && token.startsWith(type)) {
			return token.substring(type.length());
		}
		return null;	
	}
	
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
	}
	
	public Authentication getAuthentication(String accessToken) {
		Authentication authentication = null;
		
		Claims claims = getClaims(accessToken);
		if(claims.get("auth") == null) {
			throw new CustomException("AccessToken에 관한 정보 없음!");
		}
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		String auth = claims.get("auth").toString();
		for(String role : auth.split(",")) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
		UserDetails userDetails = new User(claims.getSubject(), "", authorities);
		
		authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
		
		return authentication;
	}

	public String generateOAuth2RegisterToken(Authentication authentication) {
		Date tokenExpiresDate = new Date(new Date().getTime() + (1000 * 60 * 10));

		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		String email = oAuth2User.getAttribute("email");



		return Jwts.builder()
				.setSubject("OAuth2Register")
				.claim("email", email)
				.setExpiration(tokenExpiresDate)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();

	}

	public String generateAccessToken(Authentication authentication) {
		String email = null;

		if(authentication.getPrincipal().getClass() == UserDetails.class) {
			// Principal User
			PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
			email = principalUser.getEmail();
		}else {
			// Oauth2 User
			OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
			email = oAuth2User.getAttribute("email");
		}

		if(authentication.getAuthorities() == null) {
			throw new RuntimeException("등록된 권한이 없습니다.");
		}
		StringBuilder roles = new StringBuilder();


		authentication.getAuthorities().forEach(authority -> {
			roles.append(authority.getAuthority() + ",");
		});

		roles.delete(roles.length() - 1, roles.length());
		Date tokenExpiresDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));

		return Jwts.builder()
				.setSubject("AccessToken")
				.claim("email", email)
				.claim("auth", roles)
				.setExpiration(tokenExpiresDate)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
}
