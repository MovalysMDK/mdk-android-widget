package com.soprasteria.movalysmdk.widget.core.behavior;

import android.view.View;

/**
 * Created by abelliard on 04/06/2015.
 */
public interface HasButtons extends View.OnClickListener {

    public void registerPrimaryButton();

    public void registerSecondaryButton();
}
