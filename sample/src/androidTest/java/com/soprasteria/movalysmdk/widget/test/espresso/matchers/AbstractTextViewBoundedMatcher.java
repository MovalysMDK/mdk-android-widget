package com.soprasteria.movalysmdk.widget.test.espresso.matchers;

import android.content.res.Resources;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.util.Log;
import android.widget.TextView;

import org.hamcrest.Description;

/**
 * Abstract implementation for TextView bounded matcher.
 */
public abstract class AbstractTextViewBoundedMatcher extends BoundedMatcher {

    /**
     * Log tag.
     */
    private static final String LOG_TAG = "AbsBoundedMatcher";
    /**
     * Resource names.
     */
    private String resourceNames ;

    /**
     * Concat text.
     */
    private String expectedText ;

    /**
     * resource id to create.
     */
    private int[] resourceIds = null;

    /**
     * Create matcher
     * @param resourceIds
     */
    public AbstractTextViewBoundedMatcher(int... resourceIds) {
        super(TextView.class);
        this.resourceIds = resourceIds.clone();
    }


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
                Log.e(LOG_TAG, "MdkViewMatchers.withConcatHint failure", e);
            }
        }

        CharSequence actualText = this.getText(textView);

        return null != this.expectedText && null != actualText?this.expectedText.equals(actualText.toString()):false;
    }

    protected abstract CharSequence getText(TextView textView);
}
