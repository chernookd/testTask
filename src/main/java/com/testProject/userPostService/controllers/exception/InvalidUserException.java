package com.testProject.userPostService.controllers.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String string) {
        super("invalid user" + string);
    }
}
