package com.example.cooperation_project.dto.comment;

import com.example.cooperation_project.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long comment_Id;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String userId;

    public CommentResponseDto(Comment comment){
        this.comment_Id = comment.getComment_Id();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.userId = comment.getUser().getUserId();
    }

}
