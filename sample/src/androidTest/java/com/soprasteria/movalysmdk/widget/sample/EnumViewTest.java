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
import com.soprasteria.movalysmdk.widget.sample.enums.BabyAnimals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;

import static org.hamcrest.MatcherAssert.assertThat;
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
        assertThat(null, ((ImageView) enumimage.getModeView()).getDrawable().getConstantState().equals(ContextCompat.getDrawable(mActivityRule.getActivity(), R.drawable.enum_babyanimals_kitten).getConstantState()));
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
        enumimage.setValueFromEnum(BabyAnimals.CUB);
        assertThat(null, ContextCompat.getDrawable(mActivityRule.getActivity(), R.drawable.enum_babyanimals_cub).getConstantState().equals(((ImageView) enumimage.getModeView()).getDrawable().getConstantState()));

        //set drawable from string
        enumimage.setValueFromString("babyanimals_puppy");
        assertThat(null, ContextCompat.getDrawable(mActivityRule.getActivity(), R.drawable.enum_babyanimals_puppy).getConstantState().equals(((ImageView) enumimage.getModeView()).getDrawable().getConstantState()));

        //fallback to string
        enumimage.setValueFromEnum(BabyAnimals.CALF);
        assertThat(null, mActivityRule.getActivity().getString(R.string.enum_babyanimals_calf).equals(((TextView) enumimage.getModeView()).getText().toString()));

        enumimage.setValueFromString("babyanimals_calf");
        assertThat(null, mActivityRule.getActivity().getString(R.string.enum_babyanimals_calf).equals(((TextView) enumimage.getModeView()).getText().toString()));

        //fallback to name
        enumimage.setValueFromEnum(BabyAnimals.PIGLET);
        assertThat(null, "enum_babyanimals_piglet".equals(((TextView) enumimage.getModeView()).getText().toString()));

        enumimage.setValueFromString("hello_world");
        assertThat(null, "enum_hello_world".equals(((TextView) enumimage.getModeView()).getText().toString()));

    }
}
