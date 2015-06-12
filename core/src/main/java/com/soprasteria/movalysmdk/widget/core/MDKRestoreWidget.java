package com.soprasteria.movalysmdk.widget.core;

import android.os.Parcelable;

public interface MDKRestoreWidget {

    /**
     * Call the onSaveInstanceState method
     * @return
     */
    Parcelable superOnSaveInstanceState ();

    /**
     * Call the onRestoreInstanceState method
     * @param state
     */
    void superOnRestoreInstanceState(Parcelable state);
}
