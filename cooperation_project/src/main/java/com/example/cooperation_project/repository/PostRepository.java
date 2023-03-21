package com.example.cooperation_project.repository;

import com.example.cooperation_project.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByModifiedAtDesc();

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByOrderByModifiedAtDesc(Pageable pageable);
}
