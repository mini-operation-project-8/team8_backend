package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.CommentRequestDto;
import com.example.cooperation_project.dto.CommentResponseDto;
import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.exception.NotFoundPostException;
import com.example.cooperation_project.exception.NotFoundUserException;
import com.example.cooperation_project.security.UserDetailsImpl;
import com.example.cooperation_project.service.CommentService;
import lombok.RequiredArgsConstructor;
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

    @PatchMapping("/{post_Id}/comments/{comment_Id}")
    public ResponseEntity<Object> updateComment(@PathVariable Long post_Id, @PathVariable Long comment_Id,
                                                @RequestBody CommentRequestDto requestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.update(post_Id, comment_Id, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{post_Id}/comments/{comment_Id}")
    public ResponseEntity deleteComment(@PathVariable Long post_Id, @PathVariable Long comment_Id,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(post_Id, comment_Id, userDetails.getUser()));
    }                               // NO CONTENT를 하면 빈칸이 나온다.

    @PutMapping("/comments/{id}/loves")
    public ResponseEntity<Map<String, HttpStatus>> BoardLoveOk(@PathVariable Long id,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.loveOk(id, userDetails.getUser());
    }

}
