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
package com.soprasteria.movalysmdk.widget.core.helper;

import android.support.annotation.IdRes;

import com.soprasteria.movalysmdk.widget.core.error.MDKMessage;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The MDKMessages is a container for MDKMessage.
 * <p></p>
 */
public class MDKMessages {

    /** the component id. */
    private int componentId;
    /** the component label. */
    private CharSequence componentLabel;

    /** the error map. */
    private Map<String, MDKMessage> messagesMap;

    /**
     * Constructor.
     */
    public MDKMessages() {
        this.messagesMap = new LinkedHashMap<>();
    }

    /**
     * Setter.
     * @param uniqueId the widget id
     */
    public void setComponentId(@IdRes int uniqueId) {
        this.componentId = uniqueId;
    }

    /**
     * Getter.
     * @return the widget id
     */
    @IdRes public int getComponentId() {
        return componentId;
    }

    /**
     * Return the first error message in the container.
     * @return the error message
     */
    public CharSequence getFirstErrorMessage() {
        if (!this.messagesMap.isEmpty()) {
            for (Map.Entry<String, MDKMessage> messageEntry : this.messagesMap.entrySet()) {
                if (messageEntry.getValue() != null) {
                    return messageEntry.getValue().getMessage();
                }
            }
        }
        return null;
    }

    /**
     * Setter.
     * @param label the widget label
     */
    public void setComponentLabelName(CharSequence label) {
        this.componentLabel = label;
    }

    /**
     * Getter.
     * @return the widget label
     */
    public CharSequence getComponentLabel() {
        return componentLabel;
    }

    /**
     * Return true if the key exists in the container.
     * @param key the key to find
     * @return true if the key exists in container, false otherwise
     */
    public boolean containsKey(String key) {
        return this.messagesMap.containsKey(key);
    }

    /**
     * Put a value in the container.
     * @param key the key of the MDKMessage
     * @param message the message to set
     */
    public void put(String key, MDKMessage message) {
        this.messagesMap.put(key, message);
    }

    /**
     * Return the first non-null message.
     * @return the first non-null message
     */
    public MDKMessage getFirstNonNullMessage() {
        if (!this.messagesMap.isEmpty()) {
            for (Map.Entry<String, MDKMessage> messageEntry : this.messagesMap.entrySet()) {
                if (messageEntry.getValue() != null) {
                    return messageEntry.getValue();
                }
            }
        }
        return null;
    }
}
