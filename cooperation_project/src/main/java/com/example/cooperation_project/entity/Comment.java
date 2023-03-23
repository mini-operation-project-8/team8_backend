package com.example.cooperation_project.entity;

import com.example.cooperation_project.dto.comment.ReqCommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String contents;

    @Column
    private Long numOfLove = 0L;
    @ManyToOne
    @JoinColumn(name = "USER_ID",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID",nullable = false)
    private Post post;

    public Comment(ReqCommentDto requestDto, Post post, User user) {
        this.contents = requestDto.getContents();
        this.user = user;
        this.post = post;
    }

    public void update(ReqCommentDto reqCommentDto) {
        this.contents = reqCommentDto.getContents();
    }

    public void LoveOk() {
        this.numOfLove++;
    }
    public void LoveCancel() {
        this.numOfLove--;
    }


}
