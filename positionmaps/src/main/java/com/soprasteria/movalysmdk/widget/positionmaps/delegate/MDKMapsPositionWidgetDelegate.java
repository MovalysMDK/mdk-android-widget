package com.soprasteria.movalysmdk.widget.positionmaps.delegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.soprasteria.movalysmdk.widget.position.delegate.MDKPositionWidgetDelegate;
import com.soprasteria.movalysmdk.widget.positionmaps.MDKMapsPosition;
import com.soprasteria.movalysmdk.widget.positionmaps.R;

/**
 * Delegate for the {@link MDKMapsPositionWidgetDelegate} widget.
 */
public class MDKMapsPositionWidgetDelegate extends MDKPositionWidgetDelegate {

    /** the maximum zoom level. */
    public static final int MAX_ZOOM = 20;

    /** the map view. */
    private GoogleMap map;

    /** the address text on the map. */
    private int addressTextOnMapViewId;

    /** the location text on map. */
    private int locationTextOnMapViewId;

    /** display mode of the widget. */
    private int displayMode;

    /** the zoom level of the map. */
    private int zoom;

    /** the drawable resource to use as the location marker. */
    private int gpsMarker;

    /** the drawable resource to use as the address marker. */
    private int addressMarker;

    /**
     * Constructor.
     * @param root the root view
     * @param attrs the parameters set
     */
    public MDKMapsPositionWidgetDelegate(ViewGroup root, AttributeSet attrs) {
        super(root, attrs);

        // Position specific fields parsing
        TypedArray typedArray = root.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKPositionComponent);

        this.setLatitudeViewId(typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_latitudeTextViewId, 0));

        this.setLongitudeViewId(typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_longitudeTextViewId, 0));

        typedArray.recycle();

        // Maps specific fields parsing
        typedArray = root.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKMapsPositionComponent);

        this.displayMode = typedArray.getInt(R.styleable.MDKCommons_MDKMapsPositionComponent_displayMode, Integer.MAX_VALUE);

        this.zoom = typedArray.getInt(R.styleable.MDKCommons_MDKMapsPositionComponent_zoom, 0);
        if (this.zoom > MAX_ZOOM) {
            this.zoom = MAX_ZOOM;
        }

        this.gpsMarker = typedArray.getResourceId(R.styleable.MDKCommons_MDKMapsPositionComponent_gpsMarker, 0);

        this.addressMarker = typedArray.getResourceId(R.styleable.MDKCommons_MDKMapsPositionComponent_addressMarker, 0);

        this.addressTextOnMapViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKMapsPositionComponent_addressTextOnMapViewId, 0);
        this.locationTextOnMapViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKMapsPositionComponent_locationTextOnMapViewId, 0);

        // we initialize the map
        initMap(root);

        // set the mode of the widget
        this.setMode(typedArray.getInt(R.styleable.MDKCommons_MDKMapsPositionComponent_mapsMode, MDKMapsPosition.GPS));

        typedArray.recycle();

        if (this.getMode() == MDKMapsPosition.GPS) {
            this.setAutoStart(true);
        } else if (this.getMode() == MDKMapsPosition.PLACES) {
            this.map.setOnMapClickListener((GoogleMap.OnMapClickListener) this.valueObject.getView());
        }
    }

    /**
     * Initializes the map widget and the markers.
     * @param root the root view
     */
    private void initMap(ViewGroup root) {
        // initialize the map

        MapView mapView = (MapView) root.findViewById(R.id.component_internal_map);

        try {
            mapView.onCreate(null);
            mapView.onResume(); //without this, map showed but was empty

            // Gets to GoogleMap from the MapView and does initialization stuff
            map = mapView.getMap();
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.setMyLocationEnabled(true);

            // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
            MapsInitializer.initialize(root.getContext());

            // Updates the location and zoom of the MapView
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(44.14, 14.2));
            map.animateCamera(cameraUpdate);

            // disable the gestures on the map
            map.getUiSettings().setAllGesturesEnabled(false);
        } catch (RuntimeException e) {
            // this is most probably an API key problem. We notify the user
            e.printStackTrace();
            new AlertDialog.Builder(root.getContext())
                    .setTitle(root.getContext().getString(R.string.maps_api_error_title))
                    .setMessage(root.getContext().getString(R.string.maps_api_error_description))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            // TODO : the activity should stop
        }
    }

    /**
     * Returns the map view.
     * @return the map view
     */
    public GoogleMap getMap() {
        return map;
    }

    /**
     * Returns the on map address text view.
     * @return the on map address text view
     */
    public TextView getAddressTextOnMapView() {
        if (addressTextOnMapViewId != 0) {
            return (TextView) reverseFindViewById(addressTextOnMapViewId);
        }
        return null;
    }

    /**
     * Returns the on map coordinates text view.
     * @return the on map coordinates text view
     */
    public TextView getLocationOnMapView() {
        if (locationTextOnMapViewId != 0) {
            return (TextView) reverseFindViewById(locationTextOnMapViewId);
        }
        return null;
    }

    @Override
    public EditText getLatitudeView() {
        if (this.getLatitudeViewId() != 0) {
            return (EditText) reverseFindViewById(this.getLatitudeViewId());
        }
        return null;
    }

    @Override
    public EditText getLongitudeView() {
        if (this.getLongitudeViewId() != 0) {
            return (EditText) reverseFindViewById(this.getLongitudeViewId());
        }
        return null;
    }

//    /**
//     * Returns the on map locate button.
//     * @return the on map locate button
//     */
//    @Override
//    public View getLocateButton() {
////        if (this.locateButton != null) {
////            return this.locateButton.get();
////        }
////        return null;
//        return reverseFindViewById(R.id.component_internal_map_getpos);
//    }

    /**
     * Returns the on map display mode.
     * @return the on map display mode
     */
    public int getDisplayMode() {
        return this.displayMode;
    }

    /**
     * Sets the display mode of the widget.
     * @param displayMode the mode to set
     */
    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
    }

    /**
     * Returns the defined GPS marker drawable.
     * @return the GPS marker drawable
     */
    public int getGpsMarker() {
        return this.gpsMarker;
    }

    /**
     * Sets the gps marker of the widget.
     * @param gpsMarker the marker to set
     */
    public void setGpsMarker(int gpsMarker) {
        this.gpsMarker = gpsMarker;
    }

    /**
     * Returns the defined address marker drawable.
     * @return the address marker drawable
     */
    public int getAddressMarker() {
        return this.addressMarker;
    }

    /**
     * Sets the address marker of the widget.
     * @param addressMarker the marker to set
     */
    public void setAddressMarker(int addressMarker) {
        this.addressMarker = addressMarker;
    }

    /**
     * Sets the zoom for map.
     * @param zoom the zoom to set
     */
    public void setZoom(int zoom) {
        this.zoom = zoom;
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(zoom);
        map.animateCamera(cameraUpdate);
    }
}
