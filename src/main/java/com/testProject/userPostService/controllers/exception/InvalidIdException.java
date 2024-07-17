package com.testProject.userPostService.controllers.exception;

public class InvalidIdException extends RuntimeException {

    public InvalidIdException(String string) {
        super("invalid ID" + string);
    }


}
