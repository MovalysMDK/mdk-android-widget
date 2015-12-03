package com.soprasteria.movalysmdk.widget.positionmaps.delegate;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.soprasteria.movalysmdk.widget.position.delegate.MDKPositionWidgetDelegate;
import com.soprasteria.movalysmdk.widget.positionmaps.R;

import java.lang.ref.WeakReference;

/**
 * Delegate for the {@link MDKMapsPositionWidgetDelegate} widget.
 */
public class MDKMapsPositionWidgetDelegate extends MDKPositionWidgetDelegate {

    /** the maximum zoom level. */
    public static final int MAX_ZOOM = 20;

    /** default zoom value, the higher the closer. */
    public static final int DEFAULT_ZOOM = 15;

    /** the map view. */
    private GoogleMap map;

    /** true if a click on the map should launch the Google Places app. */
    private boolean useGooglePlaces;

    /** the zoom level of the map. */
    private int zoom;

    /** the drawable resource to use as the location marker. */
    private int gpsMarker;

    /** the drawable resource to use as the address marker. */
    private int addressMarker;

    /** weak reference to the map view. */
    private WeakReference<View> weakMapView;

    /**
     * Constructor.
     * @param root the root view
     * @param attrs the parameters set
     */
    public MDKMapsPositionWidgetDelegate(ViewGroup root, AttributeSet attrs) {
        super(root, attrs);

        // Maps specific fields parsing
        TypedArray typedArray = root.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKMapsPositionComponent);

        this.useGooglePlaces = typedArray.getBoolean(R.styleable.MDKCommons_MDKMapsPositionComponent_usePicker, false);

        this.zoom = typedArray.getInt(R.styleable.MDKCommons_MDKMapsPositionComponent_zoom, DEFAULT_ZOOM);
        if (this.zoom > MAX_ZOOM) {
            this.zoom = MAX_ZOOM;
        }

        this.gpsMarker = typedArray.getResourceId(R.styleable.MDKCommons_MDKMapsPositionComponent_gpsMarker, 0);

        this.addressMarker = typedArray.getResourceId(R.styleable.MDKCommons_MDKMapsPositionComponent_addressMarker, 0);

        // we initialize the map
        View mapView = root.findViewById(R.id.component_internal_map);

        if (mapView instanceof MapView) {
            initMap(root, (MapView) mapView);
        }

        this.weakMapView = new WeakReference<>(mapView);

        typedArray.recycle();
    }

    /**
     * Initializes the map widget and the markers.
     * @param root the root view
     * @param mapView the map view to initialize
     */
    private void initMap(ViewGroup root, MapView mapView) {
        try {
            mapView.onCreate(null);
            //without this, map showed but was empty
            mapView.onResume();

            // Gets to GoogleMap from the MapView and does initialization stuff
            map = mapView.getMap();
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.setMyLocationEnabled(true);

            // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
            MapsInitializer.initialize(root.getContext());

            // Updates the location and zoom of the MapView
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(44.14, 14.2), this.zoom);
            map.animateCamera(cameraUpdate);

            // disable the gestures on the map
            map.getUiSettings().setAllGesturesEnabled(false);

            // optimization
            mapView.setPersistentDrawingCache(MapView.PERSISTENT_ALL_CACHES);
        } catch (RuntimeException e) {
            // this is most probably an API key problem. We notify the user
            Log.e(this.getClass().getSimpleName(), root.getContext().getString(R.string.maps_api_error_title), e);
            new AlertDialog.Builder(root.getContext())
                    .setTitle(root.getContext().getString(R.string.maps_api_error_title))
                    .setMessage(root.getContext().getString(R.string.maps_api_error_description))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            root.removeView(mapView);
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
     * Returns true if Google Places is used for picking the location.
     * @return true if Google Places is used
     */
    public boolean useGooglePlaces() {
        return useGooglePlaces;
    }

    /**
     * Sets whether Google Places should be used to pick the location.
     * @param useGooglePlaces true to use Google Places
     */
    public void setUseGooglePlaces(boolean useGooglePlaces) {
        this.useGooglePlaces = useGooglePlaces;
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
     * Get the current zoom.
     * @return an integer representing the zoom
     */
    public int getZoom() {
        return zoom;
    }

    /**
     * Sets the zoom for map.
     * @param zoom the zoom to set
     */
    public void setZoom(int zoom) {
        if(map!=null) {
            this.zoom = zoom;
            CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(zoom);
            map.animateCamera(cameraUpdate);
        }
    }

    /**
     * Returns the map view.
     * @return the map view
     */
    public View getMapView() {
        return this.weakMapView.get();
    }
}
