package com.soprasteria.movalysmdk.widget.positionmaps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.soprasteria.movalysmdk.widget.core.helper.ActivityHelper;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentActionHandler;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentActionHelper;
import com.soprasteria.movalysmdk.widget.position.MDKPosition;
import com.soprasteria.movalysmdk.widget.core.behavior.model.Position;
import com.soprasteria.movalysmdk.widget.positionmaps.delegate.MDKMapsPositionWidgetDelegate;


/**
 * MDK Maps Position.
 * <p>Representing a map displaying the position set on the widget.</p>
 * <p>Given the mode set on the widget, the location will either be typed by the user, or gathered from the device current location.</p>
 * <p>This widget inherits from the {@link MDKPosition} widget, and has the specific XML attributes:</p>
 * <ul>
 *     <li>autoStart: the widget will start looking for the current position as soon as it is inflated (default is false)</li>
 *     <li>activeGoto: will hide the map action when set to false (default is true)</li>
 *     <li>
 *         displayMode : sets the display option on the widget
 *         <ul>
 *             <li>coord: displays the current location on the map</li>
 *             <li>address: displays the current address on the map</li>
 *             <li>markerCoord: displays a marker pointing the current location</li>
 *             <li>markerAddress: displays a marker pointing the current address</li>
 *         </ul>
 *         By default, all the options are activated.
 *     </li>
 *     <li>zoom: sets the zoom level of the map</li>
 *     <li>gpsMarker: the drawable to use as the location marker</li>
 *     <li>addressMarker: the drawable to use as the address marker</li>
 * </ul>
 */
public class MDKMapsPosition extends MDKPosition implements GoogleMap.OnMapClickListener, MDKWidgetComponentActionHandler {

    /** constant to get the location marker in the markers array. */
    public static final int LOCATION_MARKER = 0;

    /** constant to get the address marker in the markers array. */
    public static final int ADDRESS_MARKER = 1;

    /** markers on map. */
    private Marker[] markers;

    /** identifier of the message "text changed". */
    private static final int MESSAGE_TEXT_CHANGED = 0;

    /** delay used before value is updated. */
    private static final int UPDATE_DELAY = 200;

    /** Keyword instanceState. */
    private static final String UID_STATE = "uidState";

    /** Keyword innerState. */
    private static final String INNER_STATE = "innerState";

