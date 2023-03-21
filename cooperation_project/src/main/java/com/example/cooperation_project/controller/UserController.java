package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.dto.auth.SignupRequestDto;
import com.example.cooperation_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/chitchat/auth/signup")
    public MsgCodeResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }
    @PostMapping("/chitchat/auth/login")
    public MsgCodeResponseDto login(@RequestBody SignupRequestDto signupRequestDto, HttpServletResponse httpServletResponse){
        return userService.login(signupRequestDto, httpServletResponse);
    }
}
