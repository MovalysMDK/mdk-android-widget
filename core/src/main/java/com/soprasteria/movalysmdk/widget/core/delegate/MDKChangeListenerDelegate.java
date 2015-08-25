package com.soprasteria.movalysmdk.widget.core.delegate;

import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Delegate class for the {@link ChangeListener} objects.
 */
public class MDKChangeListenerDelegate implements HasChangeListener {

    /** {@link ChangeListener} array. */
    private List<ChangeListener> listeners;

    /**
     * Constructor.
     */
    public MDKChangeListenerDelegate() {
        listeners = new ArrayList<>();
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify the registered {@link ChangeListener}.
     */
    public void notifyListeners() {
        for (ChangeListener listener : listeners) {
            listener.onChanged();
        }
    }
}
