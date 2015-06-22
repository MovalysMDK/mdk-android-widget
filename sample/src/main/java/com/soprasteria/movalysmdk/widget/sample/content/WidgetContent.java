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
