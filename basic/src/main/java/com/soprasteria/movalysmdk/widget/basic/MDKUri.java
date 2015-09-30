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
package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.InputType;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.core.behavior.types.HasUri;

/**
 * MDK Uri
 * <p>Representing an editable text validate with an uri regexp</p>
 * <p>
 *     This widget present a command who send the intent Intent.ACTION_SEND to the
 *     Android system with the component text by default.
 * </p>
 * <p>The validation regexp is stored in R.string.uri_regexp</p>
 */
public class MDKUri extends MDKCommandsEditText implements HasUri {

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     */
    public MDKUri(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSpecificAttributes(InputType.TYPE_TEXT_VARIATION_URI, new int[]{R.string.mdkvalidator_uri_class});
    }

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     * @param style the layout defined style
     */
    public MDKUri(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        setSpecificAttributes(InputType.TYPE_TEXT_VARIATION_URI, new int[]{R.string.mdkvalidator_uri_class});
    }

    @Override
    protected Uri getCommandInput() {
        Uri retUri = null;
        String sUriAddress = this.getText().toString();
        if (!sUriAddress.isEmpty()){
            retUri = Uri.parse(sUriAddress);
        }
        return retUri;
    }

    @Override
    public String getUri() {
        return this.getText().toString();
    }

    @Override
    public void setUri(String uri) {
        this.setText(uri);
    }

    @Override
    protected IntentFilter[] getBroadcastIntentFilters() {
        return new IntentFilter[] {
                new IntentFilter(getResources().getString(R.string.mdkcommand_uri_action))
        };
    }
}
