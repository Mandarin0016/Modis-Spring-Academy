package org.example.common;

public class NonExistingEntityException extends Exception {

    public NonExistingEntityException() {
    }

    public NonExistingEntityException(String message) {
        super(message);
    }

}
