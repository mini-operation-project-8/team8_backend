package com.example.cooperation_project.service;

import com.example.cooperation_project.entity.Comment;
import com.example.cooperation_project.entity.LoveComment;
import com.example.cooperation_project.entity.LovePost;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.exception.NotFoundPostException;
import com.example.cooperation_project.exception.NotFoundUserException;
import com.example.cooperation_project.repository.CommentRepository;
import com.example.cooperation_project.repository.LoveCommentRepository;
import com.example.cooperation_project.repository.LovePostRepository;
import com.example.cooperation_project.repository.PostRepository;
import com.example.cooperation_project.repository.UserRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoveService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final LovePostRepository lovePostRepository;

    private final CommentRepository commentRepository;

    private final LoveCommentRepository loveCommentRepository;

    @Transactional
    public boolean loveOk(Long id, User user) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundPostException("게시글이 존재하지 않습니다.")
        );
        User user1 = userRepository.findById(user.getId()).orElseThrow(
                () -> new NotFoundUserException("유저가 존재하지 않습니다.")
        );

        List<LovePost> boardLoveList = user1.getLovePostList();

        for (LovePost lovePost : boardLoveList) {
            if (lovePost.getPost().getId() == post.getId()
                    && lovePost.getUser().getId() == user.getId()) {

                if (!lovePost.isLove()) {
                    lovePost.update();
                    post.LoveOk();

                    return true;
                } else {
                    lovePost.update();
                    post.LoveCancel();

                    return false;
                }
            }
        }
        LovePost lovePost1 = new LovePost(post, user);
        lovePostRepository.save(lovePost1);
        lovePost1.update();
        post.LoveOk();

        return true;
    }

    @Transactional
    public ResponseEntity<Map<String, HttpStatus>> loveOkOnComment(Long id, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        User user1 = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );

        List<LoveComment> commentLoveList = user1.getLoveCommentList();

            for (LoveComment loveComment : commentLoveList) {
                if (loveComment.getUser().getId() == comment.getUser().getId() && loveComment.getComment().getCommentId() == comment.getCommentId()) {
                    if (loveComment.isLove() == false) {
                        loveComment.update();
                        comment.LoveOk();
                        return new ResponseEntity("댓글을 좋아요 했습니다.", HttpStatus.OK);
                    } else {
                        loveComment.update();
                        comment.LoveCancel();
                        return new ResponseEntity("좋아요를 취소 했습니다.", HttpStatus.OK);
                    }
                }
            }
            LoveComment commentLove = new LoveComment(comment, user);
            loveCommentRepository.save(commentLove);
            commentLove.update();
            comment.LoveOk();
            return new ResponseEntity("댓글을 좋아요 했습니다.", HttpStatus.OK);

    }

}
