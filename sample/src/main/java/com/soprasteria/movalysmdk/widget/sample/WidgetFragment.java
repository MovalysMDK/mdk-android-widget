package com.soprasteria.movalysmdk.widget.sample;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class WidgetFragment extends Fragment implements AbsListView.OnItemClickListener {

    /**
     * Log manager and formatter.
     */
    private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName() );

    /**
     * Listener on fragment.
     */
    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WidgetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageManager pm =  this.getActivity().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(this.getActivity().getPackageName(), PackageManager.GET_ACTIVITIES);
            for (ActivityInfo ai: pi.activities) {

                // load all class but main activity
                if (!ai.name.equals(ListWidgetActivity.class.getName())) {
                    WidgetContent.getITEMS().add(new WidgetContent.WidgetItem(ai.loadLabel(pm).toString(), (Class<? extends Activity>) Class.forName(ai.name)));
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("context", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("context", e);
        }

        mAdapter = new ArrayAdapter<WidgetContent.WidgetItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, WidgetContent.getITEMS());
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            LOGGER.info(activity.toString()
                    + " must implement OnFragmentInteractionListener");
            throw e;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(WidgetContent.getITEMS().get(position).getActivityClass());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        /**
         * Constructor.
         * @param activityToLaunch the activity to launch
         */
        public void onFragmentInteraction(Class<? extends Activity> activityToLaunch);
    }

}
