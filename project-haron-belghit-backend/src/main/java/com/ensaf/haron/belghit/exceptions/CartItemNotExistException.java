package com.ensaf.haron.belghit.exceptions;

public class CartItemNotExistException extends IllegalArgumentException {
    public CartItemNotExistException(String message) {
        super(message);
    }
}
