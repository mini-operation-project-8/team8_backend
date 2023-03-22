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
    private Long numOfLove = 0L;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @Exclude
    private List<Comment> commentList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, User user){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.user = user;
    }
    public void LoveOk() {
        this.numOfLove++;
    }

    public void LoveCancel() {
        this.numOfLove--;
    }

    public void update(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }


}
