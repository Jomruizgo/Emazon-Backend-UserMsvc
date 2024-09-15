package com.emazon.msvc_user.domain.exceptions;

public class DisabledAccountException extends RuntimeException{
    public DisabledAccountException(String message){super(message);}
}
