package com.soprasteria.movalysmdk.widget.core.exception;

/**
 * Global exception for MDK Widget library.
 */
public class MDKWidgetException extends RuntimeException {

    /**
     * Constructs a new {@code MDKWidgetException} with the current stack trace,
     * and the specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public MDKWidgetException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code MDKWidgetException} with the current stack trace,
     * the specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable the cause of this exception.
     */
    public MDKWidgetException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code MDKWidgetException} with the current stack trace
     * and the specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public MDKWidgetException(Throwable throwable) {
        super(throwable);
    }
}
