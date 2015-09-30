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
package com.soprasteria.movalysmdk.widget.core.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.StringRes;

import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;

/**
 * Helper for the application permission introduced with Android M.
 */
public class ApplicationPermissionHelper {

    /**
     * Constructor.
     */
    private ApplicationPermissionHelper() {
        // nothing to do
    }

    /**
     * Used to check that the application has the given permission.
     * @param context android context
     * @param delegate widget delegate
     * @param errorMessage error message identifier
     */
    public static void checkPermission(Context context, MDKWidgetDelegate delegate, @StringRes int errorMessage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && delegate != null) {
            delegate.setError(context.getString(errorMessage));
        }
    }

}
