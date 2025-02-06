package com.softuni.mappingdto.exception;

public class NonexistingEntityException extends RuntimeException {
    public NonexistingEntityException() {
    }

    public NonexistingEntityException(Throwable cause) {
        super(cause);
    }

    public NonexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistingEntityException(String message) {
        super(message);
    }
}
