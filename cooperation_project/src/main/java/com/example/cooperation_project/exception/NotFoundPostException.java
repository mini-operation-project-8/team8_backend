package com.example.cooperation_project.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotFoundPostException extends RuntimeException{

    public NotFoundPostException(String message){
        super(message);
    }
}
