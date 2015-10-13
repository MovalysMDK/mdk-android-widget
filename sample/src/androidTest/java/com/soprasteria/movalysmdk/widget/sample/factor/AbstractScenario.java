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
package com.soprasteria.movalysmdk.widget.sample.factor;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;

import org.junit.Rule;

/**
 * Abstract scenario class.
 * @param <T> class of the activity to test
 */
public abstract class AbstractScenario<T extends AppCompatActivity> {

    /**
     * Rule to initialize EditTextActivity.
     */
    @Rule
    public ActivityTestRule<T> mActivityRule;

    /**
     * Constructor.
     * @param mActivityRule the linked ActivityTestRule
     */
    public AbstractScenario(ActivityTestRule<T> mActivityRule) {
        this.mActivityRule = mActivityRule;
    }

    /**
     * Abstract method.
     * This class return the test activity.
     * @return the ActivityTestRule to test
     */
    protected ActivityTestRule getActivity() {
        return this.mActivityRule;
    }

}
