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
package com.soprasteria.movalysmdk.widget.basic.delegate;

import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.basic.model.Email;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;

/**
 * Delegate class for the MDKEmail widget.
 */
public class MDKEmailWidgetDelegate extends MDKWidgetDelegate {

    /**
     * Constructor.
     * @param view  the view
     * @param attrs the parameters set
     */
    public MDKEmailWidgetDelegate(View view, AttributeSet attrs) {
        super(view, attrs);
    }

    /**
     * Transforms an array of strings to an {@link Email} object.
     * @param emailArray the string of array
     * @return the {@link Email} object
     */
    public Email stringArrayToEmail(String[] emailArray) {
        Email emailObject = new Email();

        if (emailArray != null && emailArray.length == 5) {
            if (emailArray[0] != null) {
                emailObject.setTo(new String[]{emailArray[0]});
            }

            if (emailArray[1] != null) {
                emailObject.setCc(new String[]{emailArray[1]});
            }

            if (emailArray[2] != null) {
                emailObject.setBcc(new String[]{emailArray[2]});
            }

            emailObject.setSubject(emailArray[3]);
            emailObject.setBody(emailArray[4]);
        }

        return emailObject;

    }

    /**
     * Transforms an {@link Email} object to an array of strings.
     * @param emailObject the {@link Email} object
     * @return the string of array
     */
    public String[] emailToStringArray(Email emailObject) {
        String[] emailArray = new String[5];

        if (emailObject.getTo() != null && emailObject.getTo().length > 0) {
            emailArray[0] = emailObject.getTo()[0];
        } else {
            emailArray[0] = null;
        }

        if (emailObject.getCc() != null && emailObject.getCc().length > 0) {
            emailArray[1] = emailObject.getCc()[0];
        } else {
            emailArray[1] = null;
        }

        if (emailObject.getBcc() != null && emailObject.getBcc().length > 0) {
            emailArray[2] = emailObject.getBcc()[0];
        } else {
            emailArray[2] = null;
        }

        emailArray[3] = emailObject.getSubject();
        emailArray[4] = (String)emailObject.getBody();

        return emailArray;
    }
}
