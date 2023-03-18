package com.example.cooperation_project.repository;

import com.example.cooperation_project.entity.LoveComment;
import com.example.cooperation_project.entity.LovePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LovePostRepository extends JpaRepository<LovePost, Long> {
}
