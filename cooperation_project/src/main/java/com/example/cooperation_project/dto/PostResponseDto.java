package com.example.cooperation_project.dto;

import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long post_Id;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post){
        this.post_Id = post.getPost_Id();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUser().getUserId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
