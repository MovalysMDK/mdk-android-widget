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
package com.soprasteria.movalysmdk.widget.sample.error;

import android.content.Context;

import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessageFormat;
import com.soprasteria.movalysmdk.widget.sample.R;

/**
 * Error Formatter that add the prefix "42 /!\ " to MDKError messages.
 */
public class FortyTwoErrorMessageFormat implements MDKMessageFormat {

    @Override
    public CharSequence formatText(Context context, MDKMessages messages, boolean sharedMessageWidget) {

        return context.getString(R.string.error_prefix) + messages.getErrorMessage();
    }
}
