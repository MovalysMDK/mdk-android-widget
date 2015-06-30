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
package com.soprasteria.movalysmdk.widget.test.espresso.matchers;

import android.content.res.Resources;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.sample.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Add view matchers for espresso tests.
 */
public class MdkViewMatchers {

    /**
     * Create a matcher checking a text is equals to the concat text computed from string resource ids.
     * @param resourceIds list of resource id.
     * @return matcher.
     */
    public static Matcher<View> withConcatText(int... resourceIds) {
        return withCharSequence(resourceIds);
    }

    /**
     * Create a matcher to check the label in the text of a view.
     * <p>If the view is mandatory, the mandatory string (*) is added to the string comparison.</p>
     * @param labelId label id.
     * @param mandatory mandatory field.
     * @return matcher.
     */
    public static Matcher<View> withTextLabel(@StringRes final int labelId, final boolean mandatory) {
        return withLabel(labelId, mandatory, LABEL_TEXT);
    }

    /**
     * Create a matcher to check the label in the hint of a view.
     * <p>If the view is mandatory, the mandatory string (*) is added to the string comparison.</p>
     * @param labelId label id.
     * @param mandatory mandatory field.
     * @return matcher.
     */
    public static Matcher<View> withHintLabel(@StringRes final int labelId, final boolean mandatory) {
        return withLabel(labelId, mandatory, LABEL_HINT);
    }

    /**
     * Create a matcher checking a text is equals to the concat text computed from string resource ids.
     * @param resourceIds  list of resource id.
     * @return matcher.
     */
    private static Matcher<View> withCharSequence(final int... resourceIds) {
        return new BoundedMatcher(TextView.class) {

            /**
             * Resource names.
             */
            private String resourceNames ;

            /**
             * Concat text.
             */
            private String expectedText ;

            @Override
            public void describeTo(Description description) {
                description.appendText("with string from resource ids: ");
                for( int resId : resourceIds) {
                    description.appendText(Integer.toString(resId));
                    description.appendText(" ");
                }
                if(null != this.resourceNames) {
                    description.appendText("[");
                    description.appendText(this.resourceNames);
                    description.appendText("]");
                }

                if(null != this.expectedText) {
                    description.appendText(" value: ");
                    description.appendText(this.expectedText);
                }

            }

            @Override
            public boolean matchesSafely(Object object) {
                TextView textView = (TextView) object;
                if(null == this.expectedText) {
                    try {
                        StringBuilder text = new StringBuilder();
                        StringBuilder resName = new StringBuilder();
                        for( int resId : resourceIds) {
                            text.append(textView.getResources().getString(resId));
                            resName.append(textView.getResources().getResourceEntryName(resId));
                        }

                        this.expectedText = text.toString();
                        this.resourceNames = resName.toString();
                    } catch (Resources.NotFoundException e) {
                        Log.e(MDKWidgetApplication.LOG_TAG, "MdkViewMatchers.withCharSequence failure", e);
                    }
                }

                CharSequence actualText = textView.getText();

                return null != this.expectedText && null != actualText?this.expectedText.equals(actualText.toString()):false;
            }
        };
    }

    /**
     * Create a matcher to check the label.
     * <p>If the view is mandatory, the mandatory string (*) is added to the string comparison.</p>
     * @param labelId label id.
     * @param mandatory mandatory field.
     * @param labelLocation location of label (in text or hint)
     * @return matcher.
     */
    private static Matcher<View> withLabel(@StringRes final int labelId, final boolean mandatory, @LabelLocation final int labelLocation) {
        return new BoundedMatcher(TextView.class) {

            /**
             * Resource names.
             */
            private String resourceName ;

            /**
             * Concat text.
             */
            private String expectedText ;

            @Override
            public void describeTo(Description description) {
                description.appendText("with string from label id: ");
                description.appendText(Integer.toString(labelId));

                if(null != this.resourceName) {
                    description.appendText("[");
                    description.appendText(this.resourceName);
                    description.appendText(", mandatory:");
                    description.appendText(Boolean.toString(mandatory));
                    description.appendText("]");
                }

                if(null != this.expectedText) {
                    description.appendText(" value: ");
                    description.appendText(this.expectedText);
                }

            }

            @Override
            public boolean matchesSafely(Object object) {
                TextView textView = (TextView) object;
                if(null == this.expectedText) {
                    try {
                        StringBuilder text = new StringBuilder(textView.getResources().getString(labelId));
                        if ( mandatory ) {
                            text.append(textView.getResources().getString(R.string.mandatory_char));
                        }

                        this.expectedText = text.toString();
                        this.resourceName = textView.getResources().getResourceEntryName(labelId);
                    } catch (Resources.NotFoundException e) {
                        Log.e(MDKWidgetApplication.LOG_TAG, "MdkViewMatchers.withCharSequence failure", e);
                    }
                }

                CharSequence actualText ;
                if ( labelLocation == LABEL_TEXT ) {
                    actualText = textView.getText();
                } else {
                    actualText = textView.getHint();
                }

                return null != this.expectedText && null != actualText?this.expectedText.equals(actualText.toString()):false;
            }
        };
    }

    /**
     * Define the list of possible label locations
     */
    @IntDef({LABEL_TEXT, LABEL_HINT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LabelLocation {}

    /**
     * Label is located in the text of the view.
     */
    public static final int LABEL_TEXT = 0;

    /**
     * Label is located in the hint of the view.
     */
    public static final int LABEL_HINT = 1;
}
