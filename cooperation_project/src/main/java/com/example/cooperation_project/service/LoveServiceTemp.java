package com.example.cooperation_project.service;

import com.example.cooperation_project.entity.Comment;
import com.example.cooperation_project.entity.LoveComment;
import com.example.cooperation_project.entity.LovePost;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.exception.NotFoundPostException;
import com.example.cooperation_project.repository.CommentRepository;
import com.example.cooperation_project.repository.LoveCommentRepository;
import com.example.cooperation_project.repository.LovePostRepository;
import com.example.cooperation_project.repository.PostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class LoveServiceTemp {

    private final LovePostRepository lovePostRepo;

    private final LoveCommentRepository loveCmtRepo;

    private final PostRepository postRepo;

    private final CommentRepository cmtRepo;

    @Transactional
    public boolean loveOnPost(Long postId, User user) {

        Optional<LovePost> opLovePost
            = lovePostRepo.findLovePost(postId, user.getUserId());

        if(opLovePost.isPresent()){

            lovePostRepo.delete(opLovePost.get());

            return false;

        }else{

            Post post =
                postRepo.findById(postId).orElseThrow(NotFoundPostException::new);

            lovePostRepo.save(new LovePost(post,user));

            return true;
        }
    }

    @Transactional
    public boolean loveOnComment(Long commentId, User user) {

        Optional<LoveComment> opLoveCmt
            = loveCmtRepo.findLoveComment(commentId, user.getUserId());

        if(opLoveCmt.isPresent()){

            loveCmtRepo.delete(opLoveCmt.get());

            return false;

        }else{

            Comment cmt = cmtRepo
                .findById(commentId).orElseThrow(NotFoundPostException::new);

            loveCmtRepo.save(new LoveComment(cmt,user));

            return true;
        }
    }

}
