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
