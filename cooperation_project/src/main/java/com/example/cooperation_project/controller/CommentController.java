package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.CommentRequestDto;
import com.example.cooperation_project.dto.CommentResponseDto;
import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.exception.NotFoundPostException;
import com.example.cooperation_project.exception.NotFoundUserException;
import com.example.cooperation_project.security.UserDetailsImpl;
import com.example.cooperation_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chitchat")
public class CommentController {

    private final CommentService commentService;

    //코멘트 작성
    @PostMapping("/{post_Id}/comments")
    public ResponseEntity<Object> createdComment(@PathVariable Long post_Id,
                                                 @RequestBody CommentRequestDto commentRequestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(commentService.createdComment(post_Id,commentRequestDto, userDetails.getUser()));
    }

    @PatchMapping("/{post_Id}/comments")
    public CommentResponseDto updateComment(@PathVariable Long post_Id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(post_Id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/{post_Id}/comments")
    public ResponseEntity<Map<String, HttpStatus>> deleteComment(@PathVariable Long post_Id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseEntity<Map<String, HttpStatus>> responseEntity = commentService.deleteComment(post_Id, userDetails.getUser());
        return responseEntity;
    }

    @PutMapping("/comments/{id}/loves")
    public ResponseEntity<Map<String, HttpStatus>> BoardLoveOk(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.loveOk(id, userDetails.getUser());
    }

}
