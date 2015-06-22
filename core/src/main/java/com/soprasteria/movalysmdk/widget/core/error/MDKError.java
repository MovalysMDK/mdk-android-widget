package com.soprasteria.movalysmdk.widget.core.error;


/**
 * MDKError class definition.
 */
public class MDKError {

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

}

