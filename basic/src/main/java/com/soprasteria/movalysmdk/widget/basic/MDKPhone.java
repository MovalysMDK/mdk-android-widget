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
import android.text.InputType;
import android.util.AttributeSet;

/**
 * MDK Phone
 * <p>Representing an editable text validate for phone input</p>
 * <p>
 *     This widget present a command who send the intent Intent.ACTION_VIEW to the
 *     Android system with the component text by default.
 * </p>
 */
public class MDKPhone extends MDKCommandsEditText {

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     */
    public MDKPhone(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSpecificAttributes(InputType.TYPE_CLASS_PHONE, new int[]{R.string.mdkvalidator_phone_class});
    }

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     * @param style the layout defined style
     */
    public MDKPhone(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        setSpecificAttributes(InputType.TYPE_CLASS_PHONE, new int[]{R.string.mdkvalidator_phone_class});
    }
}