    /** Handler for text change cool down. */
    private Handler delayTextChangedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_TEXT_CHANGED) {
                updateLocationOnTextChanged();
                updateOnMapDisplay();
            }
        }
    };

    /**
     * Constructor.
     * @param context an Android context
     * @param attrs the XML attributes of the view
     */
    public MDKMapsPosition(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * @param context an Android context
     * @param attrs the XML attributes of the view
     * @param defStyleAttr the style to apply
     */
    public MDKMapsPosition(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();

        //register as handler
        MDKWidgetComponentActionHelper helper = ((MDKWidgetApplication) ((Activity)getContext()).getApplication()).getMDKWidgetComponentActionHelper();
        helper.registerActivityResultHandler(mdkWidgetDelegate.getUniqueId(), this);
    }

    @Override
    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();

        //unregister as handler
        MDKWidgetComponentActionHelper helper = ((MDKWidgetApplication) ((Activity)getContext()).getApplication()).getMDKWidgetComponentActionHelper();
        helper.unregisterActivityResultHandler(mdkWidgetDelegate.getUniqueId());
    }

    @Override
    @LayoutRes
    protected int getLayoutResource() {
        return R.layout.mdkwidget_positionmaps_layout;
    }

    /**
     * Initialize the delegates of the widget.
     * @param attrs the xml attributes of the widget
     */
    @Override
    protected void initDelegates(AttributeSet attrs) {
        this.mdkWidgetDelegate = new MDKMapsPositionWidgetDelegate(this, attrs);

        // Set the markers
        initMarkers();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).useGooglePlaces()) {
            ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getMap().setOnMapClickListener(this);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(isReadonly()) {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                ActivityHelper.startActivityForResult(getContext(), builder.build(this.getContext()), mdkWidgetDelegate.getUniqueId());
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                Log.e(this.getClass().getSimpleName(), "Google Places Error", e);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!this.writingData) {
            delayTextChangedHandler.removeMessages(MESSAGE_TEXT_CHANGED);
            final Message msg = Message.obtain(delayTextChangedHandler, MESSAGE_TEXT_CHANGED, s);
            delayTextChangedHandler.sendMessageDelayed(msg, UPDATE_DELAY);
        }
    }

    /**
     * Sets the markers on the map of the widget.
     */
    protected void initMarkers() {
        markers = new Marker[2];

        // marker for location
        BitmapDescriptor bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

        if (((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getGpsMarker() != 0) {
            bitmap = BitmapDescriptorFactory.fromResource(((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getGpsMarker());
        }

        markers[LOCATION_MARKER] = addMarker(this.getPosition(), bitmap, getContext().getString(R.string.maps_marker_geo_title));

        // marker for address
        bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);

        if (((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getAddressMarker() != 0) {
            bitmap = BitmapDescriptorFactory.fromResource(((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getAddressMarker());
        }

        markers[ADDRESS_MARKER] = addMarker(this.getPosition(), bitmap, getContext().getString(R.string.maps_marker_address_title));
    }

    /**
     * Adds a marker to the map with the given parameters.
     * @param position the position to set on the marker
     * @param bitmap the {@link BitmapDescriptor} used to display the marker
     * @param title the title of the marker
     * @return the created {@link Marker}
     */
    protected Marker addMarker(Position position, BitmapDescriptor bitmap, String title) {
        return ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getMap().addMarker(new MarkerOptions()
                .position(getLatLng())
                .icon(bitmap)
                .title(title));
    }

    /**
     * Returns a {@link LatLng} object from the widget position.
     * @return a {@link LatLng} object
     */
    @NonNull
    protected LatLng getLatLng() {
        LatLng latlng = new LatLng(0, 0);

        if (this.getPosition().getLatitude() != null && this.getPosition().getLongitude() != null) {
            latlng = new LatLng(this.getPosition().getLatitude(), this.getPosition().getLongitude());
        }
        return latlng;
    }

    /**
     * Updates on map displayed values.
     */
    protected void updateOnMapDisplay() {
        if (!this.getPosition().isNull()) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(getLatLng());
            ((MDKMapsPositionWidgetDelegate) this.mdkWidgetDelegate).getMap().animateCamera(cameraUpdate);

            if (this.hasAddresses()) {
                markers[ADDRESS_MARKER].setPosition(new LatLng(this.getPosition().getAddress().getLatitude(), this.getPosition().getAddress().getLongitude()));
            }
            markers[LOCATION_MARKER].setPosition(getLatLng());
        }
    }

    @Override
    protected void updateComponentStatus() {
        super.updateComponentStatus();

        boolean isValid = !this.getPosition().isNull();

        /* location marker */
        this.markers[LOCATION_MARKER].setVisible(
                isValid
        );

        /* address marker */
        this.markers[ADDRESS_MARKER].setVisible(
                isValid
        );
    }

    /* delegate accelerator methods */

    /**
     * Sets the mode of the widget.
     * @param mode the mode to set
     */
    @Override
    public void setMode(@PositionMode int mode) {
        this.mdkWidgetDelegate.setMode(mode);
    }

    /**
     * Sets the zoom for map.
     * @param zoom the zoom to set
     */
    public void setZoom(int zoom) {
        ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).setZoom(zoom);
    }

    /**
     * Sets the gps marker of the widget.
     * @param gpsMarker the marker to set
     */
    public void setGpsMarker(int gpsMarker) {
        ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).setGpsMarker(gpsMarker);
    }

    /**
     * Sets the address marker of the widget.
     * @param addressMarker the marker to set
     */
    public void setAddressMarker(int addressMarker) {
        ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).setAddressMarker(addressMarker);
    }

    @Override
    public void setLocation(Location location) {
        super.setLocation(location);
        updateOnMapDisplay();
    }

    /* save / restore */

    @Override
    public Parcelable onSaveInstanceState() {

        Parcelable superState = super.onSaveInstanceState();

        Bundle bundle = new Bundle();
        bundle.putParcelable(INNER_STATE, superState);
        bundle.putInt(UID_STATE, mdkWidgetDelegate.getUniqueId());

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            // Restore the uid
            this.mdkWidgetDelegate.setUniqueId(bundle.getInt(UID_STATE));

            //re-register as handler
            MDKWidgetComponentActionHelper helper = ((MDKWidgetApplication) ((Activity)getContext()).getApplication()).getMDKWidgetComponentActionHelper();
            helper.registerActivityResultHandler(mdkWidgetDelegate.getUniqueId(), this);

            super.onRestoreInstanceState(bundle.getParcelable(INNER_STATE));
        }else {
            super.onRestoreInstanceState(state);
        }

        updateOnMapDisplay();
    }

    /* handle activity results */

    @Override
    public void handleActivityResult(int resultCode, Intent data) {

        if ( resultCode == Activity.RESULT_OK) {
            Place place = PlacePicker.getPlace(data, getContext());

            Location location = new Location("Test");
            location.setLatitude(place.getLatLng().latitude);
            location.setLongitude(place.getLatLng().longitude);

            setLocation(location);
        }
    }
}
