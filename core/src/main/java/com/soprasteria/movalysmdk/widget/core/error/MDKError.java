package com.soprasteria.movalysmdk.widget.core.error;

import java.lang.ref.WeakReference;

/**
 * Created by gestionnaire on 11/06/2015.
 */
public class MDKError {

    public static int NO_ERROR_CODE = -1;

    /** Id of the component raising the error. This one is set according:
     * - If the component is inside a rich one
     * - If the component is a basic one */
    private Integer componentId = null;

    /** Name of the component raising the error  */
    private CharSequence componentLabelName = null;

    /** Error message raised by the component  */
    private CharSequence errorMessage = null;

    /** Error code defining which kind of error it is, for example,
     * it can be used later for apply text style */
    private int errorCode = NO_ERROR_CODE;

    public MDKError() {
        //Nothing to do
    }

    /**
     * MDKError builder
     * @param componentLabelName set the name of the component raising the error
     * @param errorMessage set the error message raised by the component
     * @param errorCode set the error's code categorizing it
     */
    public MDKError (CharSequence componentLabelName,
                          CharSequence errorMessage,
                          int errorCode) {

        this.componentLabelName = componentLabelName;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public CharSequence getComponentLabelName() {
        return componentLabelName;
    }

    public void setComponentLabelName(CharSequence componentLabelName) {
        this.componentLabelName = componentLabelName;
    }

    public CharSequence getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(CharSequence errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

}

