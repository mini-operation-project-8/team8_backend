package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.CommentRequestDto;
import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.security.UserDetailsImpl;
import com.example.cooperation_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chitchat")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/comments")
    public MsgCodeResponseDto createdComment (@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createdComment(commentRequestDto,userDetails.getUser());
    }



}
