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
package com.soprasteria.movalysmdk.widget.core.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * alpha ne marche pas dans les selecteurs -> gere l'apha sur l'enable
 * ne pas utiliser dans le code, uniquement dans les layouts.
 */
public class AlphaButton extends ImageButton {

    // TODO a mettre en constante dans les styles
    /** alpha applied when component is disabled. */
    private static final int DISABLED_ALPHA = 70;

    /**
     * Constructor.
     * @param context android context
     * @param attrs xml attributes
     */
    public AlphaButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor.
     * @param context android context
     * @param attrs xml attributes
     * @param defStyleAttr style
     */
    public AlphaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled != isEnabled()) {
            super.setEnabled(enabled);
            this.setAlpha(isEnabled() ? 255 : DISABLED_ALPHA);
        }
    }
}
