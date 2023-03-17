package com.example.cooperation_project.repository;

import com.example.cooperation_project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
