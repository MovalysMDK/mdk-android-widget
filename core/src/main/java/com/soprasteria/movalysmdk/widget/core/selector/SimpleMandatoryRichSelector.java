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
import com.soprasteria.movalysmdk.widget.core.helper.StateHelper;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;

/**
 * Handle the change of MDK mandatory state on a MDK widget.
 */
public class SimpleMandatoryRichSelector implements RichSelector {

    /**
     * Switch the mandatory state of the rich widget.
     * @param state the new mandatory state
     * @param view the widget's view where the change of state happened
     */
    public void onStateChange(int[] state, View view) {

        if (StateHelper.hasState(R.attr.state_mandatory, state)) {
            if (view instanceof HasLabel) {
                CharSequence label = ((HasLabel) view).getLabel();

                if (!label.toString().endsWith(view.getContext().getString(R.string.mandatory_char))) {
                    label = label.toString() + view.getContext().getString(R.string.mandatory_char);
                    ((HasLabel) view).setLabel(label);
                }
            }

        } else {
            if (view instanceof HasLabel) {
                CharSequence label = ((HasLabel) view).getLabel();

                if (label.toString().endsWith(view.getContext().getString(R.string.mandatory_char))) {
                    label = label.subSequence(0, label.length() - view.getContext().getString(R.string.mandatory_char).length());
                    ((HasLabel) view).setLabel(label);
                }
            }
        }
    }
}
