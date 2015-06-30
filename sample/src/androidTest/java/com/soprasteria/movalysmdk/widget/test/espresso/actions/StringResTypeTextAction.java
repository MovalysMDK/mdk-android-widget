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
package com.soprasteria.movalysmdk.widget.test.espresso.actions;

import android.support.annotation.StringRes;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.TypeTextAction;
import android.support.test.espresso.action.ViewActions;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * TypeText action taking a string resource as parameter.
 */
public class StringResTypeTextAction implements ViewAction {

    /**
     * Proxy to the original typeText action.
     */
    private TypeTextAction typeTextAction = new TypeTextAction("", false);

    /**
     * String resource containing the text to type.
     */
    private @StringRes int stringResId ;

    /**
     * Add a tap to enable focus on view.
     */
    private boolean tapToFocus;

    /**
     * Constructor.
     * @param stringToBeTyped string resource containing the text to type.
     */
    public StringResTypeTextAction(@StringRes int stringToBeTyped) {
        this(stringToBeTyped, true);
    }

    /**
     * Constructor.
     * @param stringToBeTyped string resource containing the text to type.
     * @param tapToFocus add a tap to enable focus on view.
     */
    public StringResTypeTextAction(@StringRes int stringToBeTyped, boolean tapToFocus) {
        this.stringResId = stringToBeTyped;
        this.tapToFocus = tapToFocus;
    }

    @Override
    public Matcher<View> getConstraints() {
        return typeTextAction.getConstraints();
    }

    @Override
    public String getDescription() {
        return typeTextAction.getDescription();
    }

    @Override
    public void perform(UiController uiController, View view) {
        this.typeTextAction = new TypeTextAction(
                view.getContext().getString(this.stringResId), tapToFocus);
        this.typeTextAction.perform(uiController, view);
    }

    /**
     * Create the TypeText action whose text to type is contained in a string resource.
     * @param stringToBeTyped string resource containing the text to type.
     * @return StringResTypeTextAction
     */
    public static ViewAction typeStringResText(@StringRes int stringToBeTyped) {
        return ViewActions.actionWithAssertions(new StringResTypeTextAction(stringToBeTyped));
    }
}
