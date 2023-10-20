package ru.relex.minisocialnetwork.exception;

public class EntityAccessDeniedException extends RuntimeException {

    public EntityAccessDeniedException(String message) {
        super(message);
    }

}
