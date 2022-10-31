package com.AronCode.postgress.exception;

public class ResourceNotFoundException extends Exception {
    private static final long serialVersionUID =1l;

    public ResourceNotFoundException(String message){
        super(message);
    }
}
