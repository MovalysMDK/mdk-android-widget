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
package com.soprasteria.movalysmdk.widget.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;

/**
 * MDKBaseRichWidgetSavedState class definition.
 */
class MDKSavedState extends View.BaseSavedState {

    /** Children states. */
    SparseArray childrenStates;

    /**
     * Constructor.
     * @param superState the new Parcelable
     */
    MDKSavedState(Parcelable superState) {
        super(superState);
    }

    /**
     * Constructor.
     * @param in the new Parcelable
     * @param classLoader the class loader
     */
    private MDKSavedState(Parcel in, ClassLoader classLoader) {
        super(in);
        childrenStates = in.readSparseArray(classLoader);
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeSparseArray(childrenStates);
    }

    /**
     * Required field that makes Parcelables from a Parcel.
     */
    public static final ClassLoaderCreator<MDKSavedState> CREATOR =
            new ClassLoaderCreator<MDKSavedState>() {

                @Override
                public MDKSavedState createFromParcel(Parcel source, ClassLoader loader) {
                    return new MDKSavedState(source, loader);
                }

                @Override
                public MDKSavedState createFromParcel(Parcel source) {
                    return new MDKSavedState(source, MDKSavedState.class.getClassLoader());
                }

                @Override
                public MDKSavedState[] newArray(int size) {
                    return new MDKSavedState[size];
                }
            };
}
