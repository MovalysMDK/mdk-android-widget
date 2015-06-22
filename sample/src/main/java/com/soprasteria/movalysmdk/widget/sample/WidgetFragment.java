package com.soprasteria.movalysmdk.widget.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.soprasteria.movalysmdk.widget.sample.content.WidgetContent;

import java.util.logging.Logger;

/**
 * A fragment representing a list of Items.
 * <p>Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.</p>
 */
public class WidgetFragment extends Fragment implements AbsListView.OnItemClickListener {

    /**
     * Log manager and formatter.
     */
    private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName() );

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApp myApp = (MyApp) this.getActivity().getApplicationContext();

        mAdapter = new ArrayAdapter<WidgetContent.WidgetItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, myApp.getWidgetContent().getItems());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_widget, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyApp myApp = (MyApp) this.getActivity().getApplicationContext();
        Class<? extends Activity> activityClass = myApp.getWidgetContent().getItems().get(position).getActivityClass();
        this.startActivity(new Intent(this.getActivity(), activityClass));
    }
}
