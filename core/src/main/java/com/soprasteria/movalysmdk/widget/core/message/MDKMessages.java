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
package com.soprasteria.movalysmdk.widget.core.message;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The MDKMessages is a container for MDKMessage.
 * <p></p>
 */
public class MDKMessages implements Parcelable {

    /** the component id. */
    private int componentId;
    /** the component label. */
    private CharSequence componentLabel;

    /** the error map. */
    private Map<String, MDKMessage> messagesMap;

    /**
     * Constructor from Parcel.
     * @param in the Parcel to recover from
     */
    protected MDKMessages(Parcel in) {
        componentId = in.readInt();
        componentLabel = in.readString();
        messagesMap = new LinkedHashMap<>();
        in.readMap(messagesMap, null);
    }

    /**
     * The Parcelable CREATOR.
     */
    public static final Creator<MDKMessages> CREATOR = new Creator<MDKMessages>() {
        @Override
        public MDKMessages createFromParcel(Parcel in) {
            return new MDKMessages(in);
        }

        @Override
        public MDKMessages[] newArray(int size) {
            return new MDKMessages[size];
        }
    };

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
     * Return all error messages in the container into a single char.
     * @return all the concatenated error messages
     */
    public CharSequence getErrorMessage() {
        StringBuilder messagesBuilder = new StringBuilder();
        if (!this.messagesMap.isEmpty()) {
            for (Map.Entry<String, MDKMessage> messageEntry : this.messagesMap.entrySet()) {
                if ((messageEntry.getValue() != null) && (messagesBuilder.length() != 0)){
                    messagesBuilder.append("\n").append(messageEntry.getValue().getMessage());
                }else if (messageEntry.getValue() != null){
                    messagesBuilder.append(messageEntry.getValue().getMessage());
                }
            }
        }
       return messagesBuilder;
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
     * Returns the stored messages.
     * @return the messages map
     */
    public Map<String, MDKMessage> getMessagesMap() {
        return this.messagesMap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(componentId);

        if (componentLabel != null) {
            dest.writeString(componentLabel.toString());
        } else {
            dest.writeString("");
        }
        dest.writeMap(messagesMap);
    }
}
