package com.geekbrains.spring.web.exceptions;

public class OrderPaymentException extends RuntimeException {
    public OrderPaymentException(String message) {
        super(message);
    }
}
