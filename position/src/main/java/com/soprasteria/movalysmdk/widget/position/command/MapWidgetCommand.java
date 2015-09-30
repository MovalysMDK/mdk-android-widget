package com.soprasteria.movalysmdk.widget.position.command;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.net.Uri;
import android.widget.Toast;

import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessage;
import com.soprasteria.movalysmdk.widget.position.R;

import java.util.List;

/**
 * Secondary command class for the position widget.
 * Will launch a map application displaying the location of the widget.
 */
public class MapWidgetCommand implements WidgetCommand<Location, Integer> {

    /** the default zoom level. */
    private static final String ZOOMLEVEL = "17";

    @Override
    public Integer execute(Context context, Location location) {
        return openMap(context, getAccurateURI(location));
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
        return "geo:" + locations.getLatitude() + "," + locations.getLongitude() + "?z=" + ZOOMLEVEL;
    }

    /**
     * open the map.
     * @param context the android context
     * @param uri map URI
     * @return returns the identifier of the error if one gets raised
     */
    private int openMap(Context context, String uri){
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
