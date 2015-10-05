package com.soprasteria.movalysmdk.widget.position.helper;

import android.location.Location;

import com.soprasteria.movalysmdk.widget.core.behavior.model.Position;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Helper for the MDKPosition widget class.
 */
public class PositionHelper {

    /** double formatter. */
    private static NumberFormat formatter = new DecimalFormat("#0.0000000");

    /**
     * Constructor.
     */
    private PositionHelper()  {
        // nothing to do
    }

    /**
     * Rounds a double value with the given number of decimals.
     * @param value the value to round
     * @param decimalNumber the number of decimal to keep
     * @return the rounded double value
     */
    public static double round(double value, int decimalNumber) {
        if (decimalNumber < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalNumber, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



    /**
     * Returns latitude as a formatted string.
     * @param position the position to get a formatted latitude from
     * @return the formatted latitude
     */
    public static String getFormattedLatitude(Position position) {
        // There is a bug in Android, the input decimal is always a point no matter the comma : bug 2626
        // We replace the comma by the point as a work around
        String latitude = "";

        if (position != null && position.getLatitude() != null) {
            latitude = formatter.format(position.getLatitude());
            latitude = latitude.replaceAll(",", ".");
        }

        return latitude;
    }

    /**
     * Returns longitude as a formatted string.
     * @param position the position to get a formatted longitude from
     * @return the formatted longitude
     */
    public static String getFormattedLongitude(Position position) {
        // There is a bug in Android, the input decimal is always a point no matter the comma : bug 2626
        // We replace the comma by the point as a work around
        String longitude = null;

        if (position != null && position.getLongitude() != null) {
            longitude = formatter.format(position.getLongitude());
            longitude = longitude.replaceAll(",", ".");
        }

        return longitude;
    }

    /**
     * Returns a formatted string for location.
     * @param position the position to get a formatted location from
     * @return a formatted string for location
     */
    public static String getFormattedLocation(Position position) {
        if (!position.isNull()) {
            return position.getLatitude() + " / " + position.getLongitude();
        } else {
            return "";
        }
    }

    /**
     * Returns true if the given location is near to the one stored in the class.
     * This is used to avoid a calculation on addresses when the position is getting more accurate.
     * @param position the position to compare
     * @param location the location to compare
     * @return true if the location is near to the one stored in the instance of that class
     */
    public static boolean isNearTo(Position position, Location location) {
        if (position != null) {
            boolean isLatitudeEqual = PositionHelper.round(location.getLatitude(), 4) == PositionHelper.round(position.getLatitude(), 4);
            boolean isLongitudeEqual = PositionHelper.round(location.getLongitude(), 4) == PositionHelper.round(position.getLongitude(), 4);

            return isLatitudeEqual && isLongitudeEqual;
        } else {
            return false;
        }
    }

    /**
     * Returns the formatted selected address.
     * @param position the position to get a formatted address from
     * @return a string representing the selected address
     */
    public static String getFormattedAddress(Position position) {
        String textAddress = "";
        if (position.getAddress() != null) {
            if (position.getAddress().getAddressLine(0) != null) {
                textAddress = position.getAddress().getAddressLine(0);
            }
            if (position.getAddress().getLocality() != null) {
                if (textAddress.length() > 0) {
                    textAddress += " ";
                }
                textAddress += position.getAddress().getLocality();
            }
        }

        return textAddress;
    }

}
