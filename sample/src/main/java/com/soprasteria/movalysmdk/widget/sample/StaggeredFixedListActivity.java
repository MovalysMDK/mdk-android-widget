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
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.soprasteria.movalysmdk.widget.fixedlist.FixedListAddListener;
import com.soprasteria.movalysmdk.widget.fixedlist.FixedListItemClickListener;
import com.soprasteria.movalysmdk.widget.fixedlist.FixedListRemoveListener;
import com.soprasteria.movalysmdk.widget.fixedlist.MDKRichFixedList;

/**
 * Test activity for the MDKRichFixedList widget.
 */
public class StaggeredFixedListActivity extends AbstractFixedListActivity {

    /** the MDKRichFixedList widget. */
    private MDKRichFixedList mRichFixedList;

    /** the MDKRichFixedList adapter. */
    private MyAdapter mRichFxlAdapter;

    @Override
    protected int[] getWidgetIds() {
        return new int[] {
                R.id.mdkRichFixedList,
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_fixed_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRichFixedList = (MDKRichFixedList) findViewById(R.id.mdkRichFixedList);

        mRichFxlAdapter = new MyAdapter(myDataset);
        mRichFixedList.setAdapter(mRichFxlAdapter);

        mRichFixedList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        mRichFixedList.addAddListener(new FixedListAddListener() {
            @Override
            public void onAddClick() {
                startDetailActivity(-1, "");
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
                startDetailActivity(position, mRichFxlAdapter.getItemAt(position));
            }
        });
    }

    /**
     * Start the detail activity with the given request code and values.
     * @param position the request code to send
     * @param itemAt the value of the detail to modify
     */
    private void startDetailActivity(int position, String itemAt) {
        Intent intent = new Intent(StaggeredFixedListActivity.this, FixedListDetailActivity.class);
        intent.putExtra("pos", position);
        intent.putExtra("value", itemAt);
        startActivityForResult(intent, RC_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CODE && resultCode == RESULT_OK) {
            String res = data.getStringExtra("value");
            int position = data.getIntExtra("position", -1);

            if (position == -1) {
                mRichFxlAdapter.addItem(res);
            } else {
                mRichFxlAdapter.updateItemAt(position, res);
            }
        }
    }
}
