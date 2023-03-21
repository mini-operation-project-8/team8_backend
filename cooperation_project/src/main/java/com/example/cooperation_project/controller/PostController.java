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

    // 게시글 작성
    @PostMapping("/chitchat/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.createPost(postRequestDto, userDetails.getUser());
    }


    @GetMapping("/chitchat/posts")
    public List<PostResponseDto> getPosts(@RequestBody ReqPostPageableDto dto){

       return postService.getProductsOrderByModified(dto);
    }

    // 선택한 게시글 조회
    @GetMapping("/chitchat/posts/{post_Id}")
    public PostCommentResponseDto getPostsId(@PathVariable Long post_Id) {
        return postService.getPostsId(post_Id);
    }

    // 게시글 수정
    @PatchMapping("/chitchat/posts/{post_Id}")
    public PostResponseDto update(@PathVariable Long post_Id,
        @RequestBody PostRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.update(post_Id, postRequestDto, userDetails.getUser());
    }

    // 게시글 삭제
    @DeleteMapping("/chitchat/posts/{post_Id}")
    public MsgCodeResponseDto delete(@PathVariable Long post_Id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.delete(post_Id, userDetails.getUser());
    }

    @PutMapping("/chitchat/posts/{post_Id}/loves")
    public ResponseEntity<Map<String, HttpStatus>> PostLoveOk(@PathVariable Long post_Id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.loveOk(post_Id, userDetails.getUser());
    }
}
