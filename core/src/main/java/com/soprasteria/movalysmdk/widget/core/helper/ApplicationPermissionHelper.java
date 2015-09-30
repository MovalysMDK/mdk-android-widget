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
