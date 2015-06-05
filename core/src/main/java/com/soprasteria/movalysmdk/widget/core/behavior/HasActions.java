package com.soprasteria.movalysmdk.widget.core.behavior;

import android.view.View;

/**
 * Interface to add button handling capacity to a widget
 */
public interface HasActions extends View.OnClickListener {

    void registerActionViews();

   /* *//**
     * register primary action View
     *//*
    public void registerPrimaryActionView();

    *//**
     * register secondary action View
     *//*
    public void registerSecondaryActionView();*/
}
