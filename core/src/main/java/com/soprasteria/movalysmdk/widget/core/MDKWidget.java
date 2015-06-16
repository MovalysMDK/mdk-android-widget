package com.soprasteria.movalysmdk.widget.core;

import com.soprasteria.movalysmdk.widget.core.error.MDKError;

/**
 * Interface for MDK inner widget component
 */
public interface MDKWidget {

    /**
     * Set the root id
     * the layout contains the error view
     * @param rootId the id of a view
     */
    void setRootId(int rootId);

    /**
     * Set the label id
     * the view used as label for this widget
     * @param labelId the id of a view
     */
    void setLabelId(int labelId);

    /**
     * Set the helper id
     * the view used as helper for this widget
     * @param helperId the id of a view
     */
    void setHelperId(int helperId);

    /**
     * Set the error id
     * the view used as error for this widget
     * @param errorId the id of a view
     */
    void setErrorId(int errorId);

    /**
     * Set if the error view is not in the same layout as the others
     * widget (used for commons errors in a layout).
     * If this value is passed to true, the root id must be set as well.
     * @param useRootIdOnlyForError true if the error is not in the same layout as
     *                              the other sub widget
     */
    void setUseRootIdOnlyForError(boolean useRootIdOnlyForError);

    /**
     * Set the MDK error value on the widget
     * @param error the error to set
     */
    void setMDKError(MDKError error);

    /**
     * Set the error value on the widget
     * @param error the error to set
     */
    void setError(CharSequence error);

    /**
     * Set mandatory properties on widget
     * @param mandatory true if mandatory, false otherwise
     */
    void setMandatory(boolean mandatory);

    /**
     * Return if the widget is mandatory
     * @return true if the widget is mandatory, false otherwise
     */
    boolean isMandatory();

    /**
     * Set unique id of the widget
     * @param parentId
     */
    void setUniqueId(int parentId);

    /**
     * Get uniqueId of the widget
     * @return
     */
    int getUniqueId();

    /**
     *
     * @param extraSpace the extra space
     * @return int[] ..
     */
    public int[] superOnCreateDrawableState(int extraSpace);

    /**
     *
     * @param baseState the base state
     * @param additionalState the additional state
     */
    public void callMergeDrawableStates(int[] baseState, int[] additionalState);
}