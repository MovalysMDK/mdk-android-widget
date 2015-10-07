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

import com.soprasteria.movalysmdk.widget.basic.MDKEnumImage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for MDKEnumImage widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EnumImageTest {

    /**
     * Activity used for these tests.
     */
    @Rule
    public ActivityTestRule<EnumImageActivity> mActivityRule = new ActivityTestRule<>(EnumImageActivity.class);

    /**
     * Tests that the drawable has been correctly set after a setValueFromEnum operation.
     */
    @Test
    public void imageFromEnumTest(){
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        MDKEnumImage enumimage = (MDKEnumImage) mActivityRule.getActivity().findViewById(R.id.mdkEnumImage_withErrorAndCommandOutside);
        assertThat(null, enumimage.getDrawable().getConstantState().equals(ContextCompat.getDrawable(mActivityRule.getActivity(),R.drawable.enum_babyanimals_kitten).getConstantState()));
    }
}
