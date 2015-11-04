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
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentActionHelper;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentProvider;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetSimpleComponentActionHelper;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetSimpleComponentProvider;
import com.soprasteria.movalysmdk.widget.sample.content.WidgetContent;
import com.soprasteria.movalysmdk.widget.sample.selector.BoldMandatorySelector;
import com.soprasteria.movalysmdk.widget.sample.validator.MobileOSAndroidValidator;
import com.soprasteria.movalysmdk.widget.sample.validator.NoNumberValidator;

/**
 * Custom Application class.
 */
public class MyApp extends Application implements MDKWidgetApplication {

    /**
     * Contains the list of activities having mdk widgets.
     */
    private WidgetContent widgetContent ;
    /**
     * Instance of the MDKWidgetComponentProvider.
     */
    private MDKWidgetComponentProvider widgetComponentProvider;
    /**
     * Instance of the MDKWidgetComponentActionHelper.
     */
    private MDKWidgetComponentActionHelper widgetComponentActionHelper;

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

                // load all class with parent class ListWidgetActivity
                if (ai.parentActivityName != null && ai.parentActivityName.equals(ListWidgetActivity.class.getName())) {
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

    @Override
    public MDKWidgetComponentProvider getMDKWidgetComponentProvider() {
        if (this.widgetComponentProvider == null) {
            this.widgetComponentProvider = new MDKWidgetSimpleComponentProvider(this);
            this.widgetComponentProvider.registerValidator(this, new NoNumberValidator());
            this.widgetComponentProvider.registerValidator(this, new MobileOSAndroidValidator());

            this.widgetComponentProvider.registerRichSelector("custom_mandatory_bold_selector", new BoldMandatorySelector());
        }

        return this.widgetComponentProvider;
    }

    @Override
    public MDKWidgetComponentActionHelper getMDKWidgetComponentActionHelper() {
        if (this.widgetComponentActionHelper == null) {
            this.widgetComponentActionHelper = new MDKWidgetSimpleComponentActionHelper();
        }
        return this.widgetComponentActionHelper;
    }
}
