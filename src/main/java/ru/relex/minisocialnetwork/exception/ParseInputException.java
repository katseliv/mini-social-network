package ru.relex.minisocialnetwork.exception;

public class ParseInputException extends RuntimeException {

    public ParseInputException(String message) {
        super(message);
    }

    public ParseInputException(String message, Throwable cause) {
        super(message, cause);
    }

}