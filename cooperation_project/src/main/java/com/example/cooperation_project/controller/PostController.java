package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.RespMsgDto;
import com.example.cooperation_project.dto.post.ReqPostDto;
import com.example.cooperation_project.dto.post.RespPostDto;
import com.example.cooperation_project.dto.post.ReqPostPageableDto;
import com.example.cooperation_project.dto.post.RespTotalOfPostsDto;
import com.example.cooperation_project.security.UserDetailsImpl;
import com.example.cooperation_project.service.LoveService;
import com.example.cooperation_project.service.PostService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    private final LoveService loveService;

    @PostMapping("/posts")
    public ResponseEntity<Object> createPost(@RequestBody @Valid ReqPostDto reqPostDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {


        return ResponseEntity.ok(postService.createPost(reqPostDto, userDetails.getUser()));
    }

    @GetMapping("/posts")
    public List<RespPostDto> getPosts(ReqPostPageableDto dto, HttpServletResponse resp) {

        Long count = postService.getCountAllPosts();

        resp.addHeader("Total_Count_Posts", String.valueOf(count));

        return postService.getPageOfPost(dto);
    }

    @GetMapping("/posts/total")
    public ResponseEntity<Object> getTotalOfPosts() {

        Long total = postService.getCountAllPosts();

        return ResponseEntity.ok(new RespTotalOfPostsDto(total));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Object> getPostsId(@PathVariable Long postId) {

        return ResponseEntity.ok(postService.getPostsId(postId));
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<Object> update(@PathVariable Long postId,
        @RequestBody ReqPostDto reqPostDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(postService.update(postId, reqPostDto, userDetails.getUser()));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Object> delete(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.delete(postId, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(new RespMsgDto("해당 자료 삭제 완료"));
    }


    @PutMapping("/posts/{postId}/loves")
    public ResponseEntity<Object> PostLoveOk(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        boolean checked = loveService.loveOk(postId, userDetails.getUser());

        return checked ?
            ResponseEntity.ok("게시글을 좋아요 했습니다.") :
            ResponseEntity.ok("좋아요를 취소 했습니다.");
    }


}
