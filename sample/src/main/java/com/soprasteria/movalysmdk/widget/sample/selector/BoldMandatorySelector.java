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
package com.soprasteria.movalysmdk.widget.sample.selector;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.helper.StateHelper;
import com.soprasteria.movalysmdk.widget.core.selector.RichSelector;
import com.soprasteria.movalysmdk.widget.sample.R;

/**
 * Rich selector to make the text bold if mandatory.
 */
public class BoldMandatorySelector implements RichSelector {

    @Override
    public void onStateChange(int[] state, View view) {

        TextView tv = null;
        if (view instanceof TextView) {
            tv = (TextView) view;
        } else if (view instanceof MDKBaseRichWidget && ((MDKBaseRichWidget) view).getInnerWidget() instanceof TextView ) {
            tv = (TextView) ((MDKBaseRichWidget) view).getInnerWidget();
        }

        if (tv != null) {
            setTypefaceBold(tv, StateHelper.hasState(R.attr.state_mandatory, state));
        }
    }

    /**
     * Sets the typeface to bold on the given {@link TextView}.
     * @param textView the {@link TextView} to set
     * @param bold true to set the typeface to bold
     */
    private void setTypefaceBold(TextView textView, boolean bold) {
        Typeface tf = textView.getTypeface();

        int setTypeFace = Typeface.NORMAL;

        if (bold) {
            if (tf != null && tf.isItalic()) {
                setTypeFace = Typeface.BOLD_ITALIC;
            } else {
                setTypeFace = Typeface.BOLD;
            }
        } else {
            if (tf != null && tf.isItalic()) {
                setTypeFace = Typeface.ITALIC;
            }
        }

        textView.setTypeface(Typeface.create(textView.getTypeface(), setTypeFace));

    }
}
