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
 * Abstract implementation for mdk widget.
 */
public abstract class AbstractCommandWidgetTest<T extends AppCompatActivity> {

    /**
     * Rule to initialize EditTextActivity.
     */
    @Rule
    public ActivityTestRule<T> activityRule;

    /** entry scenario object. */
    private EntryScenario<T> entryScenario;

    /** enable scenario object. */
    private EnabledScenario<T> enabledScenario;

    /** mandatory scenario object. */
    private MandatoryScenario<T> mandatoryScenario;

    /**
     * Constructor.
     * @param activityClass the class of the activity to test
     */
    public AbstractCommandWidgetTest(Class<T> activityClass) {
        activityRule = new ActivityTestRule<>(activityClass);

        this.entryScenario = new EntryScenario<T>(activityRule);
        this.enabledScenario = new EnabledScenario<T>(activityRule);
        this.mandatoryScenario = new MandatoryScenario<T>(activityRule);
    }

    /**
     * Returns the ActivityTestRule object.
     * @return the ActivityTestRule object
     */
    public ActivityTestRule<T> getActivityRule() {
        return activityRule;
    }

    /**
     * Returns the entry scenario object.
     * @return the entry scenario object
     */
    public EntryScenario<T> getEntryScenario() {
        return entryScenario;
    }

    /**
     * Returns the enabled scenario object.
     * @return the enabled scenario object
     */
    public EnabledScenario<T> getEnabledScenario() {
        return enabledScenario;
    }

    /**
     * Returns the mandatory scenario object.
     * @return the mandatory scenario object
     */
    public MandatoryScenario<T> getMandatoryScenario() {
        return mandatoryScenario;
    }

}
