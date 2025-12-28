package com.example.firstproject;


import com.example.firstproject.exceptions.RessourceExistanteException;
import com.example.firstproject.exceptions.RessourceNonTrouveException;
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

    private static final String DELIMITEUR = ";";

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error throwError(final Exception exception) {
        loggerErreur(exception);
        final Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage().split(DELIMITEUR)[5]);
        return error;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RessourceExistanteException.class, RetraitImpossibleException.class})
    public Error throwErrorBadRequest(Exception exception) {
        loggerErreur(exception);
        final Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        return error;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(RessourceNonTrouveException.class)
    public Error throwErrorNotFound(Exception exception) {
        loggerErreur(exception);
        final Error error = new Error();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        return error;
    }

    private void loggerErreur(Exception exception) {
        log.error("Une erreur s'est produite : {}", exception.getMessage());
    }

    @Data
    public static class Error {
        private Integer code;
        private String message;
        private List<String> errors;
    }
}

