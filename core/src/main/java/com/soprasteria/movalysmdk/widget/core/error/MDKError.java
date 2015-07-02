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
public class MDKError implements Parcelable{

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

    /** Error message raised by the component.  */
    private CharSequence errorMessage;

    /** No error code defined. */
    public static final int NO_ERROR_CODE = -1;

    /**
     * Error code defining which kind of error it is.
     * <p>For example, it can be used later for apply text style.</p>
     */
    private int errorCode;

    /**
     * Constructor.
     * @param in the new parcelable
     */
    protected MDKError(Parcel in) {
        componentId = in.readInt();
        errorCode = in.readInt();
    }

    /**
     * Required field that makes Parcelables from a Parcel.
     */
    public static final Creator<MDKError> CREATOR = new Creator<MDKError>() {
        @Override
        public MDKError createFromParcel(Parcel in) {
            return new MDKError(in);
        }

        @Override
        public MDKError[] newArray(int size) {
            return new MDKError[size];
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
    public MDKError() {
        init();
    }

    /**
     * MDKError builder.
     * @param componentLabelName set the name of the component raising the error
     * @param errorMessage set the error message raised by the component
     * @param errorCode set the error's code categorizing it
     */
    public MDKError (CharSequence componentLabelName,
                          CharSequence errorMessage,
                          int errorCode) {
        init();
        this.componentLabelName = componentLabelName;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
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
     * @return errorMessage the error message
     */
    public CharSequence getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Setter.
     * @param errorMessage the new error message
     */
    public void setErrorMessage(CharSequence errorMessage) {
        this.errorMessage = errorMessage;
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
            dest.writeString("");
        }
        if (errorMessage != null) {
            dest.writeString(errorMessage.toString());
        } else {
            dest.writeString("");
        }
        dest.writeInt(errorCode);
    }
}

