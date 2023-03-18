package com.example.cooperation_project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<LovePost> lovePostList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<LoveComment> loveCommentList;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

//    public User(String userId, String password, UserRoleEnum role){
//        this.userId = userId;
//        this.password = password;
//        this.role = role;
//    }

}
