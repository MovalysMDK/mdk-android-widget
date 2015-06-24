/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
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
