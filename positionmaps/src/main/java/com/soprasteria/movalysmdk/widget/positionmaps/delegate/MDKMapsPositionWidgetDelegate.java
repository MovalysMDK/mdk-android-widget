package com.soprasteria.movalysmdk.widget.positionmaps.delegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.delegate.MDKPositionWidgetDelegate;
import com.soprasteria.movalysmdk.widget.positionmaps.MDKMapsPosition;
import com.soprasteria.movalysmdk.widget.positionmaps.R;

import java.lang.ref.WeakReference;

/**
 * Delegate for the {@link MDKMapsPositionWidgetDelegate} widget.
 */
public class MDKMapsPositionWidgetDelegate extends MDKPositionWidgetDelegate {

    /** the maximum zoom level. */
    public static final int MAX_ZOOM = 20;

    /** the map view. */
    private GoogleMap map;

    /** the address text on the map. */
    private WeakReference<TextView> addressTextOnMapView;

    /** the location text on map. */
    private WeakReference<TextView> locationOnMapView;

    /** the layout with the input widgets. */
    private WeakReference<RelativeLayout> inputZone;

    /** the locate button on map. */
    private WeakReference<ImageButton> locateButton;

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

        this.setMode(-1);

        // Maps specific fields parsing
        TypedArray typedArray = root.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKMapsPositionComponent);

        this.displayMode = typedArray.getInt(R.styleable.MDKCommons_MDKMapsPositionComponent_displayMode, Integer.MAX_VALUE);

        this.zoom = typedArray.getInt(R.styleable.MDKCommons_MDKMapsPositionComponent_zoom, 0);
        if (this.zoom > MAX_ZOOM) {
            this.zoom = MAX_ZOOM;
        }

        this.gpsMarker = typedArray.getResourceId(R.styleable.MDKCommons_MDKMapsPositionComponent_gpsMarker, 0);

        this.addressMarker = typedArray.getResourceId(R.styleable.MDKCommons_MDKMapsPositionComponent_addressMarker, 0);

        final TextView addressOnMap = (TextView) root.findViewById(R.id.component_internal_map_address);
        addressTextOnMapView = new WeakReference<>(addressOnMap);

        final TextView locationOnMap = (TextView) root.findViewById(R.id.component_internal_map_location);
        locationOnMapView = new WeakReference<>(locationOnMap);

        final RelativeLayout input = (RelativeLayout) root.findViewById(R.id.component_internal_input);
        inputZone = new WeakReference<>(input);

        final ImageButton locateButtonOnMap = (ImageButton) root.findViewById(R.id.component_internal_map_getpos);
        locateButton = new WeakReference<>(locateButtonOnMap);

        // we initialize the map
        initMap(root);

        // set the mode of the widget
        if (this.getMode() == -1) {
            this.setMode(typedArray.getInt(R.styleable.MDKCommons_MDKMapsPositionComponent_mapsMode, MDKMapsPosition.GPS));
        }

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

    public GoogleMap getMap() {
        return map;
    }

    public TextView getAddressTextOnMapView() {
        if (addressTextOnMapView != null) {
            return this.addressTextOnMapView.get();
        }
        return null;
    }

    public TextView getLocationOnMapView() {
        if (locationOnMapView != null) {
            return this.locationOnMapView.get();
        }
        return null;
    }

    private RelativeLayout getInputZone() {
        if (this.inputZone != null) {
            return this.inputZone.get();
        }
        return null;
    }

    private ImageButton locateButton() {
        if (this.locateButton != null) {
            return this.locateButton.get();
        }
        return null;
    }

    /**
     * Sets the processing mode of the view based on the given attributes.
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        super.setMode(mode);

        if (this.getMode() == MDKMapsPosition.GPS) {
            this.setAutoStart(true);
        }
    }

    /**
     * Sets the display mode of the widget.
     * @param displayMode the mode to set
     */
    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
    }

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
    }
}
