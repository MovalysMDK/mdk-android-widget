package com.soprasteria.movalysmdk.widget.core.error;


/**
 * MDKError class definition
 * Created by gestionnaire on 11/06/2015.
 */
public class MDKError {

    /** Id of the component raising the error. This one is set according:
     * - If the component is inside a rich one
     * - If the component is a basic one */
    private Integer componentId = null;

    /** Name of the component raising the error.  */
    private CharSequence componentLabelName = null;

    /** Error message raised by the component.  */
    private CharSequence errorMessage = null;

    public static final int NO_ERROR_CODE = -1;

    /** Error code defining which kind of error it is, for example,
     * it can be used later for apply text style. */
    private int errorCode = NO_ERROR_CODE;

    public MDKError() {
        //Nothing to do
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

        this.componentLabelName = componentLabelName;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * Getter.
     * @return componentId the component id
     */
    public Integer getComponentId() {
        return new Integer(componentId);
    }

    /**
     * Setter.
     * @param componentId the new component id
     */
    public void setComponentId(Integer componentId) {
        this.componentId = new Integer(componentId);
    }

    /**
     * Getter.
     * @return componentLabelName the component label name
     */
    public CharSequence getComponentLabelName() {
        return componentLabelName;
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
        return errorMessage;
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
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * Setter.
     * @param errorCode the new error code
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

}

