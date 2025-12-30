package com.wallet.util.exceptions;

public class NotExistException extends RuntimeException {
    public NotExistException() {
        super();
    }

    public NotExistException(String message) {
        super(message);
    }
}
