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
package com.soprasteria.movalysmdk.widget.basic.command;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;

/**
 * <p>Class handling uri path building: to, cc, bcc, subject, body.
 * Ask for what application will be used to open the mail on the device.</p>
 *
 * <p>The uri command uses the Intent.ACTION_VIEW intent to open the default device
 * browser at the parameter's address.</p>
 */
public class UriWidgetCommand implements WidgetCommand<Uri, Void> {

    /**
     * Send an uri using the uri parameters.
     * <p>This method call the ACTION_VIEW Intent.</p>
     *
     * @param context the Android context
     * @param uriParams uri information
     * @return null
     */
    @Override
    public Void execute(Context context, Uri... uriParams) {
        Intent uriIntent = new Intent(Intent.ACTION_VIEW);
        if (uriParams == null || uriParams.length != 1){
            throw new IllegalArgumentException("uri command should only have one Uri in parameter.");
        } else {
            context.startActivity(uriIntent.setData(uriParams[0]));
        }

        return null;
    }
}
