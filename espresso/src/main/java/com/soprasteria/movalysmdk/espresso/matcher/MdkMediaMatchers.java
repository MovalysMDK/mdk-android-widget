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
package com.soprasteria.movalysmdk.espresso.matcher;

import android.net.Uri;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;

import com.soprasteria.movalysmdk.widget.media.MDKMedia;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Espresso matchers for checking media.
 */
public class MdkMediaMatchers {

    /**
     * Constructor.
     */
    private MdkMediaMatchers() {
        // private because helper class.
    }

    /**
     * Creates a matcher to check the value of a MDKMedia component.
     * @param expectedUri the uri of the media associated to the widget.
     * @return matcher.
     */
    public static Matcher<View> mdkMediaWithUri(final Uri expectedUri) {
        return new BoundedMatcher<View, MDKMedia>(MDKMedia.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText(expectedUri.getPath());
            }

            @Override
            public boolean matchesSafely(MDKMedia media) {
                return media.getMediaUri().equals(expectedUri);
            }
        };
    }

    /**
     * Creates a matcher to check that a MDKMedia has a uri..
     * @return matcher.
     */
    public static Matcher<View> mdkMediaHasUri() {
        return new BoundedMatcher<View, MDKMedia>(MDKMedia.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("not null");
            }

            @Override
            public boolean matchesSafely(MDKMedia media) {
                return media.getMediaUri()!=null;
            }
        };
    }

    /**
     * Creates a matcher to check the type of media of a MDKMedia widget.
     * @param expectedType the type (@MediaType) of media.
     * @return matcher.
     */
    public static Matcher<View> mdkMediaWithType(final int expectedType) {
        return new BoundedMatcher<View, MDKMedia>(MDKMedia.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText(String.valueOf(expectedType));
            }

            @Override
            public boolean matchesSafely(MDKMedia media) {
                return media.getMediaType() == expectedType;
            }
        };
    }

    /**
     * Creates a matcher to check the placeholder resource of a MDKMedia widget.
     * @param expectedPlaceholderRes the placeholder.
     * @return matcher.
     */
    public static Matcher<View> mdkMediaWithPlaceHolderRes(final int expectedPlaceholderRes) {
        return new BoundedMatcher<View, MDKMedia>(MDKMedia.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText(String.valueOf(expectedPlaceholderRes));
            }

            @Override
            public boolean matchesSafely(MDKMedia media) {
                return media.getPlaceholder() == expectedPlaceholderRes;
            }
        };
    }
}
