package com.soprasteria.movalysmdk.widget.core.behavior;

import android.text.TextWatcher;

/**
 * Created by abelliard on 04/06/2015.
 */
public interface HasTextWatcher {

    public void addTextWatcher(TextWatcher textWatcher);

    public void removeTextWatcher(TextWatcher textWatcher);

}
