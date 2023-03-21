package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.comment.CommentRequestDto;
import com.example.cooperation_project.security.UserDetailsImpl;
import com.example.cooperation_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chitchat")
public class CommentController {

    private final CommentService commentService;

    //코멘트 작성
    @PostMapping("/{post_Id}/comments")
    public ResponseEntity<Object> createdComment(@PathVariable Long postId,
                                                 @RequestBody CommentRequestDto commentRequestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(commentService.createdComment(postId,commentRequestDto, userDetails.getUser()));
    }

    @PatchMapping("/{post_Id}/comments/{comment_Id}")
    public ResponseEntity<Object> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                @RequestBody CommentRequestDto requestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.update(postId, commentId, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{post_Id}/comments/{comment_Id}")
    public ResponseEntity deleteComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(postId, commentId, userDetails.getUser()));
    }                               // NO CONTENT를 하면 빈칸이 나온다.

    @PutMapping("/comments/{id}/loves")
    public ResponseEntity<Map<String, HttpStatus>> BoardLoveOk(@PathVariable Long id,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.loveOk(id, userDetails.getUser());
    }

}
