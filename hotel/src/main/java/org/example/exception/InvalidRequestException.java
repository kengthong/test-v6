package org.example.exception;

/**
 * Exception that can be thrown to indicate that a service cannot serve a
 * a request due to violation of a business rule.
 */
public class InvalidRequestException extends RuntimeException {
    InvalidRequestException() {
    }

    InvalidRequestException(String s) {
        super(s);
    }

    InvalidRequestException(String s, Throwable throwable) {
        super(s, throwable);
    }

    InvalidRequestException(Throwable throwable) {
        super(throwable);
    }
}

