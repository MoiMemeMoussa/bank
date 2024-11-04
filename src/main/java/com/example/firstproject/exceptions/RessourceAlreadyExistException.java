package com.example.firstproject.exceptions;

public class RessourceAlreadyExistException extends RuntimeException {

    public RessourceAlreadyExistException(String message) {
        super(message);
    }
}
