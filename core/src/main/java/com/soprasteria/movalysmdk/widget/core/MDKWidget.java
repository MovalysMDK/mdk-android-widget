package com.soprasteria.movalysmdk.widget.core;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;

/**
 * Interface for MDK widget component.
 */
public interface MDKWidget {

    /**
     * Set the root id.
     * <p>The layout contains the error view.</p>
     * @param rootId the id of a view
     */
    void setRootViewId(int rootId);

    /**
     * Set the label id.
     * <p>The view used as label for this widget.</p>
     * @param labelId the id of a view
     */
    //FIXME: Rename to setLabelViewId
    void setLabelId(int labelId);

    /**
     * Set the helper id.
     * <p>The view used as helper for this widget.</p>
     * @param helperId the id of a view
     */
    void setHelperViewId(int helperId);

    /**
     * Set the error id.
     * <p>The view used as error for this widget.</p>
     * @param errorId the id of a view
     */
    void setErrorViewId(int errorId);

    /**
     * Set if the error view is not in the same layout as the others
     * widget (used for commons errors in a layout).
     * <p>If this value is passed to true, the root id must be set as well.</p>
     * @param useRootIdOnlyForError true if the error is not in the same layout as
     *                              the other sub widget
     */
    void setUseRootIdOnlyForError(boolean useRootIdOnlyForError);

    /**
     * Set the MDK error value on the widget.
     * @param error the error to set
     */
    void setMDKError(MDKError error);

    /**
     * Set the error value on the widget.
     * @param error the error to set
     */
    void setError(CharSequence error);

    /**
     * Set mandatory properties on widget.
     * @param mandatory true if mandatory, false otherwise
     */
    void setMandatory(boolean mandatory);

    /**
     * Return true if the widget is mandatory.
     * @return true if the widget is mandatory, false otherwise
     */
    boolean isMandatory();
}