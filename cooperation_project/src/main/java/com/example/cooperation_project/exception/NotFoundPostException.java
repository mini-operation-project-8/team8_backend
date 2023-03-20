package com.example.cooperation_project.exception;

public class NotFoundPostException extends RuntimeException{
    public NotFoundPostException(String message) {
        super(message);
    }
}
