package com.soprasteria.movalysmdk.widget.position.command;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.net.Uri;

import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.position.R;

import java.util.List;

/**
 * Tertiary command class for the position widget.
 * Will launch a navigation application.
 */
public class NavigationWidgetCommand implements WidgetCommand<Location, Integer> {

    @Override
    public Integer execute(Context context, Location location) {
        return openDirections(context, getAccurateURI(location));
    }

    @Override
    public void cancel() {
        // nothing to do
    }

    /**
     * get accurate URI.
     * @param locations address location
     * @return URI string
     */
    private String getAccurateURI(Location locations){
        return "http://maps.google.com/maps?daddr=" + locations.getLatitude() + "," + locations.getLongitude();
    }

    /**
     * open the direction application.
     * @param context the android context
     * @param uri map URI
     */
    private int openDirections(Context context, String uri){
        int res = 0;

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        PackageManager manager = context.getPackageManager();
        List<ResolveInfo> info = manager.queryIntentActivities(mapIntent, 0);

        if (!info.isEmpty()) {
            context.startActivity(mapIntent);
        } else {
            res = R.string.mdkcommand_map_error_map_missing;
        }

        return res;
    }
}
