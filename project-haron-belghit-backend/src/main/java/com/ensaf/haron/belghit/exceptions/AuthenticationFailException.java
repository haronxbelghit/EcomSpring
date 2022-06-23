package com.ensaf.haron.belghit.exceptions;

public class AuthenticationFailException extends IllegalArgumentException {
    public AuthenticationFailException(String message) {
        super(message);
    }
}