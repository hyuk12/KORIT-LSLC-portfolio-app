package com.korea.triplocation.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class LoginReqDto {
    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함한 8~16자리로 입력해주세요.")
    private String password;
    
    @Override
    public String toString() {
    	return "email: " + email + "passowrd: " + password;
    }

}
