package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.MsgCodeResponseDto;
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

    //private final LoveService loveService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Object> createdComment(@PathVariable Long postId,
        @RequestBody CommentRequestDto commentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(
            commentService.createdComment(postId, commentRequestDto, userDetails.getUser()));
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Object> updateComment(@PathVariable Long postId,
        @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(
            commentService.update(postId, commentId, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long postId, @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(commentService.deleteComment(postId, commentId, userDetails.getUser()));
    }

    @PutMapping("/comments/{id}/loves")
    public ResponseEntity<Map<String, HttpStatus>> BoardLoveOk(@PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.loveOk(id, userDetails.getUser());
    }

//    @PutMapping("/posts/{postId}/comments/{commentId}/loves")
//    public ResponseEntity<Object> loveComment(@PathVariable Long commentId,
//        @AuthenticationPrincipal UserDetailsImpl details, @PathVariable String postId) {
//
//        boolean checkedLove = loveService.loveOnComment(commentId,details.getUser());
//
//
//        return checkedLove ?
//            ResponseEntity.ok(new MsgCodeResponseDto("좋아요 on")) :
//            ResponseEntity.ok(new MsgCodeResponseDto("좋아요 off"));
//    }

}
