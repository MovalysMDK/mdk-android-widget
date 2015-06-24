package com.soprasteria.movalysmdk.widget.test.actions;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CloseKeyboardAction;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Custom closeSoftKeyboard method.
 * Workaround to manage time delay to close soft keyboard bug.
 */
public class CloseSoftKeyboardDelayAction implements ViewAction {

    /**
     * The delay time to allow the soft keyboard to dismiss.
     */
    private static final long KEYBOARD_DISMISSAL_DELAY_MILLIS = 1000L;

    /**
     * The real {@link CloseKeyboardAction} instance.
     */
    private final ViewAction mCloseSoftKeyboard = new CloseKeyboardAction();

    @Override
    public Matcher<View> getConstraints() {
        return mCloseSoftKeyboard.getConstraints();
    }

    @Override
    public String getDescription() {
        return mCloseSoftKeyboard.getDescription();
    }

    @Override
    public void perform(final UiController uiController, final View view) {
        mCloseSoftKeyboard.perform(uiController, view);
        uiController.loopMainThreadForAtLeast(KEYBOARD_DISMISSAL_DELAY_MILLIS);
    }

    /**
     * Close keyboard and wait until closed.
     * @return a new CloseSoftKeyboardDelayAction action
     */
    public static ViewAction closeSoftKeyboardDelay() {
        return new CloseSoftKeyboardDelayAction();
    }
}
