package com.example.cooperation_project.dto.post;

import com.example.cooperation_project.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import lombok.ToString;

@Getter
@NoArgsConstructor @ToString
public class RespPostDto {
    private Long postId;
    private String title;
    private String content;
    private String userId;
    /*private Long numOfLove;*/
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public RespPostDto(Post post){
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        /*this.numOfLove = post.getNumOfLove();*/
        this.userId = post.getUser().getUserId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
