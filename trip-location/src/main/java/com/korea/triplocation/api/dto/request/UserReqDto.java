package com.korea.triplocation.api.dto.request;

import com.korea.triplocation.domain.user.entity.User;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReqDto {
    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함한 8~16자리로 입력해주세요.")
    private String password;

    @Pattern(regexp = "^[가-힣]{2,7}$",
            message = "이름은 한글 2~7자리로 입력해주세요.")
    private String name;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$",
            message = "전화번호를 형식에 맞게 입력해주세요.")
    private String phone;
    private String address;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .name(name)
                .phone(phone)
                .address(address)
                .postsImgId(-1)
                .build();

    }
}
