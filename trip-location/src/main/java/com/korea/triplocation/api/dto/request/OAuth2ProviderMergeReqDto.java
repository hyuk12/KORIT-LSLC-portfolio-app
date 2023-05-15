package com.korea.triplocation.api.dto.request;

import lombok.Data;

@Data
public class OAuth2ProviderMergeReqDto {
    private String email;
    private String password;
    private String provider;
}
