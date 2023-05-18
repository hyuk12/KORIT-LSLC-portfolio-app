package com.korea.triplocation.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostsImg {
    private int postsImgId;
    private int userId;
    private String originName;
    private String tempName;
    private String imgSize;


}
