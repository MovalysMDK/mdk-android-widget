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
public class Position implements Parcelable {

    /** WGS84 Latitude. */
    private Double latitude;

    /** WGS84 Longitude. */
    private Double longitude;

    /** list of addresses read from the location. */
    private List<Address> addresses;

    /** user selected address in the list. */
    private int selectedAddress = 0;

    /**
     * Position public constructor.
     */
    public Position() {
        // TODO : faire une factory
        latitude = null;
        longitude = null;
    }

    /**
     * Position private constructor.
     * @param in the super state
     */
    private Position(Parcel in) {
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
        if (location != null) {
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
        } else {
            this.latitude = null;
            this.longitude = null;
            // the address list is null when not in address mode
            if (this.addresses != null) {
                this.addresses.clear();
                this.addresses.add(0, null);
            }
        }
    }

    /**
     * Returns true if latitude or longitude is null.
     * @return true if latitude or longitude is null
     */
    public boolean isNull() {
        return this.latitude == null || this.longitude == null;
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

    /**
     * Returns the location stored by the class.
     * @return the location stored by the class
     */
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
     * Returns a formatted string for location.
     * @return a formatted string for location
     */
    public String getFormattedLocation() {
        if (!isNull()) {
            return String.valueOf(this.latitude) + " / " + String.valueOf(this.longitude);
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

    /**
     * Returns true if the addresses list is filled and a selection is made on it.
     * @return false when the addressses list is empty or no selection exists
     */
    public boolean hasAddresses() {
        if (this.addresses == null) {
            return false;
        } else {
            // there should be at least two elements as there is an empty address
            return this.addresses.size() > 1 && this.selectedAddress != 0;
        }
    }

    /**
     * Returns the formatted selected address.
     * @return a string represnting the selected address
     */
    public String getFormattedAddress() {
        String address = "";

        if (this.addresses != null && !this.addresses.isEmpty() && this.selectedAddress <= this.addresses.size() && this.selectedAddress != 0) {
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
    public int getSelectedAddressPosition() {
        return this.selectedAddress;
    }

    /**
     * Returns the selected address.
     * @return the selected address
     */
    public Address getSelectedAddress() {
        if (this.addresses != null && !this.addresses.isEmpty() && this.selectedAddress <= this.addresses.size() && this.selectedAddress != 0) {
            return this.addresses.get(selectedAddress);
        } else {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeDouble(this.latitude == null ? 0 : this.latitude);
        out.writeDouble(this.longitude == null ? 0 : this.longitude);


        Parcelable[] addrs = null;

        if (this.addresses != null) {
            addrs = new Parcelable[this.addresses.size()];

            for (int rank = 0; rank < this.addresses.size(); rank++) {
                addrs[rank] = this.addresses.get(rank);
            }
        }

        out.writeParcelableArray(addrs, 0);
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
