package com.example.cooperation_project.aop;

import com.example.cooperation_project.dto.RespMsgDto;
import com.example.cooperation_project.exception.DuplicateUserException;
import com.example.cooperation_project.exception.NotAuthException;
import com.example.cooperation_project.exception.NotFoundCommentException;
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

    @ExceptionHandler({NotFoundUserException.class})
    public ResponseEntity<RespMsgDto> handlerNotFoundUser(NotFoundUserException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new RespMsgDto(e.getMessage()));
    }

    @ExceptionHandler({NotFoundPostException.class})
    public ResponseEntity<RespMsgDto> handlerNotFoundPost(NotFoundPostException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new RespMsgDto(e.getMessage()));
    }

    @ExceptionHandler({NotFoundCommentException.class})
    public ResponseEntity<RespMsgDto> handlerNotFoundComment(NotFoundCommentException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new RespMsgDto(e.getMessage()));
    }

    @ExceptionHandler({NotAuthException.class})
    public ResponseEntity<RespMsgDto> handlerNotAuthException(NotAuthException e) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new RespMsgDto(e.getMessage()));
    }


    @ExceptionHandler({DuplicateUserException.class})
    public ResponseEntity<RespMsgDto> handlerDuplicatedUserException(
        DuplicateUserException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new RespMsgDto(e.getMessage()));
    }

}
