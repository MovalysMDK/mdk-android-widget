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
package com.soprasteria.movalysmdk.widget.core.selector;

import android.content.Context;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.helper.StateHelper;

/**
 * Handle the change of MDK mandatory state on a MDK widget.
 */
public class SimpleMandatoryRichSelector implements RichSelector {

    /**
     * Switch the mandatory state of the rich widget.
     * @param state the new mandatory state
     * @param view the widget's view where the change of state happened
     */
    @Override
    public void onStateChange(int[] state, View view) {

        // Check if the mandatory attribute is already present into the current view's state
        if (StateHelper.hasState(R.attr.state_mandatory, state)) {
            // Check if the view is a hint or has a label
            if (view instanceof HasHint) {
                ((HasHint) view).setHint(computeAddMandatoryString(view.getContext(), ((HasHint) view).getHint()));
            } else if (view instanceof HasLabel) {
                ((HasLabel) view).setLabel(computeAddMandatoryString(view.getContext(), ((HasLabel) view).getLabel()));
            }

        } else {
            // Check if the view is a hint or has a label
            if (view instanceof HasHint) {
                ((HasHint) view).setHint(computeRemoveMandatoryString(view.getContext(), ((HasHint) view).getHint()));
            } else if (view instanceof HasLabel) {
                ((HasLabel) view).setLabel(computeRemoveMandatoryString(view.getContext(), ((HasLabel) view).getLabel()));
            }

        }
    }

    /**
     * Adds the mandatory string to the displayed value.
     * @param context android context
     * @param display the displayed string
     * @return the computed string
     */
    private String computeAddMandatoryString(Context context, CharSequence display) {
        String result = null;

        if (display != null) {
            result = display.toString();
            if (!result.endsWith(context.getString(R.string.mdkrichselector_mandatory_label_char))) {
                result = display + context.getString(R.string.mdkrichselector_mandatory_label_char);
            }
        }

        return result;
    }

    /**
     * Removes the mandatory string from the displayed value.
     * @param context android context
     * @param display the displayed string
     * @return the computed string
     */
    private String computeRemoveMandatoryString(Context context, CharSequence display) {
        String result = null;

        if (display != null) {
            result = display.toString();
            if (result.endsWith(context.getString(R.string.mdkrichselector_mandatory_label_char))) {
                result = display.subSequence(0, display.length() - context.getString(R.string.mdkrichselector_mandatory_label_char).length()).toString();
            }
        }

        return result;
    }
}
