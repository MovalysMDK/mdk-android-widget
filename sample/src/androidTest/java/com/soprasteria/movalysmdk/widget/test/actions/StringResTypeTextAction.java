package com.soprasteria.movalysmdk.widget.test.actions;

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
