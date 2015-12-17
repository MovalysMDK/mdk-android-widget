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
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.soprasteria.movalysmdk.widget.fixedlist.FixedListAddListener;
import com.soprasteria.movalysmdk.widget.fixedlist.FixedListItemClickListener;
import com.soprasteria.movalysmdk.widget.fixedlist.FixedListRemoveListener;
import com.soprasteria.movalysmdk.widget.fixedlist.MDKFixedList;
import com.soprasteria.movalysmdk.widget.fixedlist.MDKRichFixedList;

/**
 * Test activity for the MDKRichFixedList widget.
 */
public class WrappedFixedListActivity extends AbstractFixedListActivity {

    /** the MDKFixedList widget. */
    private MDKFixedList mFixedList;

    /** the MDKRichFixedList widget. */
    private MDKRichFixedList mRichFixedList;

    /** the Readonly MDKRichFixedList widget. */
    private MDKRichFixedList mRichFixedListReadonly;

    /** the MDKFixedList adapter. */
    private MyAdapter mFxlAdapter;

    /** the MDKRichFixedList adapter. */
    private MyAdapter mRichFxlAdapter;

    /** the Readonly MDKRichFixedList adapter. */
    private MyAdapter mRichFxlAdapterReadonly;

    @Override
    protected int[] getWidgetIds() {
        return new int[] {
                R.id.mdkRichFixedList,
                R.id.mdkFixedList,
                R.id.mdkRichFixedList_readonly,
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapped_fixed_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // "Inner" fixed list
        mFixedList = (MDKFixedList) findViewById(R.id.mdkFixedList);
        mFxlAdapter = new MyAdapter(myDataset);
        mFixedList.setAdapter(mFxlAdapter);
        mFixedList.addAddListener(new FixedListAddListener() {
            @Override
            public void onAddClick() {
                showInputDialog(mFxlAdapter, -1);
            }
        });
        mFixedList.addRemoveListener(new FixedListRemoveListener() {
            @Override
            public void onRemoveItemClick(int position) {
                ((MyAdapter) mFixedList.getAdapter()).removeItemAt(position);
            }
        });
        mFixedList.addItemClickListener(new FixedListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showInputDialog(mFxlAdapter, position);
            }
        });

        // Rich fixed list
        mRichFixedList = (MDKRichFixedList) findViewById(R.id.mdkRichFixedList);
        mRichFxlAdapter = new MyAdapter(myDataset);
        mRichFixedList.setAdapter(mRichFxlAdapter);
        mRichFixedList.addAddListener(new FixedListAddListener() {
            @Override
            public void onAddClick() {
                showInputDialog(mRichFxlAdapter, -1);
            }
        });
        mRichFixedList.addRemoveListener(new FixedListRemoveListener() {
            @Override
            public void onRemoveItemClick(int position) {
                ((MyAdapter) mRichFixedList.getInnerWidget().getAdapter()).removeItemAt(position);
            }
        });
        mRichFixedList.addItemClickListener(new FixedListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showInputDialog(mRichFxlAdapter, position);
            }
        });

        // Reaonly Rich fixed list
        mRichFixedListReadonly = (MDKRichFixedList) findViewById(R.id.mdkRichFixedList_readonly);
        mRichFxlAdapterReadonly = new MyAdapter(myDataset);
        mRichFixedListReadonly.setAdapter(mRichFxlAdapterReadonly);
        mRichFixedListReadonly.addAddListener(new FixedListAddListener() {
            @Override
            public void onAddClick() {
                showInputDialog(mRichFxlAdapterReadonly, -1);
            }
        });
        mRichFixedListReadonly.addRemoveListener(new FixedListRemoveListener() {
            @Override
            public void onRemoveItemClick(int position) {
                ((MyAdapter) mRichFixedListReadonly.getInnerWidget().getAdapter()).removeItemAt(position);
            }
        });
        mRichFixedListReadonly.addItemClickListener(new FixedListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showInputDialog(mRichFxlAdapterReadonly, position);
            }
        });
    }

    /**
     * Shows a dialog to input data in the adapter.
     * @param adapter the adapter on which data should be modified / added
     * @param position the position of the data to input, -1 to add a new cell
     */
    private void showInputDialog(final MyAdapter adapter, final int position) {
        String value = "";
        if (position > -1) {
            value = adapter.getItemAt(position);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(this.getApplicationContext());
        View promptView = layoutInflater.inflate(R.layout.dialog_staggered_fixed_list, null);

        final EditText editText = (EditText) promptView.findViewById(R.id.item_value);
        final Button valid = (Button) promptView.findViewById(R.id.validateButton);
        final Button cancel = (Button) promptView.findViewById(R.id.cancelButton);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(promptView);

        editText.setTextColor(ColorStateList.valueOf(Color.BLACK));
        editText.setText(value);

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    adapter.addItem(editText.getText().toString());
                } else {
                    adapter.updateItemAt(position, editText.getText().toString());
                }
                dialog.cancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

}
