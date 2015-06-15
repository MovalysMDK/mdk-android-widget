package com.soprasteria.movalysmdk.widget.core.exception;

/**
 * Exception thrown by Provider when string resource is not found
 * by its key
 */
public class StringNotDefineForResourceException extends RuntimeException {
    public StringNotDefineForResourceException(String message) {
        super(message);
    }
}
