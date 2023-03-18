package com.example.cooperation_project.service;

import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.dto.PostRequestDto;
import com.example.cooperation_project.dto.PostResponseDto;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.entity.UserRoleEnum;
import com.example.cooperation_project.exception.ApiException;
import com.example.cooperation_project.exception.ExceptionEnum;
import com.example.cooperation_project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user){
        Post post = postRepository.saveAndFlush(new Post(requestDto, user));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts(){
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();

        for(Post post : posts){
            postResponseDtos.add(new PostResponseDto(post));
        }
        return postResponseDtos;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostsId(Long post_Id){
        Post post = postRepository.findById(post_Id).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_USER)
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto update(Long post_Id, PostRequestDto postRequestDto, User user){
        Post post = postRepository.findById(post_Id).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_POST_ALL)
        );

        if(post.getUser().getUserId().equals(user.getUserId()) || user.getRole() == UserRoleEnum.ADMIN){
            post.update(postRequestDto);
            return new PostResponseDto(post);
        }
        return null;
    }

    @Transactional
    public MsgCodeResponseDto delete(Long post_Id, User user) {
        MsgCodeResponseDto responseDto = new MsgCodeResponseDto();
        Post post = postRepository.findById(post_Id).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_POST_ALL)
        );
        if (post.getUser().getUserId().equals(user.getUserId()) || user.getRole() == UserRoleEnum.ADMIN) {
            postRepository.deleteById(post_Id);
            responseDto.setResult("게시글 삭제 성공", HttpStatus.OK.value());
            return responseDto;
        }
        responseDto.setResult("게시글 삭제 실패", HttpStatus.BAD_REQUEST.value());
        return responseDto;
    }
    }
