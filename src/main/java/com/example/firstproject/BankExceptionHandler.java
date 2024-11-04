package com.example.firstproject;


import com.example.firstproject.exceptions.RessourceAlreadyExistException;
import com.example.firstproject.exceptions.RessourceNotFoundException;
import com.example.firstproject.exceptions.RetraitImpossibleException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class BankExceptionHandler {


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error throwError(final Exception exception) {

        log.error("Une erreur de validation s'est produite");

        final Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        String delimiteur = ";";
        error.setMessage(exception.getMessage().split(delimiteur)[5]);
        return error;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RessourceAlreadyExistException.class, RessourceNotFoundException.class, RetraitImpossibleException.class})
    public Error throwErrorBadRequest(Exception exception) {
        log.error("Une erreur s'est produite");
        final Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        return error;
    }

    @Data
    public static class Error {
        private Integer code;
        private String message;
        private List<String> errors;
    }

}

