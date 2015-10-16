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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.fixedlist.FixedListItemClickListener;
import com.soprasteria.movalysmdk.widget.fixedlist.FixedListRemoveListener;
import com.soprasteria.movalysmdk.widget.fixedlist.MDKFixedList;
import com.soprasteria.movalysmdk.widget.fixedlist.MDKRichFixedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test activity for the MDKRichFixedList widget.
 */
public class WrappedFixedListActivity extends AbstractWidgetTestableActivity {

    /** Tag for debugging. */
    private static final String FIXEDACTIVITY = "FIXEDACTIVITY";

    /** mask for the request code. */
    public static final int RC_MASK = 0xC000;

    /** mask for the position of the clicked item in the request code. */
    public static final int POS_MASK = 0x3FFF;

    /** request code for the item clicked event. */
    private static final int RC_CODE = 0x8000;

    /** the MDKFixedList widget. */
    private MDKFixedList mFixedList;

    /** the MDKRichFixedList widget. */
    private MDKRichFixedList mRichFixedList;

    /** the MDKFixedList adapter. */
    private RecyclerView.Adapter mFxlAdapter;

    /** the MDKRichFixedList adapter. */
    private RecyclerView.Adapter mRichFxlAdapter;

    /** the displayed dataset. */
    private String[] myDataset = new String[] {"test 1","test 2","test 3","test 4","test 5","test 6","test 7","test 8","test 9","test 10","test 11","test 12","test 13"};

    @Override
    protected int[] getWidgetIds() {
        return new int[] {
                R.id.mdkRichFixedList,
                R.id.mdkFixedList,
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapped_fixed_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFixedList = (MDKFixedList) findViewById(R.id.mdkFixedList);
        mRichFixedList = (MDKRichFixedList) findViewById(R.id.mdkRichFixedList);

        // specify an adapter (see also next example)
        mFxlAdapter = new MyAdapter(myDataset);
        mFixedList.setAdapter(mFxlAdapter);

        mRichFxlAdapter = new MyAdapter(myDataset);
        mRichFixedList.setAdapter(mRichFxlAdapter);

        mFixedList.addItemClickListener(new FixedListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(WrappedFixedListActivity.this, FixedListDetailActivity.class);
                intent.putExtra("RC", RC_CODE | position);
                startActivityForResult(intent, RC_CODE | position);
            }
        });

        mFixedList.addRemoveListener(new FixedListRemoveListener() {
            @Override
            public void onRemoveItemClick(int position) {
                ((MyAdapter)mFixedList.getAdapter()).removeItemAt(position);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(FIXEDACTIVITY, "REQ :"+requestCode);

        Log.d(FIXEDACTIVITY, "RC :" + String.format("0x%8s", Integer.toHexString(requestCode & RC_MASK)).replace(' ', '0'));

        Log.d(FIXEDACTIVITY, "POS :"+(requestCode&POS_MASK));

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Adapter class for the widgets.
     */
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        /** the displayed dataset. */
        private List<String> mDataset;

        /**
         * Constructor.
         * @param dataset the set displayed dataset
         */
        public MyAdapter(String[] dataset) {
            this.mDataset = new ArrayList<>(Arrays.asList(dataset));
        }

        /**
         * Provide a reference to the views for each data item.
         * Complex data items may need more than one view per item, and
         * you provide access to all the views for a data item in a view holder
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            /** first text view. */
            private TextView mTextView;
            /** second text view. */
            private TextView mTextView2;

            /**
             * Constructor.
             * @param v the view to hold
             */
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(android.R.id.text1);
                mTextView2 = (TextView) v.findViewById(android.R.id.text2);
            }

            /**
             * returns the first TextView.
             * @return the first Textview
             */
            public TextView getmTextView() {
                return mTextView;
            }

            /**
             * returns the second TextView.
             * @return the second Textview
             */
            public TextView getmTextView2() {
                return mTextView2;
            }
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new ViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.getmTextView().setText(mDataset.get(position));
            holder.getmTextView2().setText(mDataset.get(position));
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        /**
         * Removes the item at the given position.
         * @param position the position of the item to remove
         */
        public void removeItemAt(int position) {
            this.mDataset.remove(position);
            notifyDataSetChanged();
            notifyItemRangeChanged(position, this.mDataset.size());
        }
    }
}
