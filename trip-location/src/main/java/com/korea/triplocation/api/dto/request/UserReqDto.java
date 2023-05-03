package com.korea.triplocation.api.dto.request;

import com.korea.triplocation.entity.User;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReqDto {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String profileImg;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .name(name)
                .phone(phone)
                .address(address)
                .profileImg(profileImg)
                .build();

    }
}
