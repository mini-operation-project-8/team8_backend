package com.example.cooperation_project.service;

import com.example.cooperation_project.entity.Comment;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.exception.NotFoundPostException;
import com.example.cooperation_project.repository.CommentRepository;
import com.example.cooperation_project.repository.LovePostRepository;
import com.example.cooperation_project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoveService {
    private LovePostRepository lovePostRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    public boolean loveOnPost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );

        return true;

    }

    public boolean loveOnComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundPostException("해당 코멘트가 존재하지 않습니다.")
        );
        return true;
    }
}