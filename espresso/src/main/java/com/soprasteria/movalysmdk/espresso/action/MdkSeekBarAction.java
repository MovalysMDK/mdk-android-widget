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
package com.soprasteria.movalysmdk.espresso.action;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.MDKRichSeekBar;
import com.soprasteria.movalysmdk.widget.basic.MDKSeekBar;

import org.hamcrest.Matcher;

/**
 * Action to manipulate MdkSeekBarWidgets.
 */
public class MdkSeekBarAction {


    /**
     * Constructor.
     */
    private MdkSeekBarAction() {
        // private because helper class.
    }

    /**
     * Set the value of a MDKSeekBar.
     * @param progress the value to set
     * @return ViewAction
     */
    public static ViewAction setMDKSeekbarProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ((MDKSeekBar) view).setSeekProgress(progress);
                //or ((SeekBar) view).setProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set a progress";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(MDKSeekBar.class);
            }
        };
    }


    /**
     * Set the value of a MDKRichSeekBar.
     * @param progress the value to set
     * @return ViewAction
     */
    public static ViewAction setMDKRichSeekbarProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ((MDKRichSeekBar) view).setSeekProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set a progress";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(MDKRichSeekBar.class);
            }
        };
    }
}
