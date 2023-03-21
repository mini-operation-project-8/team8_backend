package com.example.cooperation_project.repository;

import com.example.cooperation_project.entity.LoveComment;
import com.example.cooperation_project.entity.LovePost;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LovePostRepository extends JpaRepository<LovePost, Long> {

    @Query("select l from LovePost l where l.user.userId = :userId and l.post.id = :postId")
    Optional<LovePost> findLovePost(Long postId,String userId);

    @Query("select count(l) from LovePost l where l.post.id = :postId")
    Long countNumOfLoveOnPost(Long postId);

}
