package com.soprasteria.movalysmdk.widget.positionmaps.delegate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.delegate.MDKPositionWidgetDelegate;
import com.soprasteria.movalysmdk.widget.position.model.Position;
import com.soprasteria.movalysmdk.widget.positionmaps.MDKMapsPosition;
import com.soprasteria.movalysmdk.widget.positionmaps.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

/**
 * Delegate for the {@link MDKMapsPositionWidgetDelegate} widget.
 */
public class MDKMapsPositionWidgetDelegate extends MDKPositionWidgetDelegate {

    /** mask for the coord option in the displayMode attribute. */
    private static int COORD_MASK = 0x000000ff;

    /** mask for the address option in the displayMode attribute. */
    private static int ADDRESS_MASK = 0x0000ff00;

    /** mask for the markerCoord option in the displayMode attribute. */
    private static int MARKER_COORD_MASK = 0x00ff0000;

    /** mask for the markerAddress option in the displayMode attribute. */
    private static int MARKER_ADDRESS_MASK = 0xff000000;

    /** tag for logging. */
    private static final String TAG = MDKMapsPositionWidgetDelegate.class.getSimpleName();

    /** constant to get the location marker in the markers array. */
    public static final int LOCATION_MARKER = 0;

    /** constant to get the address marker in the markers array. */
    public static final int ADDRESS_MARKER = 1;

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

    /** markers on map. */
    private Marker[] markers;

    /** display mode of the widget. */
    private int displayMode;

    /** the zoom level of the map. */
    private int zoom;

    /** the drawable resource to use as the location marker. */
    private int gpsMarker;

    /** the drawable resource to use as the address marker. */
    private int addressMarker;

    /** identifier of the message "text changed". */
    private static final int MESSAGE_TEXT_CHANGED = 0;

    /** delay used before value is updated. */
    private static final int UPDATE_DELAY = 200;

    /** Handler for text change cool down. */
    private Handler delayTextChangedHandler = new Handler() {
        @Override
        public void handleMessage(Message p_oMsg) {
            if (p_oMsg.what == MESSAGE_TEXT_CHANGED) {
                try {
                    double lat = Double.parseDouble(getLatitudeView().getText().toString());
                    double lng = Double.parseDouble(getLongitudeView().getText().toString());

                    position.setLatitude(lat);
                    position.setLongitude(lng);
                } catch (NumberFormatException e) {
                    Log.d(TAG, "an error occurred when parsing position");
                }
                updateShownLocation();
                validate(false, EnumFormFieldValidator.ON_USER);
            }
        }
    };

    /**
     * Constructor.
     * @param root the root view
     * @param attrs the parameters set
     */
    public MDKMapsPositionWidgetDelegate(ViewGroup root, AttributeSet attrs) {
        super(root, attrs);

        this.mode = -1;

        // Maps specific fields parsing
        TypedArray typedArray = root.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKMapsPositionComponent);

        this.displayMode = typedArray.getInt(R.styleable.MDKCommons_MDKMapsPositionComponent_displayMode, Integer.MAX_VALUE);

        this.zoom = typedArray.getInt(R.styleable.MDKCommons_MDKMapsPositionComponent_zoom, 0);
        if (this.zoom > MAX_ZOOM) {
            this.zoom = MAX_ZOOM;
        }

        this.gpsMarker = typedArray.getResourceId(R.styleable.MDKCommons_MDKMapsPositionComponent_gpsMarker, 0);

        this.addressMarker = typedArray.getResourceId(R.styleable.MDKCommons_MDKMapsPositionComponent_addressMarker, 0);

        typedArray.recycle();

        final TextView addressOnMap = (TextView) root.findViewById(R.id.component_internal_map_address);
        addressTextOnMapView = new WeakReference<>(addressOnMap);

        final TextView locationOnMap = (TextView) root.findViewById(R.id.component_internal_map_location);
        locationOnMapView = new WeakReference<>(locationOnMap);

        final RelativeLayout input = (RelativeLayout) root.findViewById(R.id.component_internal_input);
        inputZone = new WeakReference<>(input);

        final ImageButton locateButtonOnMap = (ImageButton) root.findViewById(R.id.component_internal_map_getpos);
        locateButton = new WeakReference<>(locateButtonOnMap);

        // we initialize the map
        initComponent(root);

        // we set the widget regarding its displayMode
        toggleDisplayMode(this.displayMode);

        // set the mode of the widget
        this.setMode(root, attrs);

