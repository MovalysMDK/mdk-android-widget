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
package com.soprasteria.movalysmdk.widget.core.error;

import android.content.Context;

/**
 * Class dedicated to methods on error messages formatting.
 */
public class MDKSimpleErrorMessageFormat implements MDKErrorMessageFormat {

    /**
     * Constructor.
     * @param sharedErrorWidget defined if the error is defined inside a Rich component
     * @param error MDKError object containing
     * @return oFormattedMessage the formatted message
     */
    @Override
    public CharSequence formatText(Context context, MDKError error, boolean sharedErrorWidget) {

        CharSequence oFormattedMessage = error.getErrorMessage();

        if (sharedErrorWidget) {
            StringBuilder formattedText = new StringBuilder(error.getComponentLabelName());
            formattedText.append(": ");
            formattedText.append(oFormattedMessage);
            oFormattedMessage = formattedText.toString();
        }
        return oFormattedMessage;
    }

}



