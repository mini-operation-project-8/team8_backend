package com.example.cooperation_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    private Long post_Id;
    private String contents;

}
