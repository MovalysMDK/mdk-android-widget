package com.soprasteria.movalysmdk.widget.core.behavior;

import android.text.TextWatcher;

/**
 * Interface to add test watcher capacity to widget.
 */
public interface HasTextWatcher {

    /**
     * Add a text watcher on the widget.
     * @param textWatcher the text watcher to register
     */
    void addTextChangedListener(TextWatcher textWatcher);

    /**
     * Remove a text watcher from the widget.
     * @param textWatcher the text watcher to remove
     */
    void removeTextChangedListener(TextWatcher textWatcher);

}
