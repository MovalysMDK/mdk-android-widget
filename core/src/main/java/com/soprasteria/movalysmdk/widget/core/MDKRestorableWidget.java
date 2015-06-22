package com.soprasteria.movalysmdk.widget.core;

import android.os.Parcelable;


/**
 * MDKRestorableWidget interface definition.
 */
public interface MDKRestorableWidget {

    /**
     * Call the onSaveInstanceState method.
     * @return Parcelable Parcelable
     */
    Parcelable superOnSaveInstanceState();

    /**
     * Call the onRestoreInstanceState method.
     * @param state the state
     */
    void superOnRestoreInstanceState(Parcelable state);
}
