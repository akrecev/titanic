package ru.iteratia.titanic.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.iteratia.titanic.exception.PassengerNotFoundException;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<String> handlePassengerNotFound(PassengerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Произошла ошибка: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
