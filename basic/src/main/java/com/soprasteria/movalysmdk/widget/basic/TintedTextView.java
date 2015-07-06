/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.internal.widget.TintInfo;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * The TintedTextView should be used to add Tinted behavior to a TextView.
 * <p>This class implement basic background tinted behavior (copied from AppCompatEditText)</p>
 */
public class TintedTextView extends AppCompatTextView implements TintableBackgroundView {

    //FIXME SMA : cette classe n'est pas utilisable directement par l'utilisateur à changer de package

    /**
     * Attributes to apply tint.
     */
    private static final int[] TINT_ATTRS = {
            android.R.attr.background
    };
    /** tint background info. */
    private TintInfo mInternalBackgroundTint;
    /** tint background. */
    private TintInfo mBackgroundTint;
    /** Tint manager. */
    private TintManager mTintManager;

    /**
     * Default Android Constructor.
     * <p>Call the TintedTextView(context, null) constructor</p>
     * @param context the android context
     */
    public TintedTextView(Context context) {
        this(context, null);
    }

    /**
     * Default Android Constructor.
     * <p>Call the TintedTextView(context, attrs, R.attr.mdkTintedTextViewStyle) constructor</p>
     * @param context the android context
     * @param attrs the view attributes
     */
    public TintedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.mdkTintedTextViewStyle);
    }

    /**
     * Default Android Constructor.
     * <p>Call the tint manager and create backward compatibility.</p>
     * @param context the android context
     * @param attrs the view attributes
     * @param defStyleAttr attributes for style
     */
    public TintedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                TINT_ATTRS, defStyleAttr, 0);
        if (a.hasValue(0)) {
            ColorStateList tint = a.getTintManager().getTintList(a.getResourceId(0, -1));
            if (tint != null) {
                setInternalBackgroundTint(tint);
            }
        }
        mTintManager = a.getTintManager();
        a.recycle();
    }


    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        // Update the default background tint
        setInternalBackgroundTint(mTintManager != null ? mTintManager.getTintList(resId) : null);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        // We don't know that this drawable is, so we need to clear the default background tint
        setInternalBackgroundTint(null);
    }

    /**
     * This should be accessed via
     * {@link android.support.v4.view.ViewCompat#setBackgroundTintList(android.view.View,
     * android.content.res.ColorStateList)}.
     * @param tint tint color state
     *
     * @hide
     */
    @Override
    public void setSupportBackgroundTintList(@Nullable ColorStateList tint) {
        if (mBackgroundTint == null) {
            mBackgroundTint = new TintInfo();
        }
        mBackgroundTint.mTintList = tint;
        mBackgroundTint.mHasTintList = true;

        applySupportBackgroundTint();
    }

    /**
     * This should be accessed via
     * {@link android.support.v4.view.ViewCompat#getBackgroundTintList(android.view.View)}.
     *
     * @hide
     */
    @Override
    @Nullable
    public ColorStateList getSupportBackgroundTintList() {
        return mBackgroundTint != null ? mBackgroundTint.mTintList : null;
    }

    /**
     * This should be accessed via
     * {@link android.support.v4.view.ViewCompat#setBackgroundTintMode(android.view.View, android.graphics.PorterDuff.Mode)}.
     * @param tintMode the tint mode to use in widget
     *
     * @hide
     */
    @Override
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (mBackgroundTint == null) {
            mBackgroundTint = new TintInfo();
        }
        mBackgroundTint.mTintMode = tintMode;
        mBackgroundTint.mHasTintMode = true;

        applySupportBackgroundTint();
    }

    /**
     * This should be accessed via
     * {@link android.support.v4.view.ViewCompat#getBackgroundTintMode(android.view.View)}.
     *
     * @return the Porter.Mode of the widget
     *
     * @hide
     */
    @Override
    @Nullable
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return mBackgroundTint != null ? mBackgroundTint.mTintMode : null;
    }

    /**
     * Call to apply background Tint on drawable state change.
     */
    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        applySupportBackgroundTint();
    }

    /**
     * Apply the tint on the background.
     */
    private void applySupportBackgroundTint() {
        if (getBackground() != null) {
            if (mBackgroundTint != null) {
                TintManager.tintViewBackground(this, mBackgroundTint);
            } else if (mInternalBackgroundTint != null) {
                TintManager.tintViewBackground(this, mInternalBackgroundTint);
            }
        }
    }

    /**
     * Set the tint on the component and apply the tint.
     * @param tint the new tint to apply
     */
    private void setInternalBackgroundTint(ColorStateList tint) {
        if (tint != null) {
            if (mInternalBackgroundTint == null) {
                mInternalBackgroundTint = new TintInfo();
            }
            mInternalBackgroundTint.mTintList = tint;
            mInternalBackgroundTint.mHasTintList = true;
        } else {
            mInternalBackgroundTint = null;
        }
        applySupportBackgroundTint();
    }
}
