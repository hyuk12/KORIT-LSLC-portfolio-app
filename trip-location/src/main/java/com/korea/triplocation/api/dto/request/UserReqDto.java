package com.korea.triplocation.api.dto.request;

import com.korea.triplocation.entity.User;
import lombok.*;

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
    private String profileImage;

    public User toEntity() {


    }
}
