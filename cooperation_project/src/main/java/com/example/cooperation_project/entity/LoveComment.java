package com.example.cooperation_project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LoveComment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "COMMENTID",nullable = false)
    private Comment comment;
    @Column
    private boolean isLove = false;

    public LoveComment(Comment comment, User user) {
        this.comment = comment;
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
