package com.example.cooperation_project.service;

import com.example.cooperation_project.dto.RespMsgDto;
import com.example.cooperation_project.dto.post.RespPostCommentDto;
import com.example.cooperation_project.dto.post.ReqPostDto;
import com.example.cooperation_project.dto.post.RespPostDto;
import com.example.cooperation_project.dto.post.ReqPostPageableDto;
import com.example.cooperation_project.entity.*;
import com.example.cooperation_project.exception.NotAuthException;
import com.example.cooperation_project.exception.NotFoundPostException;
import com.example.cooperation_project.exception.NotFoundUserException;
import com.example.cooperation_project.repository.*;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LovePostRepository lovePostRepository;
    private final LoveCommentRepository loveCommentRepository;
    private final UserRepository userRepository;



    @Transactional
    public RespPostDto createPost(ReqPostDto requestDto, User user) {

        Post post = postRepository.save(new Post(requestDto, user));

        return new RespPostDto(post);
    }

    @Transactional(readOnly = true)
    public List<RespPostDto> getPosts() {

        List<Post> postList = postRepository
            .findAllByOrderByCreatedAtDesc();

        return postList.stream()
            .map(RespPostDto::new).toList();
    }

    @Transactional(readOnly = true)
    public RespPostCommentDto getPostsId(Long postId) throws NotFoundPostException {

        Post post = postRepository.findById(postId).orElseThrow(
            () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );

        return new RespPostCommentDto(post);
    }

    @Transactional(readOnly = true)
    public Long getCountAllPosts(){

        return postRepository.countPosts();
    }

    @Transactional
    public RespPostDto update(Long postId, ReqPostDto reqPostDto, User user)
        throws NotFoundPostException, NotAuthException {

        Post post = postRepository.findById(postId).orElseThrow(
            () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );

        if (isMatchUser(post, user) || user.getRole() == UserRoleEnum.ADMIN) {
            post.update(reqPostDto);

            return new RespPostDto(post);

        } else {

            throw new NotAuthException("해당 권한이 없습니다");
        }
    }


    public List<RespPostDto> getPageOfPost(ReqPostPageableDto dto) {

        return postRepository
            .findAllByOrderByCreatedAtDesc(configPageAble(dto))
            .stream().map(RespPostDto::new).toList();
    }

    private Pageable configPageAble(ReqPostPageableDto dto){

        Sort.Direction direction = dto.isAsc()? Direction.ASC : Direction.DESC;
        Sort sort = Sort.by(direction,dto.getSortBy());

        return PageRequest.of(dto.getPage()-1,dto.getSize(),sort);
    }

    private boolean isMatchUser(Post post, User user) {

        return post.getUser().getUserId().equals(user.getUserId());
    }

    @Transactional
    public void delete(Long postId, User user) {

        Post post = postRepository.findById(postId).orElseThrow(
            () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );

        if (isMatchUser(post, user) || user.getRole() == UserRoleEnum.ADMIN) {
            List<Comment> commentList = post.getCommentList();
            for(Comment comment: commentList){
                List<LoveComment> loveComments = user.getLoveCommentList();
                for(LoveComment loveComment: loveComments){
                    if(user.getId() == loveComment.getUser().getId() && comment.getCommentId() == loveComment.getComment().getCommentId()){
                        loveCommentRepository.delete(loveComment);
                    }
                }

                commentRepository.delete(comment);
            }
            postRepository.deleteById(postId);

        } else {
            throw new NotAuthException("해당 권한이 없습니다");
        }
    }


}

