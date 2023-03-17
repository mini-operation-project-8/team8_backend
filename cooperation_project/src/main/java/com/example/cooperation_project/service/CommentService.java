package com.example.cooperation_project.service;

import com.example.cooperation_project.dto.CommentRequestDto;
import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.entity.Comment;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.jwt.JwtUtil;
import com.example.cooperation_project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public MsgCodeResponseDto createdComment(CommentRequestDto commentRequestDto, User user) {
        Comment comment = new Comment(commentRequestDto.getComment(),user);
        commentRepository.save(comment);
        MsgCodeResponseDto result = new MsgCodeResponseDto();
        result.setResult("댓글 작성 성공",HttpStatus.OK.value());
        return result;
    }
}