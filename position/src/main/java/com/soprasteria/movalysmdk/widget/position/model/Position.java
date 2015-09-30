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

//    /** WGS84 Latitude. */
//    private Double latitude;
//
//    /** WGS84 Longitude. */
//    private Double longitude;
//
//    /** list of addresses read from the location. */
//    private List<Address> addresses;
//
//    /** user selected address in the list. */
//    private int selectedAddress = 0;

    /** double formatter. */
    private NumberFormat formatter;

    private Location location;

    private Address address;

    /**
     * Position public constructor.
     */
    public Position() {
        // TODO : faire une factory
//        latitude = null;
//        longitude = null;
        location = new Location("dummy");
        address = new Address(Locale.getDefault());
        // TODO : externaliser le formatage
        formatter = new DecimalFormat("#0.0000000");
    }

    /**
     * Position private constructor.
     * @param in the super state
     */
    private Position(Parcel in) {
//        this.latitude = in.readDouble();
//        this.longitude = in.readDouble();
//
//        Parcelable[] inAddresses = in.readParcelableArray((ClassLoader) Address.CREATOR);
//
//        this.addresses = new ArrayList<>();
//
//        for (Parcelable address : inAddresses) {
//            this.addresses.add((Address) address);
//        }
        this.location = in.readParcelable((ClassLoader) Location.CREATOR);
        this.address = in.readParcelable((ClassLoader) Address.CREATOR);
    }

    /**
     * Sets the position from the given location.
     * @param location the location to set the position from
     */
    public void setPositionFromLocation(Location location) {
//        if (location != null) {
//            this.latitude = location.getLatitude();
//            this.longitude = location.getLongitude();
//        } else {
//            this.latitude = null;
//            this.longitude = null;
//            // the address list is null when not in address mode
//            if (this.addresses != null) {
//                this.addresses.clear();
//                this.addresses.add(0, null);
//            }
//        }
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

//    /**
//     * Sets the object's latitude.
//     * @param latitude the latitude to set
//     */
//    public void setLatitude(Double latitude) {
//        this.location.setLatitude(latitude);
//    }

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

//    /**
//     * Sets the object's longitude.
//     * @param longitude the longitude to set
//     */
//    public void setLongitude(Double longitude) {
//        this.longitude = longitude;
//    }

    /**
     * Returns the location stored by the class.
     * @return the location stored by the class
     */
    public Location getLocation() {
//        Location location = new Location("dummyprovider");
//
//        if (this.latitude != null) {
//            location.setLatitude(this.latitude);
//        }
//        if (this.longitude != null) {
//            location.setLongitude(this.longitude);
//        }

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
//        boolean isLatitudeEqual;
//
//        if (this.latitude == null) {
//            isLatitudeEqual = false;
//        } else {
//            isLatitudeEqual = PositionHelper.round(location.getLatitude(), 4) == PositionHelper.round(this.latitude, 4);
//        }
//        boolean isLongitudeEqual;
//        if (this.longitude == null) {
//            isLongitudeEqual = false;
//        } else {
//            isLongitudeEqual = PositionHelper.round(location.getLongitude(), 4) == PositionHelper.round(this.longitude, 4);
//        }
//
//        return isLatitudeEqual && isLongitudeEqual;

        if (this.location != null) {
            boolean isLatitudeEqual = PositionHelper.round(location.getLatitude(), 4) == PositionHelper.round(this.location.getLatitude(), 4);
            boolean isLongitudeEqual = PositionHelper.round(location.getLongitude(), 4) == PositionHelper.round(this.location.getLongitude(), 4);

            return isLatitudeEqual && isLongitudeEqual;
        } else {
            return false;
        }
    }

//    /**
//     * Returns the list of addresses stored in the object.
//     * @return the list of addresses
//     */
//    public List<Address> getAddresses() {
//        return this.addresses;
//    }
//
//    /**
//     * Sets the addresses found for the location.
//     * @param addresses the addresses list
//     */
//    public void setAddresses(List<Address> addresses) {
//        this.addresses = addresses;
//    }
//
//    /**
//     * Sets the index of the selected address.
//     * @param selection the index of the selected address
//     */
//    public void setSelectedAddress(int selection) {
//        this.selectedAddress = selection;
//    }
//
//    /**
//     * Returns true if the addresses list is filled and a selection is made on it.
//     * @return false when the addresses list is empty or no selection exists
//     */
//    public boolean hasAddresses() {
//        if (this.addresses == null) {
//            return false;
//        } else {
//            // there should be at least two elements as there is an empty address
//            return this.addresses.size() > 1 && this.selectedAddress != 0;
//        }
//    }

    /**
     * Returns the formatted selected address.
     * @return a string representing the selected address
     */
    public String getFormattedAddress() {
        String address = "";

//        if (this.addresses != null && !this.addresses.isEmpty() && this.selectedAddress <= this.addresses.size() && this.selectedAddress != 0) {
//            Address currentAddress = this.addresses.get(this.selectedAddress);
//            if (currentAddress.getAddressLine(0) != null) {
//                address = currentAddress.getAddressLine(0);
//            }
//            if (currentAddress.getLocality() != null) {
//                if (address.length() > 0) {
//                    address += " ";
//                }
//                address += currentAddress.getLocality();
//            }
//        }
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

//    /**
//     * Returns the index of the selected address.
//     * @return the index of the selected address
//     */
//    public int getSelectedAddressPosition() {
//        return this.selectedAddress;
//    }
//
//    /**
//     * Returns the selected address.
//     * @return the selected address
//     */
//    public Address getSelectedAddress() {
//        if (this.addresses != null && !this.addresses.isEmpty() && this.selectedAddress <= this.addresses.size() && this.selectedAddress != 0) {
//            return this.addresses.get(selectedAddress);
//        } else {
//            return null;
//        }
//    }

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
//        out.writeDouble(this.latitude == null ? 0 : this.latitude);
//        out.writeDouble(this.longitude == null ? 0 : this.longitude);
//
//
//        Parcelable[] addrs = null;
//
//        if (this.addresses != null) {
//            addrs = new Parcelable[this.addresses.size()];
//
//            for (int rank = 0; rank < this.addresses.size(); rank++) {
//                addrs[rank] = this.addresses.get(rank);
//            }
//        }
//
//        out.writeParcelableArray(addrs, 0);

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
