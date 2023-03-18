package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.CommentRequestDto;
import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.security.UserDetailsImpl;
import com.example.cooperation_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chitchat")
public class CommentController {

    private final CommentService commentService;


    //코멘트 작성
    @ResponseBody
    @PostMapping("/comments")
    public MsgCodeResponseDto createdComment (@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createdComment(commentRequestDto,userDetails.getUser());
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Map<String, HttpStatus>> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Map<String, HttpStatus>> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseEntity<Map<String,HttpStatus>> responseEntity = commentService.deleteComment(id,userDetails.getUser());
        return responseEntity;
    }

    @PutMapping("/comments/{id}/loves")
    public ResponseEntity<Map<String, HttpStatus>> BoardLoveOk(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.loveOk(id, userDetails.getUser());
    }

}
