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
package com.soprasteria.movalysmdk.widget.core.message;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;

/**
 * Class dedicated to methods on messages formatting.
 * Formats a message as <em>component_label: message</em>
 */
public class MDKSimpleMessageFormat implements MDKMessageFormat {

    /**
     * Constructor.
     * @param context application context to access resource
     * @param messages MDKMessage object containing
     * @param sharedMessageWidget defined if the message is defined inside a Rich component
     * @return oFormattedMessage the formatted message
     */
    @Override
    public CharSequence formatText(Context context, MDKMessages messages, boolean sharedMessageWidget) {

        CharSequence oFormattedMessage = messages.getErrorMessage();

        if (sharedMessageWidget) {
            StringBuilder formattedText = new StringBuilder(messages.getComponentLabel());
            formattedText.append(": ");
            formattedText.append(oFormattedMessage);
            oFormattedMessage = formattedText.toString();
        }
        return oFormattedMessage;
    }

}



