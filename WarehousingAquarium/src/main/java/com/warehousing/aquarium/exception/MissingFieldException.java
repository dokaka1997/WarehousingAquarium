package com.warehousing.aquarium.exception;

public class MissingFieldException extends RuntimeException{
    public MissingFieldException(String errorMessage) {
        super(errorMessage);
    }
}
