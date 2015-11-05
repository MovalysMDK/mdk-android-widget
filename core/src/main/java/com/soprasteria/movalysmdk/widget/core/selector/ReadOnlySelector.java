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

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.behavior.HasEditFields;
import com.soprasteria.movalysmdk.widget.core.behavior.HasOneSelected;
import com.soprasteria.movalysmdk.widget.core.behavior.HasTextWatcher;
import com.soprasteria.movalysmdk.widget.core.helper.StateHelper;

/**
 * Handle the change of MDK readonly state on a MDK widget.
 * /!\ DO NOT MODIFY OR YOU MAY EXPERIENCE FAULTY BEHAVIOR
 */
public class ReadOnlySelector implements RichSelector{

    @Override
    public void onStateChange(int[] state, View v) {
        if (StateHelper.hasState(R.attr.state_readonly, state)) {
            if (v instanceof HasTextWatcher) {
                v.setBackgroundColor(ContextCompat.getColor(v.getContext(), android.R.color.transparent));
            } else if (v instanceof HasOneSelected) {
                v.setBackgroundColor(ContextCompat.getColor(v.getContext(), android.R.color.transparent));
            } else if (v instanceof HasEditFields) {
                for (int i=0; i<((HasEditFields)v).getEditFields().length; i++) {
                    View child = ((HasEditFields)v).getEditFields()[i];
                    if (child != null) {
                        child.setBackgroundColor(ContextCompat.getColor(v.getContext(), android.R.color.transparent));
                    }
                }
            }
        }
    }
}
