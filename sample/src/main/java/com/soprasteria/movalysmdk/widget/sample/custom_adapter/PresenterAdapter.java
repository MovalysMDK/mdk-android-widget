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
package com.soprasteria.movalysmdk.widget.sample.custom_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.basic.MDKPresenterView;
import com.soprasteria.movalysmdk.widget.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The PresenterAdapter.
 */
public class PresenterAdapter extends BaseAdapter {
    /**
     * The array list.
     */
    private ArrayList<String> mEntries = new ArrayList<String>();
    /**
     * The layout inflater.
     */
    private LayoutInflater mLayoutInflater;
    /**
     * The context.
     */
    private Context mContext;

    /**
     * Constructor.
     *
     * @param context The context
     * @param entries The entries
     */
    public PresenterAdapter(Context context, List<String> entries) {
        this.mEntries = new ArrayList<String>(entries);
        this.mContext = context;
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

        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_list_view_item, parent, false);
            mViewHolder.mdkPresenterView = (MDKPresenterView) convertView.findViewById(R.id.rlv_name_view);
            mViewHolder.mTextView = (TextView) convertView.findViewById(R.id.tv_name_holder);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        String item = getItem(position);

        if (item != null) {
            mViewHolder.mTextView.setText(item);
            mViewHolder.mdkPresenterView.setTitle(item.substring(0, 1).toUpperCase());
        }

        return convertView;
    }

    /**
     * The view holder of the adapter.
     */
    private static class ViewHolder {
        private MDKPresenterView mdkPresenterView;
        private TextView mTextView;
    }
}
