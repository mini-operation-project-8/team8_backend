package com.example.cooperation_project.service;

import com.example.cooperation_project.dto.LoginRequestDto;
import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.dto.SignupRequestDto;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.entity.UserRoleEnum;
import com.example.cooperation_project.jwt.JwtUtil;
import com.example.cooperation_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final  JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "admin";

    @Transactional
    public MsgCodeResponseDto signup(SignupRequestDto signupRequestDto){
        String userId = signupRequestDto.getUserId();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUserId(userId);
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(userId, password, role);
        userRepository.save(user);
        MsgCodeResponseDto result = new MsgCodeResponseDto();
        result.setResult("회원가입 성공", HttpStatus.OK.value());
        return result;
    }

    @Transactional
    public MsgCodeResponseDto login(SignupRequestDto signupRequestDto , HttpServletResponse httpServletResponse){
        String userId = signupRequestDto.getUserId();
        String password = signupRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        String encodePassword = user.getPassword();
        boolean isPasswordMatch = passwordEncoder.matches(signupRequestDto.getPassword(), encodePassword);
        // 비밀번호 확인
        if(!isPasswordMatch){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserId(), user.getRole()));

        MsgCodeResponseDto result = new MsgCodeResponseDto();
        result.setResult("로그인 성공", HttpStatus.OK.value());
        return result;
    }
}
