package com.soprasteria.movalysmdk.widget.sample.content;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 */
public class WidgetContent {
    /**
     * An array of sample (dummy) items.
     */
    private static List<WidgetItem> ITEMS = new ArrayList<WidgetItem>();

    private static void addItem(WidgetItem item) {
        ITEMS.add(item);
    }

    public static List<WidgetItem> getITEMS() {
        return ITEMS;
    }

    public static void setITEMS(List<WidgetItem> pItems) {
        WidgetContent.ITEMS = pItems;
    }

    /**
     * private constructor.
     */
    private WidgetContent() {

    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class WidgetItem {
        private String content;

        protected Class<? extends Activity> activityClass;

        /**
         * Constructor.
         * @param content the content
         * @param activityToLaunch the activity to launch
         */
        public WidgetItem(String content, Class<? extends Activity> activityToLaunch) {
            this.content = content;
            this.activityClass = activityToLaunch;
        }

        @Override
        public String toString() {
            return content;
        }

        /**
         * Getter.
         * @return content the content
         */
        public String getContent() {
            return content;
        }

        /**
         * Setter.
         * @param content the context
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         * Getter.
         * @return activityClass the activity class
         */
        public Class<? extends Activity> getActivityClass() {
            return activityClass;
        }
    }
}
