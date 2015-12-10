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

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;

/**
 * Helper class for activities related actions.
 * Use this to launch a startActivityForResult action.
 */
public class ActivityHelper {

    /**
     * private constructor.
     */
    private ActivityHelper() {
        // nothing to do.
    }

    /**
     * Start an activity for result from a given context.
     * @param ctx android context
     * @param intent intent of the activity to launch
     * @param requestCode request code to return
     */
    public static void startActivityForResult(Context ctx, Intent intent, int requestCode) {
        Activity activity = findActivityFromContext(ctx);

        if (activity != null) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            Log.e(ActivityHelper.class.getSimpleName(), "Could not get activity from context");
        }
    }

    /**
     * Tries to get the activity from a context.
     * Helper for finding an activity from a dialog context
     * @param ctx android context
     * @return an Activity object, or null if none was found
     */
    public static Activity findActivityFromContext(Context ctx) {
        if (ctx == null) {
            return null;
        } else if (ctx instanceof Activity) {
            return (Activity) ctx;
        } else if (ctx instanceof ContextWrapper) {
            return findActivityFromContext(((ContextWrapper) ctx).getBaseContext());
        }

        return null;
    }

}
