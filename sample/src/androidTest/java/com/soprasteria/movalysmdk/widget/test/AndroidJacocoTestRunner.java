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
package com.soprasteria.movalysmdk.widget.test;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Runner for android test with jacoco.
 * <p>This runner adds a property at runtime to specify the target file for coverage report.</p>
 */
public class AndroidJacocoTestRunner extends AndroidJUnitRunner {

    static {
        System.setProperty("jacoco-agent.destfile", "/storage/sdcard/coverage.ec");
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        try {
            Class rt = Class.forName("org.jacoco.agent.rt.RT");
            Method getAgent = rt.getMethod("getAgent");
            Method dump = getAgent.getReturnType().getMethod("dump", boolean.class);
            Object agent = getAgent.invoke(null);
            dump.invoke(agent, false);
        } catch (ClassNotFoundException e) {
            Log.e(MDKWidgetApplication.LOG_TAG, "AndroidJacocoTestRunner failure", e);
        } catch (NoSuchMethodException e) {
            Log.e(MDKWidgetApplication.LOG_TAG, "AndroidJacocoTestRunner failure", e);
        } catch (InvocationTargetException e) {
            Log.e(MDKWidgetApplication.LOG_TAG, "AndroidJacocoTestRunner failure", e);
        } catch (IllegalAccessException e) {
            Log.e(MDKWidgetApplication.LOG_TAG, "AndroidJacocoTestRunner failure", e);
        }
        super.finish(resultCode, results);
    }
}
