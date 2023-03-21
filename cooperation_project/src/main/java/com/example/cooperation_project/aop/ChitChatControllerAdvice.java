package com.example.cooperation_project.aop;

import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.exception.NotAuthException;
import com.example.cooperation_project.exception.NotFoundPostException;
import com.example.cooperation_project.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ChitChatControllerAdvice {

    @ExceptionHandler({NotFoundUserException.class}) // 로그인 실패 ...
    public ResponseEntity<MsgCodeResponseDto> handlerNotFoundUser(NotFoundUserException e){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MsgCodeResponseDto(e.getMessage()));
    }

    @ExceptionHandler({NotFoundPostException.class})
    public ResponseEntity<MsgCodeResponseDto> handlerNotFoundPost(NotFoundPostException e){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MsgCodeResponseDto(e.getMessage()));
    }

    @ExceptionHandler({NotAuthException.class})
    public ResponseEntity<MsgCodeResponseDto> handlerNotAuthException(NotAuthException e){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MsgCodeResponseDto(e.getMessage()));
    }

}
