package com.example.cooperation_project.service;

import com.example.cooperation_project.dto.PostRequestDto;
import com.example.cooperation_project.dto.PostResponseDto;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.exception.ApiException;
import com.example.cooperation_project.exception.ExceptionEnum;
import com.example.cooperation_project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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
}
