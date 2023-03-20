package com.example.cooperation_project.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotFoundUserException extends RuntimeException{

    public NotFoundUserException(String message){
        super(message);
    }
}
