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

import android.view.View;

import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasHint;
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
            // Check if the view is a hint
            if (view instanceof HasHint) {
                CharSequence hint = ((HasHint) view).getHint();
                if (hint != null && !hint.toString().endsWith(view.getContext().getString(R.string.mdkrichselector_mandatory_label_char))){
                    hint = hint.toString() + view.getContext().getString(R.string.mdkrichselector_mandatory_label_char);
                    ((HasHint) view).setHint(hint);
                }
            }

        } else if (view instanceof HasHint) {
            CharSequence hint = ((HasHint) view).getHint();

            if (hint != null &&
                    hint.toString().endsWith(view.getContext().getString(R.string.mdkrichselector_mandatory_label_char))) {
                hint = hint.subSequence(0, hint.length() - view.getContext().getString(R.string.mdkrichselector_mandatory_label_char).length());
            }

            ((HasHint) view).setHint(hint);
        }
    }
}
