package com.soprasteria.movalysmdk.widget.test.matchers;

import android.content.res.Resources;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

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
}
