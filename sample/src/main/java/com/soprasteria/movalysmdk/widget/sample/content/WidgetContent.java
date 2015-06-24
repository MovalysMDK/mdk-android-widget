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
package com.soprasteria.movalysmdk.widget.sample.content;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces.
 */
public class WidgetContent {

    /**
     * An array of sample (dummy) items.
     */
    private List<WidgetItem> items = new ArrayList<>();

    /**
     * Get item list.
     * @return item list
     */
    public List<WidgetItem> getItems() {
        return items;
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class WidgetItem {

        /**
         * Content.
         */
        private String content;

        /**
         * Activity class.
         */
        protected Class<? extends Activity> activityClass;

        /**
         * Constructor.
         * @param content the content
         * @param activityToLaunch activity to launch
         */
        public WidgetItem(String content, Class activityToLaunch) {
            this.content = content;
            this.activityClass = activityToLaunch;
        }

        @Override
        public String toString() {
            return this.content;
        }

        /**
         * Get content.
         * @return content the content
         */
        public String getContent() {
            return content;
        }

        /**
         * Set content.
         * @param content the context
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         * Getter.
         * @return activityClass the activity class
         */
        public Class getActivityClass() {
            return activityClass;
        }
    }
}
