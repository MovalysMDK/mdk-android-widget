package com.soprasteria.movalysmdk.widget.position.model;

import android.location.Address;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;

import com.soprasteria.movalysmdk.widget.position.helper.PositionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Position class.
 * Represents a position object as processed by the MDKPosition widget
 */
public class Position extends View.BaseSavedState {

    /** WGS84 Latitude. */
    private Double latitude;

    /** WGS84 Longitude. */
    private Double longitude;

    /** list of addresses read from the location. */
    private List<Address> addresses;

    /** user selected address in the list. */
    private int selectedAddress;

    /**
     * Position public constructor.
     * @param superState the super state
     */
    public Position(Parcelable superState) {
        super(superState);
    }

    /**
     * Position private constructor.
     * @param in the super state
     */
    private Position(Parcel in) {
        super(in);

        this.latitude = in.readDouble();
        this.longitude = in.readDouble();

        Parcelable[] inAddresses = in.readParcelableArray((ClassLoader) Address.CREATOR);

        this.addresses = new ArrayList<>();

        for (Parcelable address : inAddresses) {
            this.addresses.add((Address) address);
        }
    }

    /**
     * Sets the position from the given location.
     * @param location the location to set the position from
     */
    public void setPositionFromLocation(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    /**
     * Returns the object's latitude.
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Sets the object's latitude.
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Returns the object's longitude.
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Sets the object's longitude.
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Location getLocation() {
        Location location = new Location("dummyprovider");

        if (this.latitude != null) {
            location.setLatitude(this.latitude);
        }
        if (this.longitude != null) {
            location.setLongitude(this.longitude);
        }

        return location;
    }

    /**
     * Returns true if the current location is equal to the one sent as parameter.
     * @param location the location to test
     * @return true if the location equals the current one
     */
    public boolean equals(Location location) {
        boolean isLatitudeEqual;
        if (this.latitude == null) {
            isLatitudeEqual = false;
        } else {
            isLatitudeEqual = PositionHelper.round(location.getLatitude(), 4) == PositionHelper.round(this.latitude, 4);
        }
        boolean isLongitudeEqual;
        if (this.longitude == null) {
            isLongitudeEqual = false;
        } else {
            isLongitudeEqual = PositionHelper.round(location.getLongitude(), 4) == PositionHelper.round(this.longitude, 4);
        }

        return isLatitudeEqual && isLongitudeEqual;
    }

    /**
     * Returns the list of addresses stored in the object.
     * @return the list of addresses
     */
    public List<Address> getAddresses() {
        return this.addresses;
    }

    /**
     * Sets the addresses found for the location.
     * @param addresses the addresses list
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     * Sets the index of the selected address.
     * @param selection the index of the selected address
     */
    public void setSelectedAddress(int selection) {
        this.selectedAddress = selection;
    }

    public boolean hasAddresses() {
        if (this.addresses == null) {
            return false;
        } else {
            return this.addresses.size() > 0;
        }
    }

    public String getStringAddress() {
        String address = null;

        if (this.addresses != null && this.addresses.size() > 0 && this.selectedAddress <= this.addresses.size()) {
            Address currentAddress = this.addresses.get(this.selectedAddress);
            if (currentAddress.getAddressLine(0) != null) {
                address = currentAddress.getAddressLine(0);
            }
            if (currentAddress.getLocality() != null) {
                if (address.length() > 0) {
                    address += " ";
                }
                address += currentAddress.getLocality();
            }
        }

        return address;
    }

    /**
     * Returns the index of the selected address.
     * @return the index of the selected address
     */
    public int getSelectedAddress() {
        return this.selectedAddress;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        super.writeToParcel(out, flags);

        out.writeDouble(this.latitude == null ? 0 : this.latitude);
        out.writeDouble(this.longitude == null ? 0 : this.longitude);

        out.writeParcelableArray(this.addresses != null ? (Parcelable[]) this.addresses.toArray() : null, 0);
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
