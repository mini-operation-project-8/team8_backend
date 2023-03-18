package com.example.cooperation_project.entity;

import com.example.cooperation_project.dto.CommentRequestDto;
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
    private Long comment_Id;

    @Column(nullable = false)
    private String contents;

    @Column
    private int love = 0;
    @ManyToOne
    @JoinColumn(name = "USER_ID",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID",nullable = false)
    private Post post;

    public Comment(String contents, User user) {
        this.contents = contents;
        this.user = user;
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
