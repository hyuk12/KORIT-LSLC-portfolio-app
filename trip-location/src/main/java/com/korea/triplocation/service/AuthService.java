package com.korea.triplocation.service;

import com.korea.triplocation.api.dto.request.OAuth2ProviderMergeReqDto;
import com.korea.triplocation.api.dto.request.OAuth2RegisterReqDto;
import com.korea.triplocation.domain.user.entity.PostsImg;
import com.korea.triplocation.repository.UserRepository;
import com.korea.triplocation.security.oauth2.OAuth2Attribute;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.korea.triplocation.api.dto.request.LoginReqDto;
import com.korea.triplocation.api.dto.request.UserReqDto;
import com.korea.triplocation.api.dto.response.JwtRespDto;
import com.korea.triplocation.api.dto.response.PrincipalRespDto;
import com.korea.triplocation.domain.user.entity.Authority;
import com.korea.triplocation.domain.user.entity.User;
import com.korea.triplocation.exception.CustomException;
import com.korea.triplocation.exception.ErrorMap;
import com.korea.triplocation.repository.AuthRepository;
import com.korea.triplocation.security.jwt.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final AuthRepository authRepository;
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	
	public void checkDuplicatedByEmail(String email) {
		User userEntity = authRepository.findUserByEmail(email);
		
		if(userEntity != null) {
			throw new CustomException("Duplicated Email", 
					ErrorMap.builder()
					.put("email","이미 사용중인 이메일입니다.").build());
		}
	}
	
	public void signup(UserReqDto userReqDto) {
		User userEntity = userReqDto.toEntity();

		authRepository.saveUser(userEntity);
		authRepository.saveAuthority(
				Authority.builder().userId(userEntity.getUserId()).roleId(1).build());

	}

	public JwtRespDto signin(LoginReqDto loginReqDto) {

		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());
		Authentication authentication = 
				authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		return jwtTokenProvider.generateToken(authentication);
		
	}
	
	@Override
	public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException{
		User userEntity = authRepository.findUserByEmail(username);
		
		if(userEntity == null) {
			throw new CustomException("로그인 실패",
					ErrorMap.builder()
					.put("email", "사용자 정보를 확인하세요.")
					.build());
		}
		
		return userEntity.toPrincipal();
	}
	
	public boolean authenticated(String accessToken) {
		return jwtTokenProvider.validateToken(jwtTokenProvider.getToken(accessToken));
	}
	
	public PrincipalRespDto getPrincipal(String accessToken) {
		Claims claims = jwtTokenProvider.getClaims(jwtTokenProvider.getToken(accessToken));

		User userEntity = authRepository.findUserByEmail(claims.getSubject());
		String imageUrl = null;

		if(userEntity.getPostsImgId() != -1) {
			PostsImg postsImg = userRepository.getPostsImgById(userEntity.getPostsImgId());
			imageUrl = convertFilePathToUrl(postsImg.getTempName());
		}else {
			imageUrl = convertFilePathToUrl("default.png");
		}

		return PrincipalRespDto.builder()
				.userId(userEntity.getUserId())
				.email(userEntity.getEmail())
				.name(userEntity.getName())
				.phone(userEntity.getPhone())
				.address(userEntity.getAddress())
				.postsImgId(userEntity.getPostsImgId())
				.postsImgUrl(imageUrl)
				.authorities((String) claims.get("auth"))
				.provider(userEntity.getProvider())
				.build();
		
	}

	private String convertFilePathToUrl(String tempName) {
		return "http://localhost:8080/image/user/" + tempName;
	}

	public int oauth2Register(OAuth2RegisterReqDto oAuth2RegisterReqDto) {
		User userEntity = oAuth2RegisterReqDto.toEntity();

		authRepository.saveUser(userEntity);
		return authRepository.saveAuthority(
				Authority.builder()
						.userId(userEntity.getUserId())
						.roleId(1)
						.build());
	}

	public boolean checkPassword(String email, String password) {
		User userEntity = authRepository.findUserByEmail(email);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		return passwordEncoder.matches(password, userEntity.getPassword());
	}

	public int oAuth2ProviderMerge(OAuth2ProviderMergeReqDto oAuth2ProviderMergeReqDto) {
		User userEntity = authRepository.findUserByEmail(oAuth2ProviderMergeReqDto.getEmail());

		String provider = oAuth2ProviderMergeReqDto.getProvider();

		if(StringUtils.hasText(userEntity.getProvider())) {
			userEntity.setProvider(userEntity.getProvider() + "," + provider);

		}else {
			userEntity.setProvider(provider);
		}

		return authRepository.updateProvider(userEntity);
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, oAuth2User.getAttributes());
		Map<String, Object> userAttribute = oAuth2Attribute.convertToMap();

		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), userAttribute, "email");
	}
}
