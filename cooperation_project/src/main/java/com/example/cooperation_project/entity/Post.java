package com.example.cooperation_project.entity;

import com.example.cooperation_project.dto.post.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.ToString.Exclude;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{

    @Id @Column(name = "post_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @Column
    private int love = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @Exclude
    private List<Comment> commentList = new ArrayList<>();

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Post(PostRequestDto postRequestDto, User user){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.user = user;
    }
    public void LoveOk() {
        this.love++;
    }

    public void LoveCancel() {
        this.love--;
    }

    public void update(PostRequestDto postRequestDto){

        this.content = postRequestDto.getContent();
    }


}
