package com.korea.triplocation.api.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.korea.triplocation.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModifyReqDto {
	
    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Pattern(regexp = "^[가-힣]{2,7}$",
            message = "이름은 한글 2~7자리로 입력해주세요.")
    private String name;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$",
            message = "전화번호를 형식에 맞게 입력해주세요.")
    private String phone;
    private String address;
    private MultipartFile profileImg;

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .address(address)
                .build();

    }

}
