package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.comment.ReqCommentDto;
import com.example.cooperation_project.dto.comment.RespCommentDto;
import com.example.cooperation_project.security.UserDetailsImpl;
import com.example.cooperation_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chitchat")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Object> createdComment(@PathVariable Long postId,
        @RequestBody ReqCommentDto reqCommentDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(
            commentService.createdComment(postId, reqCommentDto, userDetails.getUser()));
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Object> updateComment(@PathVariable Long postId,
        @PathVariable Long commentId, @RequestBody ReqCommentDto requestDto,
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
    @GetMapping("/{postId}/comments")
    public List<RespCommentDto> getComment (@PathVariable Long postId){
        return commentService.getComment(postId);
    }

}
