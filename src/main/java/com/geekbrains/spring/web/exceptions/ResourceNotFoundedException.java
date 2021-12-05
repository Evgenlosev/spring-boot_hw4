package com.geekbrains.spring.web.exceptions;

public class ResourceNotFoundedException extends RuntimeException {
    public ResourceNotFoundedException(String message) {
        super(message);
    }
}
