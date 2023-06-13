package com.korea.triplocation.api.dto.request.login;

import com.korea.triplocation.domain.user.entity.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class OAuth2RegisterReqDto {
    private String email;
    private String name;
    private String password;
    private String checkPassword;
    private String phone;
    private String address;
    private int postsImgId;
    private String provider;

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .password(new BCryptPasswordEncoder().encode(password))
                .phone(phone)
                .address(address)
                .postsImgId(postsImgId)
                .provider(provider)
                .build();
    }
}