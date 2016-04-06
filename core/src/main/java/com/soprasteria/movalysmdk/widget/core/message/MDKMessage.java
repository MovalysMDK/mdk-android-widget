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
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * MDKMessage class definition.
 */
public class MDKMessage implements Parcelable {

    /** Empty message string. */
    private static final String EMPTY_MESSAGE = "";

    /**
     * Enum for message type.
     */
    @IntDef({NO_MESSAGE_CODE, ERROR_TYPE, MESSAGE_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    @interface EnumKindOfMessage {
    }
    /** No message code defined. */
    public static final int NO_MESSAGE_CODE = -1;
    /** Message type. */
    public static final int MESSAGE_TYPE = 1;
    /** Error type. */
    public static final int ERROR_TYPE = 0;

    /**
     * Id of the component raising the message. This one is set according:
     * <ul>
     *     <li>If the component is inside a rich one.</li>
     *     <li>If the component is a basic one.</li>
     * </ul>
     */
    private int componentId;

    /** Name of the component raising the message.  */
    private CharSequence componentLabelName;

    /** message raised by the component.  */
    private CharSequence message;

    /**
     * Message code defining which kind of message it is.
     * <p>For example, it can be used later for apply text style.</p>
     */
    private int messageCode;

    /**
     * Type of this message.
     */
    private int messageType = ERROR_TYPE;


    /**
     * Constructor from Parcel.
     * @param in the Parcel to recover from
     */
    protected MDKMessage(Parcel in) {
        componentId = in.readInt();
        messageCode = in.readInt();
        messageType = in.readInt();
    }

    /**
     * The Parcelable CREATOR.
     */
    public static final Creator<MDKMessage> CREATOR = new Creator<MDKMessage>() {
        @Override
        public MDKMessage createFromParcel(Parcel in) {
            return new MDKMessage(in);
        }

        @Override
        public MDKMessage[] newArray(int size) {
            return new MDKMessage[size];
        }
    };

    /**
     * Private initializer.
     */
    private void init() {
        messageCode = NO_MESSAGE_CODE;
    }

    /**
     * Default builder.
     */
    public MDKMessage() {
        init();
    }

    /**
     * MDKMessage builder.
     * @param componentLabelName set the name of the component raising the message
     * @param message set the message message raised by the component
     * @param messageCode set the message code categorizing it
     */
    public MDKMessage(CharSequence componentLabelName,
                      CharSequence message,
                      @EnumKindOfMessage int messageCode) {
        init();
        this.componentLabelName = componentLabelName;
        this.message = message;
        this.messageCode = messageCode;
    }

    /**
     * MDKMessage builder.
     * @param componentLabelName set the name of the component raising the message
     * @param message set the message message raised by the component
     * @param messageCode set the message code categorizing it
     * @param messageType set the message type, default to ERROR_TYPE
     */
    public MDKMessage(CharSequence componentLabelName,
                      CharSequence message,
                      @EnumKindOfMessage int messageCode,
                      int messageType) {
        init();
        this.componentLabelName = componentLabelName;
        this.message = message;
        this.messageCode = messageCode;
        this.messageType = messageType;
    }

    /**
     * Getter.
     * @return componentId the component id
     */
    public int getComponentId() {
        return this.componentId;
    }

    /**
     * Setter.
     * @param componentId the new component id
     */
    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    /**
     * Getter.
     * @return componentLabelName the component label name
     */
    public CharSequence getComponentLabelName() {
        return this.componentLabelName;
    }

    /**
     * Setter.
     * @param componentLabelName the new component label name
     */
    public void setComponentLabelName(CharSequence componentLabelName) {
        this.componentLabelName = componentLabelName;
    }

    /**
     * Getter.
     * @return the message
     */
    public CharSequence getMessage() {
        return this.message;
    }

    /**
     * Setter.
     * @param message the new message
     */
    public void setMessage(CharSequence message) {
        this.message = message;
    }

    /**
     * Getter.
     * @return messageCode the message code
     */
    public int getMessageCode() {
        return this.messageCode;
    }

    /**
     * Setter.
     * @param messageCode the new message code
     */
    public void setMessageCode(Integer messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * Getter.
     * @return message type ERROR_TYPE or MESSAGE_TYPE
     */
    public int getMessageType() {
        return messageType;
    }

    /**
     * Setter.
     * @param messageType the message type
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(componentId);

        if (componentLabelName != null) {
            dest.writeString(componentLabelName.toString());
        } else {
            dest.writeString(EMPTY_MESSAGE);
        }
        if (message != null) {
            dest.writeString(message.toString());
        } else {
            dest.writeString(EMPTY_MESSAGE);
        }
        dest.writeInt(messageCode);
        dest.writeInt(messageType);
    }
}

