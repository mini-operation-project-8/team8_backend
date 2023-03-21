package com.example.cooperation_project.exception;

public class NotAuthException extends RuntimeException{

    public NotAuthException(String message) {
        super(message);
    }
}
