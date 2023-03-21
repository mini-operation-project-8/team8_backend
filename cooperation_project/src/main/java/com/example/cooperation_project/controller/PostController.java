package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.dto.post.PostCommentResponseDto;
import com.example.cooperation_project.dto.post.PostRequestDto;
import com.example.cooperation_project.dto.post.PostResponseDto;
import com.example.cooperation_project.dto.post.ReqPostPageableDto;
import com.example.cooperation_project.security.UserDetailsImpl;
import com.example.cooperation_project.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/chitchat/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.createPost(postRequestDto, userDetails.getUser());
    }

    @GetMapping("/chitchat/posts")
    public List<PostResponseDto> getPosts(@RequestBody ReqPostPageableDto dto){

       return postService.getProductsOrderByModified(dto);
    }
    @GetMapping("/chitchat/posts/{postId}")
    public PostCommentResponseDto getPostsId(@PathVariable Long postId) {
        return postService.getPostsId(postId);
    }

    @PatchMapping("/chitchat/posts/{postId}")
    public PostResponseDto update(@PathVariable Long postId,
        @RequestBody PostRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.update(postId, postRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/chitchat/posts/{postId}")
    public MsgCodeResponseDto delete(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.delete(postId, userDetails.getUser());
    }

    @PutMapping("/chitchat/posts/{postId}/loves")
    public ResponseEntity<Map<String, HttpStatus>> PostLoveOk(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.loveOk(postId, userDetails.getUser());
    }
}
