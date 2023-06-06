package com.korea.triplocation.api.dto.request.login;

import lombok.Data;

@Data
public class OAuth2ProviderMergeReqDto {
    private String email;
    private String password;
    private String provider;
}
