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
package com.soprasteria.movalysmdk.widget.core.error;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * MDKError class definition.
 */
public class MDKMessage implements Parcelable {


    /** No error code defined. */
    public static final int NO_ERROR_CODE = -1;

    /** Error type. */
    public static final int ERROR_TYPE = 0;
    /** Message type. */
    public static final int MESSAGE_TYPE = 1;
    //FIXME : Ajouter IntDef pour gérer une enumeration sur le type

    /**
     * Id of the component raising the error. This one is set according:
     * <ul>
     *     <li>If the component is inside a rich one.</li>
     *     <li>If the component is a basic one.</li>
     * </ul>
     */
    private int componentId;

    /** Name of the component raising the error.  */
    private CharSequence componentLabelName;

    /** message raised by the component.  */
    private CharSequence message;

    /**
     * Error code defining which kind of error it is.
     * <p>For example, it can be used later for apply text style.</p>
     */
    private int errorCode;

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
        errorCode = in.readInt();
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
        errorCode = NO_ERROR_CODE;
    }

    /**
     * Default builder.
     */
    public MDKMessage() {
        init();
    }

    /**
     * MDKError builder.
     * @param componentLabelName set the name of the component raising the error
     * @param message set the error message raised by the component
     * @param errorCode set the error's code categorizing it
     */
    public MDKMessage(CharSequence componentLabelName,
                      CharSequence message,
                      int errorCode) {
        init();
        this.componentLabelName = componentLabelName;
        this.message = message;
        this.errorCode = errorCode;
    }

    /**
     * MDKError builder.
     * @param componentLabelName set the name of the component raising the error
     * @param message set the error message raised by the component
     * @param errorCode set the error's code categorizing it
     * @param messageType set the message type, default to ERROR_TYPE
     */
    public MDKMessage(CharSequence componentLabelName,
                      CharSequence message,
                      int errorCode,
                      int messageType) {
        init();
        this.componentLabelName = componentLabelName;
        this.message = message;
        this.errorCode = errorCode;
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
     * @return errorCode the error code
     */
    public int getErrorCode() {
        return this.errorCode;
    }

    /**
     * Setter.
     * @param errorCode the new error code
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
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
            //FIXME prévoir une constante pour ""
            dest.writeString("");
        }
        if (message != null) {
            dest.writeString(message.toString());
        } else {
            dest.writeString("");
        }
        dest.writeInt(errorCode);
        dest.writeInt(messageType);
    }
}

