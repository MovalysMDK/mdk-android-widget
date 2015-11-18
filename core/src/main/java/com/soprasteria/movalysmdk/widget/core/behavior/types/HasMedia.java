/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 * <p/>
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

/**
 * Adds media behavior to a widget.
 */
public interface HasMedia {

    /**
     * Gets the widget's media uri.
     *
     * @return the media uri
     */
    Uri getMediaUri();

    /**
     * Sets the widget's media, updating the thumbnail.
     *
     * @param uri the uri of the media
     */
    void setMediaUri(Uri uri);

    /**
     * Gets the widget's media type.
     *
     * @return the media type
     */
    int getMediaType();

    /**
     * Sets the widget's media type.
     *
     * @param type the media type
     */
    void setMediaType(int type);

    /**
     * Sets the widget's placeholder drawable resource id and updates it on the view.
     *
     * @param drawableRes the placeholder drawable resource id
     */
    void setPlaceholder(int drawableRes);

    /**
     * Gets the widget's placeholder drawable resource id.
     *
     * @return the placeholder drawable resource id
     */
    int getPlaceholder();

    /**
     * Get the modified photo svg.
     *
     * @return the modified photo svg
     */
    String getModifiedPhotoSvg();

    /**
     * Set the modified photo svg.
     *
     * @param svg the modified photo svg
     */
    void setModifiedPhotoSvg(String svg);
}
