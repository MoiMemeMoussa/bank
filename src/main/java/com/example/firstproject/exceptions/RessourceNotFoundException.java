package com.example.firstproject.exceptions;

public class RessourceNotFoundException extends RuntimeException {

    public RessourceNotFoundException(String message) {
        super(message);
    }
}
