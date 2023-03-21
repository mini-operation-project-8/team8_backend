package com.example.cooperation_project.service;

import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.dto.auth.SignupRequestDto;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.entity.UserRoleEnum;
import com.example.cooperation_project.exception.DuplicateUserException;
import com.example.cooperation_project.jwt.JwtUtil;
import com.example.cooperation_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

        Optional<User> found = userRepository.findByUserId(userId);

        if(found.isPresent()){
            throw new DuplicateUserException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(userId, password, role);

        userRepository.save(user);

        MsgCodeResponseDto result = new MsgCodeResponseDto("회원가입 성공");
        return result;
    }

    @Transactional
    public MsgCodeResponseDto login(SignupRequestDto signupRequestDto , HttpServletResponse httpServletResponse){
        String userId = signupRequestDto.getUserId();

        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        String encodePassword = user.getPassword();
        boolean isPasswordMatch = passwordEncoder.matches(signupRequestDto.getPassword(), encodePassword);
        if(!isPasswordMatch){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserId(), user.getRole()));

        MsgCodeResponseDto result = new MsgCodeResponseDto("로그인 성공");
        return result;
    }

}
