package com.example.cooperation_project.service;

import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.dto.comment.CommentRequestDto;
import com.example.cooperation_project.dto.comment.CommentResponseDto;
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
    public CommentResponseDto createdComment(Long postId, CommentRequestDto commentRequestDto, User user){
        
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto, post, user));
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto update(Long postId, Long commentId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException("게시글을 찾을 수 없습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentException("댓글을 찾을 수 없습니다.")
        );
        if ((post.getId().equals(postId) && isMatchComment(comment, user)) || user.getRole() == UserRoleEnum.ADMIN) {
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else {
            throw new NotFoundCommentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    @Transactional
    public MsgCodeResponseDto deleteComment(Long postId, Long commentId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException("게시글을 찾을 수 없습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentException("댓글이 존재하지 않습니다.")
        );

        if ((post.getId().equals(postId) && isMatchComment(comment, user)) || user.getRole() == UserRoleEnum.ADMIN) {
            commentRepository.deleteById(commentId);
            return new MsgCodeResponseDto("댓글을 삭제했습니다");
        } else {
            throw new NotFoundCommentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    @Transactional
    public ResponseEntity<Map<String, HttpStatus>> loveOk(Long id, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        User user1 = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );

        List<LoveComment> commentLoveList = user1.getLoveCommentList();

        if (user != null) {

            for (LoveComment loveComment : commentLoveList) {
                if (loveComment.getUser().getUserId() == comment.getUser().getUserId() && loveComment.getUser().getUserId() == user.getUserId()) {
                    if (loveComment.isLove() == false) {
                        loveComment.update();
                        comment.LoveOk();
                        return new ResponseEntity("댓글을 좋아요 했습니다.", HttpStatus.OK);
                    } else {
                        loveComment.update();
                        comment.LoveCancel();
                        return new ResponseEntity("좋아요를 취소 했습니다.", HttpStatus.OK);
                    }
                } else {
                    LoveComment commentLove = new LoveComment(comment, user);
                    loveCommentRepository.save(commentLove);
                    commentLove.update();
                    comment.LoveOk();
                    return new ResponseEntity("댓글을 좋아요 했습니다.", HttpStatus.OK);
                }
            }
            if(commentLoveList.size() == 0){
                LoveComment commentLove = new LoveComment(comment, user);
                loveCommentRepository.save(commentLove);
                commentLove.update();
                comment.LoveOk();
                return new ResponseEntity("댓글을 좋아요 했습니다.", HttpStatus.OK);
            }

        } else {
            throw new IllegalArgumentException("로그인 유저만 좋아요할 수 있습니다.");
        }
        return null;
    }

    private boolean isMatchComment(Comment comment, User user){
        return comment.getUser().getUserId().equals(user.getUserId());
    }
}