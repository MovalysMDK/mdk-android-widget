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
import android.view.View;
import android.widget.Button;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;

/**
 * Test activity for the MDKRichEditText widget.
 */
public class EditTextActivity extends AbstractWidgetTestableActivity {

    /**
     * MDKRichEditText with custom layout.
     */
    private MDKRichEditText mdkRichEditText;
    private MDKRichEditText mdkRichEditText2;

    /**
     * Button to clear/fill mdkRichEditText.
     */
    private Button fillEraseButton;

    @Override
    protected int[] getWidgetIds() {
        return new int[] {
                R.id.mdkRichEditText_withCustomLayout,
                R.id.mdkRichEditText_withCustomLayoutAndButton,
                R.id.mdkRichEditText_withLabelAndMandatory,
                R.id.mdkRichEditText_withLabelAndHint,
                R.id.mdkRichEditText_withoutLabelAndHint,
                R.id.mdkRichEditText_withoutLabelButHint,
                R.id.mdkEditText_withExternalLabelAndSharedError,
                R.id.mdkEditText_withHintAndExternalLabelAndSharedError,
                R.id.test_case_7,
                R.id.mdkEditText_withHintAndSharedError,
                R.id.mdkRichEditText_readonly
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        this.mdkRichEditText = (MDKRichEditText) findViewById(R.id.mdkRichEditText_withCustomLayoutAndButton);
        this.fillEraseButton = (Button) findViewById(R.id.richedittext_button_remplir_effacer);

        fillEraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getString(R.string.action_remplir).equals(fillEraseButton.getText())) {
                    mdkRichEditText.setText(getString(R.string.hello_world));
                    fillEraseButton.setText(R.string.action_vider);
                } else {
                    mdkRichEditText.setText(getString(R.string.empty_string));
                    fillEraseButton.setText(R.string.action_remplir);
                }
            }
        });

        this.mdkRichEditText2 = (MDKRichEditText) findViewById(R.id.mdkRichEditText_readonly);
        this.mdkRichEditText2.setText(getString(R.string.app_name));
    }
}
