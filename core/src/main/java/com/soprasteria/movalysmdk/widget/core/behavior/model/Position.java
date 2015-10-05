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
package com.soprasteria.movalysmdk.widget.core.behavior.model;

import android.location.Address;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * Position class.
 * Represents a position object as processed by the MDKPosition widget
 */
public class Position implements Parcelable {

    /** location. */
    private Location location;

    /** address. */
    private Address address;

    /**
     * Position public constructor.
     */
    public Position() {
        // TODO : faire une factory -> basse priorité
        location = null;
        address = new Address(Locale.getDefault());

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
        if (location != null) {
            return location.getLatitude();
        }
        return null;
    }

    /**
     * Returns the object's longitude.
     * @return the longitude
     */
    public Double getLongitude() {
        if (location != null) {
            return location.getLongitude();
        }
        return null;
    }


    /**
     * Returns the location stored by the class.
     * @return the location stored by the class
     */
    public Location getLocation() {
        return location;
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
