package com.example.cooperation_project.exception;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {
    }

    public DuplicateUserException(String message) {
        super(message);
    }

}
