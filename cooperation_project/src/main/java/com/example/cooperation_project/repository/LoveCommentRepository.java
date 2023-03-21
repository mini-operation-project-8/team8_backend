package com.example.cooperation_project.repository;

import com.example.cooperation_project.entity.LoveComment;
import com.example.cooperation_project.entity.LovePost;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoveCommentRepository extends JpaRepository<LoveComment, Long> {

    @Query("select l from LoveComment l where l.user.userId = :userId and l.comment.commentId = :commentId")
    Optional<LoveComment> findLoveComment(Long commentId,String userId);

    @Query("select count(l) from LoveComment l where l.comment.commentId = :commentId")
    Long countNumOfLoveOnComment(Long commentId);
}