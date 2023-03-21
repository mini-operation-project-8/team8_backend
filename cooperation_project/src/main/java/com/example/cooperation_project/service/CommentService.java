package com.example.cooperation_project.service;

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
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LoveCommentRepository loveCommentRepository;


    @Transactional
    public CommentResponseDto createdComment(Long post_Id, CommentRequestDto commentRequestDto, User user){

//        user = userRepository.findByUserId(user.getUserId()).orElseThrow(
//                () -> new NotFoundUserException("사용자가 존재하지않습니다.")
//        );
        Post post = postRepository.findById(post_Id).orElseThrow(
                () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto, post, user));
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto update(Long post_Id, Long comment_Id, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(post_Id).orElseThrow(
                () -> new NotFoundPostException("게시글을 찾을 수 없습니다.")
        );
        Comment comment = commentRepository.findById(comment_Id).orElseThrow(
                () -> new NotFoundCommentException("댓글을 찾을 수 없습니다.")
        );
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        if ((post.getId().equals(post_Id) && comment.getUser().getUserId().equals(user.getUserId())) || user.getRole() == UserRoleEnum.ADMIN) {
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else {
            throw new NotFoundCommentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    @Transactional
    public NotFoundCommentException deleteComment(Long post_Id, Long comment_Id, User user) {
        Post post = postRepository.findById(post_Id).orElseThrow(
                () -> new NotFoundPostException("게시글을 찾을 수 없습니다.")
        );
        Comment comment = commentRepository.findById(comment_Id).orElseThrow(
                () -> new NotFoundCommentException("댓글이 존재하지 않습니다.")
        );

        if ((post.getId().equals(post_Id) && comment.getUser().getUserId().equals(user.getUserId())) || user.getRole() == UserRoleEnum.ADMIN) {
            commentRepository.deleteById(comment_Id);
            return new NotFoundCommentException("댓글을 삭제 했습니다.");
        } else {
            throw new NotFoundCommentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    @Transactional
    public ResponseEntity<Map<String, HttpStatus>> loveOk(Long id, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        List<LoveComment> commentLoveList = user.getLoveCommentList();

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
}