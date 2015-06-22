package com.soprasteria.movalysmdk.widget.sample;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.soprasteria.movalysmdk.widget.core.exception.MDKWidgetException;
import com.soprasteria.movalysmdk.widget.sample.content.WidgetContent;

/**
 * Custom Application class.
 */
public class MyApp extends Application {

    /**
     * Contains the list of activities having mdk widgets.
     */
    private WidgetContent widgetContent ;

    @Override
    public void onCreate() {
        super.onCreate();

        findWidgetActivities();
    }

    /**
     * Compute list of activities having mdk widgets.
     */
    private void findWidgetActivities() {
        try {
            PackageManager pm =  this.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);

            this.widgetContent = new WidgetContent();
            for (ActivityInfo ai: pi.activities) {

                // load all class but main activity
                if (!ai.name.equals(ListWidgetActivity.class.getName())) {
                    widgetContent.getItems().add(
                            new WidgetContent.WidgetItem(ai.loadLabel(pm).toString(), (Class<? extends Activity>) Class.forName(ai.name)));
                }

            }
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException exception) {
            throw new MDKWidgetException("context", exception);
        }
    }

    /**
     * Get widgetContent.
     * @return widgetContent
     */
    public WidgetContent getWidgetContent() {
        return widgetContent;
    }
}
