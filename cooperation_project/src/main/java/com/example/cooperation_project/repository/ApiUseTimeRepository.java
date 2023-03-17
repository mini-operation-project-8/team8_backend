package com.example.cooperation_project.repository;

import com.example.cooperation_project.entity.ApiUseTime;
import com.example.cooperation_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(User user);
}