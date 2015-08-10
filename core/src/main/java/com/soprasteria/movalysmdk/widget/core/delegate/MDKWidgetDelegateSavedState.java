package com.soprasteria.movalysmdk.widget.core.delegate;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

/**
 * MDKWidgetDelegateSavedState class definition.
 */
class MDKWidgetDelegateSavedState extends View.BaseSavedState {

    /**
     * qualifier.
     */
    String qualifier;
    /**
     * resHelperId.
     */
    int resHelperId;
    /**
     * richSelectors.
     */
    List<String> richSelectors;

    /**
     * rootViewId.
     */
    int rootId;
    /**
     * labelViewId.
     */
    int labelId;
    /**
     * showFloatingLabelAnimId.
     */
    int showFloatingLabelAnimId;
    /**
     * hideFloatingLabelAnimId.
     */
    int hideFloatingLabelAnimId;
    /**
     * helperViewId.
     */
    int helperId;
    /**
     * errorViewId.
     */
    int errorId;
    /**
     * uniqueId.
     */
    int uniqueId;

    /**
     * useRootIdOnlyForError.
     */
    boolean useRootIdOnlyForError;
    /**
     * valid.
     */
    boolean valid;
    /**
     * mandatory.
     */
    boolean mandatory;
    /**
     * error.
     */
    boolean error;

    /**
     * MDKWidgetDelegateSavedState public constructor.
     * @param superState the super state
     */
    MDKWidgetDelegateSavedState(Parcelable superState) {
        super(superState);
    }

    /**
     * MDKWidgetDelegateSavedState private constructor.
     * @param in the super state
     */
    private MDKWidgetDelegateSavedState(Parcel in) {
        super(in);

        this.qualifier = in.readString();
        this.resHelperId = in.readInt();

        // TODO : read the richSelectors

        this.rootId = in.readInt();
        this.labelId = in.readInt();
        this.showFloatingLabelAnimId = in.readInt();
        this.hideFloatingLabelAnimId = in.readInt();
        this.helperId = in.readInt();
        this.errorId = in.readInt();
        this.uniqueId = in.readInt();

        this.useRootIdOnlyForError = in.readByte() != 0;
        this.valid = in.readByte() != 0;
        this.mandatory = in.readByte() != 0;
        this.error = in.readByte() != 0;
    }

    /**
     * Initializes the Saved state object with a {@link MDKWidgetDelegateValueObject}.
     * @param valueObject the {@link MDKWidgetDelegateValueObject} object to use for initialisation
     */
    public void initializeFromValueObject(MDKWidgetDelegateValueObject valueObject) {
        this.qualifier = valueObject.getQualifier();
        this.resHelperId = valueObject.getResHelperId();
        this.richSelectors = valueObject.getRichSelectors();

        this.rootId = valueObject.getRootViewId();
        this.labelId = valueObject.getLabelViewId();
        this.showFloatingLabelAnimId = valueObject.getShowFloatingLabelAnimId();
        this.hideFloatingLabelAnimId = valueObject.getHideFloatingLabelAnimId();
        this.helperId = valueObject.getHelperViewId();
        this.errorId = valueObject.getErrorViewId();
        this.uniqueId = valueObject.getUniqueId();

        this.useRootIdOnlyForError = valueObject.isUseRootIdOnlyForError();
        this.valid = valueObject.isValid();
        this.mandatory = valueObject.isMandatory();
        this.error = valueObject.isError();
    }

    /**
     * Restores values in {@link MDKWidgetDelegateValueObject}.
     * @param valueObject the {@link MDKWidgetDelegateValueObject} object to restore
     */
    public void restoreValueObject(MDKWidgetDelegateValueObject valueObject) {
        valueObject.setQualifier(this.qualifier);
        valueObject.setResHelperId(this.resHelperId);
        valueObject.setRichSelectors(this.richSelectors);

        valueObject.setRootViewId(this.rootId);
        valueObject.setLabelViewId(this.labelId);
        valueObject.setShowFloatingLabelAnimId(this.showFloatingLabelAnimId);
        valueObject.setHideFloatingLabelAnimId(this.hideFloatingLabelAnimId);
        valueObject.setHelperViewId(this.helperId);
        valueObject.setErrorViewId(this.errorId);
        valueObject.setUniqueId(this.uniqueId);

        valueObject.setUseRootIdOnlyForError(this.useRootIdOnlyForError);
        valueObject.setValid(this.valid);
        valueObject.setMandatory(this.mandatory);
        valueObject.setError(this.error);
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        super.writeToParcel(out, flags);

        out.writeString(this.qualifier);
        out.writeInt(this.resHelperId);
        out.writeStringList(this.richSelectors);

        out.writeInt(this.rootId);
        out.writeInt(this.labelId);
        out.writeInt(this.showFloatingLabelAnimId);
        out.writeInt(this.hideFloatingLabelAnimId);
        out.writeInt(this.helperId);
        out.writeInt(this.errorId);
        out.writeInt(this.uniqueId);

        out.writeByte((byte) (this.useRootIdOnlyForError ? 1 : 0));
        out.writeByte((byte) (this.valid ? 1 : 0));
        out.writeByte((byte) (this.mandatory ? 1 : 0));
        out.writeByte((byte) (this.error ? 1 : 0));
    }

    /**
     * Required field that makes Parcelables from a Parcel.
     */
    public static final Creator<MDKWidgetDelegateSavedState> CREATOR =
        new Creator<MDKWidgetDelegateSavedState>() {

            @Override
            public MDKWidgetDelegateSavedState createFromParcel(Parcel in) {
                return new MDKWidgetDelegateSavedState(in);
            }

            @Override
            public MDKWidgetDelegateSavedState[] newArray(int size) {
                return new MDKWidgetDelegateSavedState[size];
            }
        };
}
