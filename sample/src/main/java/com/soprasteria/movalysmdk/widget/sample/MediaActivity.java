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

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;

/**
 * Test activity for the MDKMedia widget.
 */
public class MediaActivity extends AbstractWidgetTestableActivity {

    @Override
    protected int[] getWidgetIds() {
        return new int[]{
                R.id.mdkMedia,
                R.id.mdkRichMedia_withLabelAndError,
                R.id.mdkRichMedia_withCustomPlaceholder,
                R.id.mdkMedia_readonly
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        Button openDialog = (Button) findViewById(R.id.mdkButton_richMediaInDialog);

        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_media);
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((MDKWidgetApplication)getApplication()).getMDKWidgetComponentActionHelper().handleActivityResult(requestCode, resultCode, data);
    }
}