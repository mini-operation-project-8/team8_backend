package com.example.cooperation_project.service;

import com.example.cooperation_project.entity.LovePost;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.exception.NotFoundPostException;
import com.example.cooperation_project.exception.NotFoundUserException;
import com.example.cooperation_project.repository.LovePostRepository;
import com.example.cooperation_project.repository.PostRepository;
import com.example.cooperation_project.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoveService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final LovePostRepository lovePostRepository;

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

}
