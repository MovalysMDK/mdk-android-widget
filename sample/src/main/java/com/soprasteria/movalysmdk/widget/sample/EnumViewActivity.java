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
package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;

import com.soprasteria.movalysmdk.widget.basic.MDKEnumView;
import com.soprasteria.movalysmdk.widget.basic.MDKRichEnumView;
import com.soprasteria.movalysmdk.widget.sample.enums.BabyAnimals;

/**
 * Test activity for the MDKRichPosition widget.
 */
public class EnumViewActivity extends AbstractWidgetTestableActivity {

    @Override
    protected int[] getWidgetIds() {
        return new int[]{
                R.id.mdkRichEnumImage_withLabelAndError,
                R.id.mdkEnumImage_withErrorAndCommandOutside,
                R.id.mdkEnumImage_withImageSetByString,
                R.id.mdkRichEnumText_withLabelAndError
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enumview);


        //initializing images from enum
        MDKRichEnumView withLabelAndError = (MDKRichEnumView) findViewById(R.id.mdkRichEnumImage_withLabelAndError);
        withLabelAndError.setValueFromEnum(BabyAnimals.PUPPY);

        MDKEnumView withErrorAndCommandOutside = (MDKEnumView) findViewById(R.id.mdkEnumImage_withErrorAndCommandOutside);
        withErrorAndCommandOutside.setValueFromEnum(BabyAnimals.KITTEN);

        //initializing image from string
        MDKEnumView withImageSetByString = (MDKEnumView) findViewById(R.id.mdkEnumImage_withImageSetByString);
        withImageSetByString.setValueFromString("babyanimals_cub");

        //initializing text from enum
        MDKRichEnumView withText = (MDKRichEnumView) findViewById(R.id.mdkRichEnumText_withLabelAndError);
        withText.setValueFromId(R.string.hello_world);
    }

}