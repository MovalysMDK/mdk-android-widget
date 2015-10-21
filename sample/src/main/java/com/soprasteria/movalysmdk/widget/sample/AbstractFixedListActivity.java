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

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.basic.MDKRichEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract test activity for the MDKRichFixedList widget.
 */
public abstract class AbstractFixedListActivity extends AbstractWidgetTestableActivity {

    /** Tag for debugging. */
    protected static final String FIXEDACTIVITY = "FIXEDACTIVITY";

    /** mask for the request code. */
    public static final int RC_MASK = 0xC000;

    /** mask for the position of the clicked item in the request code. */
    public static final int POS_MASK = 0x3FFF;

    /** request code for the item clicked event. */
    protected static final int RC_CODE = 0x8000;

    /** the displayed dataset. */
    protected String[] myDataset = new String[] {};

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(FIXEDACTIVITY, "REQ :" + requestCode);

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

    /**
     * Shows a dialog to input data in the adapter.
     * @param adapter the adapter on which data should be modified / added
     * @param position the position of the data to input, -1 to add a new cell
     */
    protected void showInputDialog(final MyAdapter adapter, final int position) {
        String value = "";
        if (position > -1) {
            value = adapter.getItemAt(position);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(this.getApplicationContext());
        View promptView = layoutInflater.inflate(R.layout.dialog_staggered_fixed_list, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        alertDialogBuilder.setView(promptView);

        final MDKRichEditText editText = (MDKRichEditText) promptView.findViewById(R.id.item_value);

        editText.setText(value);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(getApplication().getText(R.string.fixedlist_addDialog_validate), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (position == -1) {
                            adapter.addItem(editText.getText().toString());
                        } else {
                            adapter.updateItemAt(position, editText.getText().toString());
                        }
                    }
                })
                .setNegativeButton(getApplication().getText(R.string.fixedlist_addDialog_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

}
