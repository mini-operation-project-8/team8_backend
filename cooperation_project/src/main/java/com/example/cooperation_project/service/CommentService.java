package com.example.cooperation_project.service;

import com.example.cooperation_project.dto.comment.ReqCommentDto;
import com.example.cooperation_project.dto.comment.RespCommentDto;
import com.example.cooperation_project.entity.*;
import com.example.cooperation_project.exception.*;
import com.example.cooperation_project.repository.CommentRepository;
import com.example.cooperation_project.repository.LoveCommentRepository;
import com.example.cooperation_project.repository.PostRepository;
import com.example.cooperation_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final LoveCommentRepository loveCommentRepository;
    private final UserRepository userRepository;

    @Transactional
    public RespCommentDto createdComment(Long postId, ReqCommentDto reqCommentDto, User user){

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.saveAndFlush(new Comment(reqCommentDto, post, user));
        return new RespCommentDto(comment);
    }

    @Transactional
    public RespCommentDto update(Long postId, Long commentId, ReqCommentDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException("게시글을 찾을 수 없습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentException("댓글을 찾을 수 없습니다.")
        );
        if ((post.getId().equals(postId) && isMatchComment(comment, user)) || user.getRole() == UserRoleEnum.ADMIN) {
            comment.update(requestDto);
            return new RespCommentDto(comment);
        } else {
            throw new NotFoundCommentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    @Transactional
    public NotFoundCommentException deleteComment(Long postId, Long commentId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException("게시글을 찾을 수 없습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentException("댓글이 존재하지 않습니다.")
        );

        if ((post.getId().equals(postId) && isMatchComment(comment, user)) || user.getRole() == UserRoleEnum.ADMIN) {
            commentRepository.deleteById(commentId);
            return new NotFoundCommentException("댓글을 삭제 했습니다.");
        } else {
            throw new NotFoundCommentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    private boolean isMatchComment(Comment comment, User user){
        return comment.getUser().getUserId().equals(user.getUserId());
    }

    public List<RespCommentDto> getComment(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        List<RespCommentDto> result = new ArrayList<>();
        for (Comment comment: comments) {
            result.add(new RespCommentDto(comment));
        }
        return result;

    }
}