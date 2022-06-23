package com.ensaf.haron.belghit.exceptions;

public class ProductNotExistException extends IllegalArgumentException {
    public ProductNotExistException(String message) {
        super(message);
    }
}
