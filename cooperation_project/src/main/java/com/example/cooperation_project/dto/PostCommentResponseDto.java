package com.example.cooperation_project.dto;

import com.example.cooperation_project.entity.Comment;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostCommentResponseDto {
    private Long post_Id;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

    public PostCommentResponseDto(Post post){
        this.post_Id = post.getPost_Id();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUser().getId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        for(Comment comment : post.getCommentList()){
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
    }
}
