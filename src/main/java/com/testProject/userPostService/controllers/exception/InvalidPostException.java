package com.testProject.userPostService.controllers.exception;

public class InvalidPostException extends RuntimeException {

    public InvalidPostException(String string) {
        super("invalid ID" + string);
    }


}
