package com.soprasteria.movalysmdk.widget.position.model;

import android.location.Address;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.soprasteria.movalysmdk.widget.position.helper.PositionHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Position class.
 * Represents a position object as processed by the MDKPosition widget
 */
public class Position implements Parcelable {

    /** double formatter. */
    private NumberFormat formatter;

    /** location. */
    private Location location;

    /** address. */
    private Address address;

    /**
     * Position public constructor.
     */
    public Position() {
        // TODO : faire une factory
        location = null;
        address = new Address(Locale.getDefault());
        // TODO : externaliser le formatage
        formatter = new DecimalFormat("#0.0000000");
    }

    /**
     * Position private constructor.
     * @param in the super state
     */
    private Position(Parcel in) {
        this.location = in.readParcelable((ClassLoader) Location.CREATOR);
        this.address = in.readParcelable((ClassLoader) Address.CREATOR);
    }

    /**
     * Sets the position from the given location.
     * @param location the location to set the position from
     */
    public void setPositionFromLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns true if location is null.
     * @return true if location is null
     */
    public boolean isNull() {
        return this.location == null;
    }

    /**
     * Returns the object's latitude.
     * @return the latitude
     */
    public Double getLatitude() {
        return location.getLatitude();
    }

    /**
     * Returns latitude as a formatted string.
     * @return the formatted latitude
     */
    public String getFormattedLatitude() {
        // There is a bug in Android, the input decimal is always a point no matter the comma : bug 2626
        // We replace the comma by the point as a work around
        String latitude = "";

        if (location != null) {
            latitude = formatter.format(location.getLatitude());
            latitude = latitude.replaceAll(",", ".");
        }

        return latitude;
    }

    /**
     * Returns the object's longitude.
     * @return the longitude
     */
    public Double getLongitude() {
        return location.getLongitude();
    }

    /**
     * Returns longitude as a formatted string.
     * @return the formatted longitude
     */
    public String getFormattedLongitude() {
        // There is a bug in Android, the input decimal is always a point no matter the comma : bug 2626
        // We replace the comma by the point as a work around
        String longitude = null;

        if (location != null) {
            longitude = formatter.format(location.getLongitude());
            longitude = longitude.replaceAll(",", ".");
        }

        return longitude;
    }

    /**
     * Returns the location stored by the class.
     * @return the location stored by the class
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns a formatted string for location.
     * @return a formatted string for location
     */
    public String getFormattedLocation() {
        if (!isNull()) {
            return this.location.getLatitude() + " / " + this.location.getLongitude();
        } else {
            return "";
        }
    }

    /**
     * Returns true if the given location is near to the one stored in the class.
     * This is used to avoid a calculation on addresses when the position is getting more accurate.
     * @param location the location to compare
     * @return true if the location is near to the one stored in the instance of that class
     */
    public boolean isNearTo(Location location) {
        if (this.location != null) {
            boolean isLatitudeEqual = PositionHelper.round(location.getLatitude(), 4) == PositionHelper.round(this.location.getLatitude(), 4);
            boolean isLongitudeEqual = PositionHelper.round(location.getLongitude(), 4) == PositionHelper.round(this.location.getLongitude(), 4);

            return isLatitudeEqual && isLongitudeEqual;
        } else {
            return false;
        }
    }

    /**
     * Returns the formatted selected address.
     * @return a string representing the selected address
     */
    public String getFormattedAddress() {
        String address = "";
        if (this.address != null) {
            if (this.address.getAddressLine(0) != null) {
                address = this.address.getAddressLine(0);
            }
            if (this.address.getLocality() != null) {
                if (address.length() > 0) {
                    address += " ";
                }
                address += this.address.getLocality();
            }
        }

        return address;
    }

    /**
     * Returns the selected address.
     * @return the selected address
     */
    public Address getAddress() {
        return this.address;
    }

    /**
     * Sets the selected address.
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeParcelable(this.location, 0);
        out.writeParcelable(this.address, 0);
    }

    /**
     * Required field that makes Parcelables from a Parcel.
     */
    public static final Creator<Position> CREATOR =
        new Creator<Position>() {

            @Override
            public Position createFromParcel(Parcel in) {
                return new Position(in);
            }

            @Override
            public Position[] newArray(int size) {
                return new Position[size];
            }
        };
}
