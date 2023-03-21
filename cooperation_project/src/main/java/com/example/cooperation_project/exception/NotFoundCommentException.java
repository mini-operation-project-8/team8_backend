package com.example.cooperation_project.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotFoundCommentException extends RuntimeException{

    public NotFoundCommentException(String msg) {
        super(msg);
    }
}
