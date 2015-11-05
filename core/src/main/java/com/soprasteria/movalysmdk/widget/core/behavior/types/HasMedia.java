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
package com.soprasteria.movalysmdk.widget.core.behavior.types;

import android.net.Uri;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Adds media behavior to a widget.
 */
public interface HasMedia {

    /** Media type constant for photo. **/
    int TYPE_PHOTO = 0;

    /** Media type constant for photo. **/
    int TYPE_VIDEO = 1;

    /** Media type constant for photo. **/
    int TYPE_FILE = 2;

    /**
     * Enumeration listing possible MDKMedia modes.
     */
    @IntDef({TYPE_PHOTO,TYPE_VIDEO,TYPE_FILE})
    @Retention(RetentionPolicy.SOURCE)
    @interface MediaType {
    }

    /**
     * Gets the widget's media uri.
     * @return the media uri
     */
    Uri getMediaUri();

    /**
     * Sets the widget's media, updating the thumbnail.
     * @param uri the uri of the media
     */
    void setMediaUri(Uri uri);

    /**
     * Gets the widget's media type.
     * @return the media type
     */
    @MediaType int getMediaType();

    /**
     * Sets the widget's media type.
     * @param type the media type
     */
    void setMediaType(@MediaType int type);

    /**
     * Sets the widget's placeholder drawable resource id and updates it on the view.
     * @param drawableRes the placeholder drawable resource id
     */
    void setPlaceholder(int drawableRes);
    /**
     * Gets the widget's placeholder drawable resource id.
     * @return the placeholder drawable resource id
     */
    int getPlaceholder();
}
