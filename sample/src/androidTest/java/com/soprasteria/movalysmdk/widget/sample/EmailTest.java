package com.soprasteria.movalysmdk.widget.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmailTest {

    @Rule
    public ActivityTestRule<EmailActivity> mActivityRule = new ActivityTestRule<>(EmailActivity.class);

    @Test
    public void testRichEmail() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));
        onView(withId(R.id.view)).perform(typeText("myemail@soprasteria.com"));
    }

    @Test
    public void testEmail() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));
        onView(withId(R.id.view2)).perform(typeText("myemail@soprasteria.com"));
    }
}
