package com.astra.moments.exception;

import java.util.NoSuchElementException;

public class EntityNotFoundException extends NoSuchElementException {

    public EntityNotFoundException(String message){
        super(message);
    }
}
