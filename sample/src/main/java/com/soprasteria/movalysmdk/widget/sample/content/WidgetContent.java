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
    public static List<WidgetItem> ITEMS = new ArrayList<WidgetItem>();

    private static void addItem(WidgetItem item) {
        ITEMS.add(item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class WidgetItem {
        public String content;
        public Class<? extends Activity> activityClass;

        public WidgetItem(String content, Class<? extends Activity> activityToLaunch) {
            this.content = content;
            this.activityClass = activityToLaunch;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
