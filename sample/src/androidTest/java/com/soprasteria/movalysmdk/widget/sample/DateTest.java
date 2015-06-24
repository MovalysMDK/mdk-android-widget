package com.soprasteria.movalysmdk.widget.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;


import com.soprasteria.movalysmdk.widget.basic.MDKRichDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DateTest {

    @Rule
    public ActivityTestRule<DateActivity> mActivityRule = new ActivityTestRule<>(DateActivity.class);

    @Test
    public void testNotFilledDate1() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        // check error
        //onView(allOf(withId(R.id.errorText), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
        //       .check(matches(withText("42 /!\\ Must be filled")));


        // Write valid date for first elements
        String startDate = "10:24 AM 2015 02 02";
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a yyyy MM dd");
        try {
            final Date date = sdf1.parse(startDate);
            final MDKRichDateTime oMDKRichDateTime = (MDKRichDateTime) mActivityRule.getActivity().findViewById(R.id.mdkRichDateTime_withLabelAndMandatory);
            //
            mActivityRule.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    oMDKRichDateTime.getInnerWidget().setDate(date);
                }
            });

            // click validate button
            onView(withId(R.id.validateButton)).perform(click());


            // check error
            TextView toto = (TextView) mActivityRule.getActivity().findViewById(R.id.component_error);
            TextView titi = (TextView) mActivityRule.getActivity().findViewById(R.id.component_internal);

            onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
                            .check(matches(withText("42 /!\\ Must be filled")));

            // Update time
            mActivityRule.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    oMDKRichDateTime.getInnerWidget().setTime(date);
                }
            });

            // click validate button
            onView(withId(R.id.validateButton)).perform(click());

            onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
                    .check(matches(withText("")));

            //onView(allOf(withId(R.id.component_internal_time), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
            //        .check(matches(withText("toto")));
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @Test
    public void testValidDate() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));
        //onView(withId(R.id.rich_date_time_1)).perform(

        // click validate button
        //onView(withId(R.id.validateButton)).perform(click());

        // check no error
       // onView(withId(R.id.errorText)).check(matches(withText(isEmptyOrNullString())));
    }
}
