package com.emazon.msvc_user.domain.exceptions;

public class DuplicatedObjectException extends RuntimeException {
    public DuplicatedObjectException(String message){
        super(message);
    }
}
