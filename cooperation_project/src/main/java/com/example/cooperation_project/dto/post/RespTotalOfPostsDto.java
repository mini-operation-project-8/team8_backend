package com.example.cooperation_project.dto.post;

import lombok.Getter;

@Getter
public class RespTotalOfPostsDto {

    private final Long totalPosts;

    public RespTotalOfPostsDto(Long totalPosts) {
        this.totalPosts = totalPosts;
    }
}
