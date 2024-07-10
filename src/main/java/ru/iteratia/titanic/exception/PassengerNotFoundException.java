package ru.iteratia.titanic.exception;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException(String message) {
        super(message);
    }
}
