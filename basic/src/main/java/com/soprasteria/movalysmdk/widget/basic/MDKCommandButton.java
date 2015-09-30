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
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.delegate.MDKCommandDelegate;

/**
 * Button to execute a widget broadcast action.
 * <p>
 *     3 arguments must be filled to work :
 *     <ul>
 *         <li>referenceWidget : @ResId,  who indicate witch widget action will be triggered</li>
 *         <li>action : @ResString or String, who indicate the executed action (Broadcasted intent)</li>
 *         <li>command : String who indicate the action to execute on widget (primary or secondary or other string)</li>
 *     </ul>
 * </p>
 */
public class MDKCommandButton extends AppCompatButton implements View.OnClickListener {

    /** widget delegate. */
    private MDKCommandDelegate delegate;

    /**
     * Default constructor.
     * @param context Android context
     */
    public MDKCommandButton(Context context) {
        this(context, null);
    }

    /**
     * Defaut Constructor.
     * @param context Android context
     * @param attrs android xml attributes
     */
    public MDKCommandButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            delegate = new MDKCommandDelegate(this, attrs);
        }
    }

    /**
     * Default Constructor.
     * @param context Android context
     * @param attrs android xml attributes
     * @param defStyleAttr default style attribut
     */
    public MDKCommandButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            delegate = new MDKCommandDelegate(this, attrs);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.delegate.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        this.delegate.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v) {
        this.delegate.onClick();
    }
}
