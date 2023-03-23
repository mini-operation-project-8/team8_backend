package com.example.cooperation_project.service;

import com.example.cooperation_project.dto.auth.SignupRequestDto;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.entity.UserRoleEnum;
import com.example.cooperation_project.exception.DuplicateUserException;
import com.example.cooperation_project.exception.NotAuthException;
import com.example.cooperation_project.exception.NotFoundUserException;
import com.example.cooperation_project.jwt.JwtUtil;
import com.example.cooperation_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final  JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    private static final String ADMIN_TOKEN = "admin";

    @Transactional
    public void signup(SignupRequestDto dto){

        String userId = dto.getUserId();
        String password = encoder.encode(dto.getPassword());

        Optional<User> found = userRepository.findByUserId(userId);

        if(found.isPresent()){
            throw new DuplicateUserException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if(isAdmin(dto)){
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(userId, password, role);

        userRepository.save(user);
    }

    @Transactional
    public String login(SignupRequestDto dto){
        String userId = dto.getUserId();

        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new NotFoundUserException("등록된 사용자가 없습니다.")
        );

        String encodePassword = user.getPassword();

        if(!encoder.matches(dto.getPassword(), encodePassword)){

            throw new NotAuthException("인증 정보가 맞지 않아 실패.");
        }

        return jwtUtil.createToken(user.getUserId(), user.getRole());
    }

    private boolean isAdmin(SignupRequestDto dto){

        return dto.isAdmin() && dto.getAdminToken().equals(ADMIN_TOKEN);
    }

}
