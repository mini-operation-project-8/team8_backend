package com.example.cooperation_project.entity;

import com.example.cooperation_project.dto.comment.CommentRequestDto;
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
    private int love = 0;
    @ManyToOne
    @JoinColumn(name = "USERID",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "POSTID",nullable = false)
    private Post post;

    public Comment(CommentRequestDto requestDto, Post post, User user) {
        this.contents = requestDto.getContents();
        this.user = user;
        this.post = post;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.contents = commentRequestDto.getContents();
    }

    public void LoveOk() {
        this.love++;
    }
    public void LoveCancel() {
        this.love--;
    }
}