        if (this.mode == MDKMapsPosition.PLACES) {
            this.map.setOnMapClickListener((GoogleMap.OnMapClickListener) this.valueObject.getView());
        }
    }

    /**
     * Initializes the map widget and the markers.
     * @param root the root view
     */
    private void initComponent(ViewGroup root) {
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

        // Set the markers
        markers = new Marker[2];

        setGpsMarker(this.gpsMarker);

        setAddressMarker(this.addressMarker);
    }

    /**
     * Adds a marker to the map with the given parameters.
     * @param position the position to set on the marker
     * @param bitmap the {@link BitmapDescriptor} used to display the marker
     * @param title the title of the marker
     * @return the created {@link Marker}
     */
    private Marker addMarker(Position position, BitmapDescriptor bitmap, String title) {
        return this.map.addMarker(new MarkerOptions()
                .position(getLatLng())
                .icon(bitmap)
                .title(title));
    }

    @NonNull
    private LatLng getLatLng() {
        LatLng latlng = new LatLng(0, 0);

        if (position.getLatitude() != null && position.getLongitude() != null) {
            latlng = new LatLng(position.getLatitude(), position.getLongitude());
        }
        return latlng;
    }

    /**
     * Sets the processing mode of the view based on the given attributes.
     * @param root the view being processed
     * @param attrs the attribute set
     */
    @Override
    protected void setMode(ViewGroup root, AttributeSet attrs) {
        TypedArray typedArray = root.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKMapsPositionComponent);

        if (mode == -1) {
            this.mode = typedArray.getInt(R.styleable.MDKCommons_MDKMapsPositionComponent_mapsMode, MDKMapsPosition.GPS);
        }

        if (mode == MDKMapsPosition.GPS) {
            this.autoStart = true;
        }

        typedArray.recycle();

        toggleViews(this.mode);
    }

    /**
     * Sets the processing mode of the view based on the given attributes.
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;

        if (this.mode == MDKMapsPosition.GPS) {
            this.autoStart = true;
        }

        toggleViews(this.mode);
    }

    /**
     *
     * @param dispMode
     */
    private void toggleDisplayMode(int dispMode) {
        int visible;

        if ((dispMode & this.COORD_MASK) != 0) {
            visible = View.VISIBLE;
        } else {
            visible = View.GONE;
        }
        this.addressTextOnMapView.get().setVisibility(visible);

        if ((dispMode & this.ADDRESS_MASK) != 0) {
            visible = View.VISIBLE;
        } else {
            visible = View.GONE;
        }
        this.locationOnMapView.get().setVisibility(visible);

        this.markers[LOCATION_MARKER].setVisible((dispMode & this.MARKER_COORD_MASK) != 0);

        this.markers[ADDRESS_MARKER].setVisible((dispMode & this.MARKER_ADDRESS_MASK) != 0);
    }

    @Override
    protected void toggleViews(int mode) {
        final EditText latView = latitudeView.get();
        final EditText lngView = longitudeView.get();
        RelativeLayout input = null;
        if (inputZone != null) {
            input = inputZone.get();
        }
        ImageButton locButton = null;
        if (locateButton != null) {
            locButton = locateButton.get();
        }

        switch (mode) {
            case MDKMapsPosition.PLACES:
                // the input fields for localization are hidden
                if (input != null) {
                    input.setVisibility(View.GONE);
                }
                // the locate button is hidden
                if (locButton != null) {
                    locButton.setVisibility(View.GONE);
                }
                break;
            case MDKMapsPosition.GEOPOINT:
                if (input != null) {
                    input.setVisibility(View.VISIBLE);
                }
                if (latView != null) {
                    latView.setHint(latHint);
                }
                if (lngView != null) {
                    lngView.setHint(lngHint);
                }
                if (locButton != null) {
                    locButton.setVisibility(View.VISIBLE);
                }
                break;
            case MDKMapsPosition.GPS:
                // the input fields for localization are hidden
                if (input != null) {
                    input.setVisibility(View.GONE);
                }
                // the locate button is hidden
                if (locButton != null) {
                    locButton.setVisibility(View.GONE);
                }
                break;
            default:
        }
    }

    /**
     * Updates the displayed location with the current one.
     */
    @Override
    protected void updateShownLocation() {
        if (!writingData && position != null) {
            this.mdkChangeListener.notifyListeners();

            if (position.getLatitude() != null && position.getLongitude() != null) {
                // update marker
                markers[LOCATION_MARKER].setPosition(getLatLng());

                CameraUpdate camUpdate;

                if (this.zoom != 0) {
                    camUpdate = CameraUpdateFactory.newLatLngZoom(getLatLng(), zoom);
                } else {
                    camUpdate = CameraUpdateFactory.newLatLng(getLatLng());
                }

                map.animateCamera(camUpdate);

                locationOnMapView.get().setText(Double.toString(position.getLatitude()) + "\n" + Double.toString(position.getLongitude()));

                Geocoder geocoder = new Geocoder(this.getContext(), Locale.getDefault());
                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocation(position.getLatitude(), position.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses.size() > 0) {
                    // update text
                    addressTextOnMapView.get().setText(addresses.get(0).getAddressLine(0) + "\n" + addresses.get(0).getPostalCode() + " " + addresses.get(0).getLocality());

                    // update marker
                    LatLng latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    markers[ADDRESS_MARKER].setPosition(latLng);
                }
            }
        }
    }

    @Override
    public void setLocation(Context context, Location location, boolean isAccurate) {
        this.writingData = true;

        if (location != null) {
            this.position.setPositionFromLocation(location);
        }

        this.writingData = false;

        updateShownLocation();
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
     * Sets the display mode of the widget.
     * @param displayMode the mode to set
     */
    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
        // we set the widget regarding its displayMode
        toggleDisplayMode(this.displayMode);
    }

    /**
     * Sets the gps marker of the widget.
     * @param gpsMarker the marker to set
     */
    public void setGpsMarker(int gpsMarker) {
        this.gpsMarker = gpsMarker;

        // marker for location
        BitmapDescriptor bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

        if (this.gpsMarker != 0) {
            bitmap = BitmapDescriptorFactory.fromResource(this.gpsMarker);
        }

        markers[LOCATION_MARKER] = addMarker(this.position, bitmap, getContext().getString(R.string.maps_marker_geo_title));
    }

    /**
     * Sets the address marker of the widget.
     * @param addressMarker the marker to set
     */
    public void setAddressMarker(int addressMarker) {
        this.addressMarker = addressMarker;

        // marker for address
        BitmapDescriptor bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);

        if (this.addressMarker != 0) {
            bitmap = BitmapDescriptorFactory.fromResource(this.addressMarker);
        }

        markers[ADDRESS_MARKER] = addMarker(this.position, bitmap, getContext().getString(R.string.maps_marker_address_title));
    }

    /**
     * Sets the zoom for map.
     * @param zoom the zoom to set
     */
    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
}
