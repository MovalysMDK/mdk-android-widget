package com.soprasteria.movalysmdk.widget.test.espresso.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichDateWidget;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Calendar;
import java.util.Date;

/**
 * Add view matchers on date for espresso tests.
 */
public class MdkDateMatchers {

    /**
     * Constructor.
     */
    private MdkDateMatchers() {
        // empty constructor
    }

    /**
     * Create a matcher to check a date.
     * @param year year.
     * @param month month.
     * @param day day.
     * @return matcher.
     */
    public static Matcher<View> withDate( final int year, final int month, final int day) {
        return matchDateTime(year, month, day, 0, 0);
    }

    /**
     * Create a matcher to check a date with time.
     * @param year year.
     * @param month month.
     * @param day day.
     * @param hour hour.
     * @param minute minute.
     * @return matcher.
     */
    public static Matcher<View> withDateTime( final int year, final int month, final int day, final int hour, final int minute) {
        return matchDateTime(year, month, day, hour, minute);
    }

    /**
     * Create a matcher to check a date.
     * @param year year.
     * @param month month.
     * @param day day of month.
     * @param hour hour.
     * @param minute minute.
     * @return matcher.
     */
    private static Matcher<View> matchDateTime( final int year, final int month, final int day, final int hour, final int minute ) {
        return new BoundedMatcher(MDKBaseRichDateWidget.class) {

            /**
             * Expected date.
             */
            private Calendar expectedDate ;

            @Override
            public void describeTo(Description description) {
                description.appendText("with date: ");
                description.appendText(Integer.toString(year));
                description.appendText("/");
                description.appendText(Integer.toString(month));
                description.appendText("/");
                description.appendText(Integer.toString(day));
                description.appendText(" ");
                description.appendText(Integer.toString(hour));
                description.appendText(":");
                description.appendText(Integer.toString(minute));

                if(null != this.expectedDate) {
                    description.appendText(" value: ");
                    description.appendText(this.expectedDate.toString());
                }

            }

            @Override
            public boolean matchesSafely(Object object) {
                MDKBaseRichDateWidget dateView = (MDKBaseRichDateWidget) object;
                if(null == this.expectedDate) {
                    this.expectedDate = Calendar.getInstance();
                    expectedDate.set(Calendar.YEAR, year);
                    expectedDate.set(Calendar.MONTH, month-1);
                    expectedDate.set(Calendar.DAY_OF_MONTH, day);
                    expectedDate.set(Calendar.HOUR_OF_DAY, hour);
                    expectedDate.set(Calendar.MINUTE, minute);
                    expectedDate.set(Calendar.SECOND, 0);
                    expectedDate.set(Calendar.MILLISECOND, 0);
                }

                Date actualDate = dateView.getDate();

                return null != this.expectedDate && null != actualDate?this.expectedDate.getTime().equals(actualDate):false;
            }
        };
    }
}
