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
package com.soprasteria.movalysmdk.widget.media;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * Adapter for the brush size spinner.
 */
public class BrushSizeAdapter implements SpinnerAdapter {

    /** Context of the adapter. **/
    private Context context;

    /**
     * Constructor.
     * @param ctx the parent activity context.
     */
    public BrushSizeAdapter(Context ctx){
        this.context = ctx;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // Nothing to do.
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // Nothing to do.
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return (int)Math.pow(2,position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View oCurrentView = convertView;
        if(oCurrentView==null) {
            oCurrentView = LayoutInflater.from(context).inflate(R.layout.brush_size_adapter_layout, null);
        }

        View v = oCurrentView.findViewById(R.id.brush_preview);
        TextView tv = (TextView) oCurrentView.findViewById(R.id.brush_size_text);

        v.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) Math.pow(2,position),context.getResources().getDisplayMetrics());
        tv.setText(String.format(context.getString(R.string.mdkwidget_mdkmedia_brush_pt), (int)Math.pow(2,position)));

        return oCurrentView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View oCurrentView = convertView;
        if(oCurrentView==null) {
            oCurrentView = LayoutInflater.from(context).inflate(R.layout.brush_size_dropdown_adapter_layout, null);
        }

        View v = oCurrentView.findViewById(R.id.brush_preview);
        TextView tv = (TextView) oCurrentView.findViewById(R.id.brush_size_text);

        v.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) Math.pow(2,position),context.getResources().getDisplayMetrics());
        tv.setText(String.format(context.getString(R.string.mdkwidget_mdkmedia_brush_pt), (int)Math.pow(2,position)));

        return oCurrentView;
    }
}
