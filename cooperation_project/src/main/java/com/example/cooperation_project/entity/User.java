package com.example.cooperation_project.entity;

import com.example.cooperation_project.dto.SignupRequestDto;
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
    private Long Id;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<LovePost> lovePostList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<LoveComment> loveCommentList;

    public User(String userId, String password, UserRoleEnum role){
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

}
