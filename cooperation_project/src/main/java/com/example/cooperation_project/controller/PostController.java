package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.dto.post.PostCommentResponseDto;
import com.example.cooperation_project.dto.post.PostRequestDto;
import com.example.cooperation_project.dto.post.PostResponseDto;
import com.example.cooperation_project.dto.post.ReqPostPageableDto;
import com.example.cooperation_project.security.UserDetailsImpl;
import com.example.cooperation_project.service.LoveServiceTemp;
import com.example.cooperation_project.service.PostService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chitchat")
public class PostController {

    private final PostService postService;

    private final LoveServiceTemp loveService;

    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.createPost(postRequestDto, userDetails.getUser());
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(ReqPostPageableDto dto, HttpServletResponse resp) {

        Long count = postService.getCountAllPosts();

        resp.addHeader("Total_Count_Posts",String.valueOf(count));

        return postService.getPageOfPost(dto);
    }

    @GetMapping("/posts/{postId}")
    public PostCommentResponseDto getPostsId(@PathVariable Long postId) {

        return postService.getPostsId(postId);
    }

    @PatchMapping("/posts/{postId}")
    public PostResponseDto update(@PathVariable Long postId,
        @RequestBody PostRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.update(postId, postRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Object> delete(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.delete(postId, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(new MsgCodeResponseDto("해당 자료 삭제 완료"));
    }


    /*@PutMapping("/chitchat/posts/{postId}/loves")
    public ResponseEntity<Map<String, HttpStatus>> PostLoveOk(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.loveOk(postId, userDetails.getUser());
    }*/

    @PutMapping("/posts/{postId}/loves")
    public ResponseEntity<Object> postLove(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl details) {

        boolean checkedLove = loveService.loveOnPost(postId,details.getUser());


        return checkedLove ?
            ResponseEntity.ok(new MsgCodeResponseDto("좋아요 on")) :
            ResponseEntity.ok(new MsgCodeResponseDto("좋아요 off"));
    }

}
