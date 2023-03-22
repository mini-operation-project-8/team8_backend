package com.example.cooperation_project.repository;

import com.example.cooperation_project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Optional<Comment> findById(Long userId);

    List<Comment> findByPostId( Long PostId);
}
