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

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.soprasteria.movalysmdk.widget.core.behavior.model.Position;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.positionmaps.MDKRichMapsPosition;
import com.soprasteria.movalysmdk.widget.positionmaps.MDKRichStaticMapsPosition;

/**
 * Test activity for the MDKRichPosition widget.
 */
public class MapsActivity extends AbstractWidgetTestableActivity {

    @Override
    protected int[] getWidgetIds() {
        return new int[]{
                R.id.mdkRichMapsPosition_locationWithLabelAndError,
                R.id.mdkRichMapsPosition_addressesWithLabelAndError,
                R.id.mdkRichMapsPosition_infoWithLabelAndError,
                R.id.mdkMapsPosition_withErrorAndCommandOutside,
                R.id.mdkMapsPosition_non_editable,
//                R.id.mdkRichPosition_withoutLabel,
//                R.id.mdkRichPosition_withCustomLayout,
//                R.id.mdkRichPosition1_withSharedError,
//                R.id.mdkRichPosition2_withSharedError,
//                R.id.position_helper,
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Position pos = new Position();
        Location loc = new Location("dummy");
        loc.setLatitude(48.104451);
        loc.setLongitude(-1.66901);
        pos.setPositionFromLocation(loc);

        MDKRichMapsPosition map = (MDKRichMapsPosition) findViewById(R.id.mdkRichMapsPosition_locationWithLabelAndError);
        map.setPosition(pos);
        MDKRichStaticMapsPosition staticMap = (MDKRichStaticMapsPosition) findViewById(R.id.mdkMapsPosition_non_editable);
        staticMap.setPosition(pos);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((MDKWidgetApplication)getApplication()).getMDKWidgetComponentActionHelper().handleActivityResult(requestCode, resultCode, data);
    }
}