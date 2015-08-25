package com.soprasteria.movalysmdk.widget.position.command;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.position.R;

/**
 * Secondary command class for the position widget.
 * Will launch a map application displaying the location of the widget.
 */
public class MapWidgetCommand implements WidgetCommand<String, Void> {

    /** the default zoom level. */
    private static final String ZOOMLEVEL = "17";

    @Override
    public Void execute(Context context, String... locations) {
        if (locations.length != 2 || !validParameters(locations)) {
            throw new IllegalArgumentException("position command should have two long as strings parameters.");
        }

        openMap(context, getAccurateURI(locations));

        return null;
    }

    /**
     * Checks that the parameters of the command are valid, ie there should be two strings representing two double values.
     * @param locations the parameters to check
     * @return true if the parameters are valid
     */
    private boolean validParameters(String[] locations) {
        boolean valid;

        valid = locations.length == 2;

        if (valid) {
            try {
                Double.parseDouble(locations[0]);
                Double.parseDouble(locations[1]);
            } catch (NumberFormatException e) {
                valid = false;
            }
        }

        return valid;
    }

    /**
     * get accurate URI.
     * @param locations address location
     * @return URI string
     */
    private String getAccurateURI(String[] locations){
        boolean latitudeOk = locations[0] != null && locations[0].length() > 0;
        boolean longitudeOk = locations[1] != null && locations[1].length() > 0;

        if (latitudeOk && longitudeOk) {
            return "geo:" + locations[0] + "," + locations[1] + "?z=" + ZOOMLEVEL;
        }
        return null;
    }

    /**
     * open the map.
     * @param context the android context
     * @param uri map URI
     */
    private void openMap(Context context, String uri){
        try {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.alert_map_missing), Toast.LENGTH_SHORT).show();
        }
    }
}
