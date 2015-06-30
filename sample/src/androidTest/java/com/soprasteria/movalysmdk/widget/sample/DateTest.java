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
package com.soprasteria.movalysmdk.widget.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.soprasteria.movalysmdk.widget.basic.MDKRichDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Non regression testing class for custom MDK Date widget
 */
public class DateTest {

    @Rule
    public ActivityTestRule<DateActivity> mActivityRule = new ActivityTestRule<>(DateActivity.class);

    @Test
    /**
     * Check behaviour when the date is not valid
     */
    public void testNotFilledDate1() {


        assertThat(mActivityRule.getActivity(), is(notNullValue()));

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
            onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
                            .check(matches(withText(R.string.mdk_error_message_1)));

            // Update time
            mActivityRule.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    oMDKRichDateTime.getInnerWidget().setTime(date);
                }
            });

            // click validate button
            onView(withId(R.id.validateButton)).perform(click());

            // Check that error component message is empty
            onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichDateTime_withLabelAndMandatory))))
                    .check(matches(withText("")));

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @Test
    /**
     * Check behaviour when the date is valid
     */
    public void testValidDate() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));
        //onView(withId(R.id.rich_date_time_1)).perform(

        // click validate button
        //onView(withId(R.id.validateButton)).perform(click());

        // check no error
       // onView(withId(R.id.errorText)).check(matches(withText(isEmptyOrNullString())));
    }
}
