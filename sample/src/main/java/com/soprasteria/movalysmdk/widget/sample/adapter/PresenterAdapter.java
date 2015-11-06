/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 * <p/>
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
package com.soprasteria.movalysmdk.widget.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.basic.MDKPresenterView;
import com.soprasteria.movalysmdk.widget.basic.model.MDKPresenter;
import com.soprasteria.movalysmdk.widget.sample.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * The PresenterAdapter.
 */
public class PresenterAdapter extends BaseAdapter {
    /**
     * The array list.
     */
    private List<String> mEntries = new ArrayList();
    /**
     * The layout inflater.
     */
    private LayoutInflater mLayoutInflater;

    /**
     * Constructor.
     *
     * @param context The context
     * @param entries The entries
     */
    public PresenterAdapter(Context context, List<String> entries) {
        this.mEntries = new ArrayList(entries);
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.mEntries.size();
    }

    @Override
    public String getItem(int position) {
        return this.mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        View v;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            v = mLayoutInflater.inflate(R.layout.layout_list_view_item, parent, false);
            mViewHolder.mdkPresenterView = (MDKPresenterView) v.findViewById(R.id.rlv_name_view);
            mViewHolder.mTextView = (TextView) v.findViewById(R.id.tv_name_holder);
            v.setTag(mViewHolder);
        } else {
            v = convertView;
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        String item = getItem(position);

        if (item != null) {
            URI uri = null;
            mViewHolder.mTextView.setText(item);
            MDKPresenter mdkPresenter = new MDKPresenter(item, uri, true);
            mViewHolder.mdkPresenterView.setPresenter(mdkPresenter);
        }

        return v;
    }

    /**
     * The view holder of the adapter.
     */
    private static class ViewHolder {

        /**
         * The MDKPresenterView.
         */
        private MDKPresenterView mdkPresenterView;
        /**
         * the textView.
         */
        private TextView mTextView;
    }
}
