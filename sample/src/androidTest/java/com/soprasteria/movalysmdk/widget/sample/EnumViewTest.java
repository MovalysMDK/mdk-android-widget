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
import android.support.v4.content.ContextCompat;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.AttributeSet;
import android.util.Xml;
import android.widget.ImageView;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.basic.MDKEnumView;
import com.soprasteria.movalysmdk.widget.basic.MDKRichEnumView;
import com.soprasteria.movalysmdk.widget.sample.enums.MobileOS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.soprasteria.movalysmdk.espresso.matcher.MdkViewMatchers.withConcatText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for MDKEnumImage widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EnumViewTest {



    /**
     * Activity used for these tests.
     */
    @Rule
    public ActivityTestRule<EnumViewActivity> mActivityRule = new ActivityTestRule<>(EnumViewActivity.class);

    /**
     * Tests that the drawable has been correctly set after a setValueFromEnum operation.
     */
    @Test
    public void imageFromEnumTest(){
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        MDKEnumView enumimage = (MDKEnumView) mActivityRule.getActivity().findViewById(R.id.mdkEnumImage_withErrorAndCommandOutside);
        assertThat(null, ((ImageView) enumimage.getModeView()).getDrawable().getConstantState().equals(ContextCompat.getDrawable(mActivityRule.getActivity(), R.drawable.enum_mobileos_ios).getConstantState()));
    }

    /**
     * Tests that values are correctly set and fallback behaviors work.
     */
    @Test
    public void setValueTest(){
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        XmlPullParser parser = mActivityRule.getActivity().getResources().getLayout(R.layout.test_mdkenumview);
        AttributeSet attributes = Xml.asAttributeSet(parser);

        MDKEnumView enumimage = new MDKEnumView(mActivityRule.getActivity(), attributes);

        //set drawable from java enum
        enumimage.setValueFromEnum(MobileOS.WINDOWS);
        assertThat(null, ContextCompat.getDrawable(mActivityRule.getActivity(), R.drawable.enum_mobileos_windows).getConstantState().equals(((ImageView) enumimage.getModeView()).getDrawable().getConstantState()));

        //set drawable from string
        enumimage.setValueFromString("mobileos_android");
        assertThat(null, ContextCompat.getDrawable(mActivityRule.getActivity(), R.drawable.enum_mobileos_android).getConstantState().equals(((ImageView) enumimage.getModeView()).getDrawable().getConstantState()));

        //fallback to string
        enumimage.setValueFromEnum(MobileOS.BLACKBERRY);
       assertThat(null, "BlackBerry".equals(((TextView) enumimage.getModeView()).getText().toString()));

        enumimage.setValueFromString("mobileos_blackberry");
        assertThat(null, "enum_mobileos_blackberry".equals(((TextView) enumimage.getModeView()).getText().toString()));

        //fallback to name
        enumimage.setValueFromEnum(MobileOS.FIREFOX_OS);
        assertThat(null, "enum_MobileOS_FIREFOX_OS".equals(((TextView) enumimage.getModeView()).getText().toString()));

        enumimage.setValueFromString("hello_world");
        assertThat(null, "enum_hello_world".equals(((TextView) enumimage.getModeView()).getText().toString()));

    }

    /**
     * Tests the editable property. Clicks the view to cycle through possible values of the enum.
     */
    @Test
    public void editableTest(){
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        assertThat(null, ContextCompat.getDrawable(mActivityRule.getActivity(), R.drawable.enum_mobileos_android).getConstantState().equals(((ImageView) ((MDKRichEnumView) mActivityRule.getActivity().findViewById(R.id.mdkRichEnumImage_withLabelAndError)).getModeView()).getDrawable().getConstantState()));

        // Click the view
        onView(withId(R.id.mdkRichEnumImage_withLabelAndError)).perform(scrollTo(), click());
        assertThat(null, ContextCompat.getDrawable(mActivityRule.getActivity(), R.drawable.enum_mobileos_ios).getConstantState().equals(((ImageView) ((MDKRichEnumView) mActivityRule.getActivity().findViewById(R.id.mdkRichEnumImage_withLabelAndError)).getModeView()).getDrawable().getConstantState()));

        // Click the view
        onView(withId(R.id.mdkRichEnumImage_withLabelAndError)).perform(scrollTo(), click());
        assertThat(null, ContextCompat.getDrawable(mActivityRule.getActivity(), R.drawable.enum_mobileos_windows).getConstantState().equals(((ImageView) ((MDKRichEnumView) mActivityRule.getActivity().findViewById(R.id.mdkRichEnumImage_withLabelAndError)).getModeView()).getDrawable().getConstantState()));

        // Click the view
        onView(withId(R.id.mdkRichEnumImage_withLabelAndError)).perform(scrollTo(), click());
        assertThat(null, "BlackBerry".equals(((TextView) ((MDKRichEnumView) mActivityRule.getActivity().findViewById(R.id.mdkRichEnumImage_withLabelAndError)).getModeView()).getText().toString()));

        // Click the view
        onView(withId(R.id.mdkRichEnumImage_withLabelAndError)).perform(scrollTo(), click());
        assertThat(null, "enum_MobileOS_FIREFOX_OS".equals(((TextView) ((MDKRichEnumView) mActivityRule.getActivity().findViewById(R.id.mdkRichEnumImage_withLabelAndError)).getModeView()).getText().toString()));

        // Click the view
        onView(withId(R.id.mdkRichEnumImage_withLabelAndError)).perform(scrollTo(), click());
        assertThat(null, "Symbian".equals(((TextView) ((MDKRichEnumView) mActivityRule.getActivity().findViewById(R.id.mdkRichEnumImage_withLabelAndError)).getModeView()).getText().toString()));

        // END OF ENUM REACHED
        // MUST GO BACK TO FIRST ELEMENT IN ENUM

        // Click the view
        onView(withId(R.id.mdkRichEnumImage_withLabelAndError)).perform(scrollTo(), click());
        assertThat(null, ContextCompat.getDrawable(mActivityRule.getActivity(), R.drawable.enum_mobileos_android).getConstantState().equals(((ImageView) ((MDKRichEnumView) mActivityRule.getActivity().findViewById(R.id.mdkRichEnumImage_withLabelAndError)).getModeView()).getDrawable().getConstantState()));

    }

    /**
     * Tests the editable property when no enum specified. Should not change the value on user click.
     */
    @Test
    public void editableNoEnumTest() {
        assertThat(mActivityRule.getActivity(), is(notNullValue()));
        onView(withId(R.id.mdkRichEnumText_withLabelAndError)).perform(scrollTo());
        assertThat(null, mActivityRule.getActivity().getString(R.string.hello_world).equals(((TextView) ((MDKRichEnumView) mActivityRule.getActivity().findViewById(R.id.mdkRichEnumText_withLabelAndError)).getModeView()).getText().toString()));

        //SHOULD BE THE SAME VALUE AFTER CLICK

        // Click the view
        onView(withId(R.id.mdkRichEnumText_withLabelAndError)).perform(scrollTo(), click());
        assertThat(null, mActivityRule.getActivity().getString(R.string.hello_world).equals(((TextView) ((MDKRichEnumView) mActivityRule.getActivity().findViewById(R.id.mdkRichEnumText_withLabelAndError)).getModeView()).getText().toString()));

    }

    /**
     * Tests the custom validator of an EnumView.
     */
    @Test
    public void enumValidatorTest() {

        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        //SHOULD NOT VALIDATE AFTER CLICK

        // Click the view
        onView(withId(R.id.mdkRichEnumImage_withLabelAndError)).perform(scrollTo(), click());
        onView(allOf(withId(R.id.component_error), isDescendantOfA(withId(R.id.mdkRichEnumImage_withLabelAndError))))
                .check(matches(withConcatText(R.string.test_fortyTwoTextFormater_prefix, R.string.wrong_os)));
    }


}
