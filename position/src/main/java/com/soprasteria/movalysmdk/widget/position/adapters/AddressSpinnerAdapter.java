package com.soprasteria.movalysmdk.widget.position.adapters;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.position.R;

import java.util.List;

/**
 * adapter class for the address spinner selector of the position widget.
 */
public class AddressSpinnerAdapter extends ArrayAdapter<Address> {

    /** the layout of the items. */
    private int layoutResourceId;

    /**
     * Constructor.
     * @param context an Android context
     * @param layoutResourceId the layout resource to use for inflate
     * @param objects the array of data to store
     */
    public AddressSpinnerAdapter(Context context, int layoutResourceId, List<Address> objects) {
        super(context, layoutResourceId, objects);

        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    /**
     * Returns the view for the given position.
     * @param position the position of the view
     * @param convertView the existing view
     * @param parent the parent of the view
     * @return the inflated view
     */
    private View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            view = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.street = (TextView) view.findViewById(R.id.component_internal_address_street);
            holder.city = (TextView) view.findViewById(R.id.component_internal_address_city);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Address addr = this.getItem(position);

        if (addr != null) {
            String street = addr.getAddressLine(0);
            if (street == null) {
                street = "";
            }

            holder.street.setText(street);

            String city = "";
            if (addr.getPostalCode() != null) {
                city = addr.getPostalCode();
            }
            if (addr.getLocality() != null) {
                if (city.length() > 0) {
                    city += " ";
                }
                city += addr.getLocality();
            }

            holder.city.setText(city);
        }

        return view;
    }

    /**
     * Adapter view holder.
     */
    static class ViewHolder {
        /** The street TextView. */
        TextView street;

        /** The city TextView. */
        TextView city;
    }
}
