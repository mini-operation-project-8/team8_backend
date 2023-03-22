package com.example.cooperation_project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
public class LovePost{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID",nullable = false)
    private Post post;
    @Column
    private boolean isLove = false;
    public LovePost(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public void update(){
        if(this.isLove == false){
            this.isLove = true;
        }else{
            this.isLove = false;
        }
    }
}
