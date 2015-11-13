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
package com.soprasteria.movalysmdk.widget.basic.model;


import android.net.Uri;

/**
 * MDKPresenter class.
 * <p>Value object representing an presenter object to pass to the MDKPresenterView.</p>
 */
public class MDKPresenter {
    /**
     * The title.
     */
    private String string;
    /**
     * The uri.
     */
    private Uri uri;

    /**
     * Constructor.
     */
    public MDKPresenter() {
        //Base constructor
    }

    /**
     * Constructor.
     *
     * @param string the title
     * @param uri    the uri for image
     */
    public MDKPresenter(String string, Uri uri) {
        this.string = string;
        this.uri = uri;
    }

    /**
     * Constructor.
     *
     * @param string      the title
     * @param uri         the uri for image
     * @param firstLetter boolean to test if the first letter of title string is required
     */
    public MDKPresenter(String string, Uri uri, boolean firstLetter) {
        if (firstLetter) {
            this.string = getFirstLetterToUpper(string);
        } else {
            this.string = string;
        }
        this.uri = uri;
    }

    /**
     * Title getter.
     *
     * @return the title.
     */
    public String getString() {
        return this.string;
    }

    /**
     * Title setter.
     *
     * @param string the title to set
     */
    public void setString(String string) {
        this.string = string;
    }

    /**
     * The uri getter.
     *
     * @return the uri
     */
    public Uri getUri() {
        return this.uri;
    }

    /**
     * The uri setter.
     *
     * @param uri the uri to set
     */
    public void setUri(Uri uri) {
        this.uri = uri;
    }

    /**
     * To get the first letter of a string in uppercase.
     *
     * @param title the string used to get the first letter in uppercase
     * @return the first letter in uppercase
     */
    private String getFirstLetterToUpper(String title) {
        if (title != null) {
            return title.substring(0, 1).toUpperCase();
        }
        return null;
    }

    /**
     * To set the title with getFirstLetterToUpper method.
     *
     * @param title the string used to get the first letter in uppercase and set into titleView
     */
    public void setTitleFirstLetterToUpper(String title) {
        String newTitle = this.getFirstLetterToUpper(title);
        if (title != null) {
            this.setString(newTitle);
        }
    }
}
