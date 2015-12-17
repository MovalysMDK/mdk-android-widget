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
     * @param permission the permission to check
     * @param errorMessage error message identifier
     */
    public static void checkCommandPermission(Context context, MDKWidgetDelegate delegate, String permission, @StringRes int errorMessage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED
                && delegate != null) {
            delegate.setError(context.getString(errorMessage));
        }
    }

    /**
     * Used to check that the application has the given permissions.
     * @param context android context
     * @param delegate widget delegate
     * @param permissions the list of permissions to check
     * @param errorMessage error message identifier
     * @return true if the list of given permissions are granted, false otherwise
     */
    public static boolean checkPermissions(Context context, MDKWidgetDelegate delegate, String[] permissions, @StringRes int errorMessage) {
        boolean hasPermissions = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && delegate != null) {
            for (String permission : permissions) {
                if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    delegate.setError(context.getString(errorMessage));
                    hasPermissions = false;
                }
            }
        }

        return hasPermissions;
    }

}
