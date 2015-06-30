package com.soprasteria.movalysmdk.widget.test.actions;

import android.support.annotation.StringRes;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.TypeTextAction;
import android.support.test.espresso.action.ViewActions;
import android.view.View;

import org.hamcrest.Matcher;

public class StringResTypeTextAction implements ViewAction {

    private TypeTextAction typeTextAction = new TypeTextAction("", false);

    private @StringRes int stringResId ;

    private boolean tapToFocus;

    public StringResTypeTextAction(@StringRes int stringToBeTyped) {
        this(stringToBeTyped, true);
    }

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

    public static ViewAction typeStringResText(@StringRes int stringToBeTyped) {
        return ViewActions.actionWithAssertions(new StringResTypeTextAction(stringToBeTyped));
    }
}
