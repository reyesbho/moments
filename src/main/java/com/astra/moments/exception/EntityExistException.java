package com.astra.moments.exception;

import java.util.NoSuchElementException;

public class EntityExistException extends NoSuchElementException {

    public EntityExistException(String message){
        super(message);
    }
}
