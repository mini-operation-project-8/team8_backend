package com.example.cooperation_project.controller;

import com.example.cooperation_project.dto.LoginRequestDto;
import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.dto.SignupRequestDto;
import com.example.cooperation_project.security.UserDetailsImpl;
import com.example.cooperation_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @ResponseBody
    @PostMapping("/chitchat/signup")
    public MsgCodeResponseDto signup(@RequestBody SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }
    @CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
    @ResponseBody
    @PostMapping("/chitchat/login")
    public MsgCodeResponseDto login(@RequestBody SignupRequestDto signupRequestDto, HttpServletResponse httpServletResponse){
        return userService.login(signupRequestDto, httpServletResponse);
    }
}
