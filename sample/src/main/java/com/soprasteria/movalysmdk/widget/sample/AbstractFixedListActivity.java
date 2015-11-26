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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract test activity for the MDKRichFixedList widget.
 */
public abstract class AbstractFixedListActivity extends AbstractWidgetTestableActivity {

    /** request code for the item clicked event. */
    protected static final int RC_CODE = 0x8000;

    /** the displayed dataset. */
    // TODO save data on rotate
    protected String[] myDataset = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Android", "iPhone", "WindowsMobile" };

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
         * Updates the dataset item on the given position.
         * @param position the position of the item to update
         * @param newValue the value to set on the item
         */
        public void updateItemAt(int position, String newValue) {
            mDataset.set(position, newValue);
            notifyDataSetChanged();
        }

        /**
         * Returns the value of the item at the given position.
         * @param position the position of the item to return
         * @return the value of the found item
         */
        public String getItemAt(int position) {
            return mDataset.get(position);
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

        /**
         * Adds an item to the dataset.
         * @param item the value of the item to add
         */
        public void addItem(String item) {
            mDataset.add(item);
            notifyDataSetChanged();
        }
    }

}
