package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.dto.auth.SignupRequestDto;
import com.example.cooperation_project.jwt.JwtUtil;
import com.example.cooperation_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/chitchat/auth/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid SignupRequestDto dto){

        userService.signup(dto);

        return ResponseEntity.ok(new MsgCodeResponseDto("회원가입 성공"));
    }
    @PostMapping("/chitchat/auth/login")
    public ResponseEntity<Object> login(@RequestBody SignupRequestDto dto, HttpServletResponse resp){

        String token = userService.login(dto);

        resp.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return ResponseEntity.ok(new MsgCodeResponseDto("로그인 성공"));
    }
}
