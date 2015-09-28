package com.soprasteria.movalysmdk.widget.positionmaps;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Spinner;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.MDKPosition;
import com.soprasteria.movalysmdk.widget.position.model.Position;
import com.soprasteria.movalysmdk.widget.positionmaps.delegate.MDKMapsPositionWidgetDelegate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * MDK Maps Position.
 * <p>Representing a map displaying the position set on the widget.</p>
 * <p>Given the mode set on the widget, the location will either be typed by the user, or gathered from the device current location.</p>
 * <p>This widget inherits from the {@link MDKPosition} widget, and has the specific XML attributes:</p>
 * <ul>
 *     <li>
 *         mapsMode: sets one the the following modes on the widget
 *         <ul>
 *             <li>places_picker: the position will be picked using the Android Places API</li>
 *             <li>geopoint: the position will be shown as a latitude and a longitude</li>
 *             <li>gps: the position will be gathered from the device's location (default option)</li>
 *         </ul>
 *     </li>
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
public class MDKMapsPosition extends MDKPosition implements GoogleMap.OnMapClickListener {

    /** mask for the coord option in the displayMode attribute. */
    public static final int COORD_MASK = 0x000000ff;

    /** mask for the address option in the displayMode attribute. */
    public static final int ADDRESS_MASK = 0x0000ff00;

    /** mask for the markerCoord option in the displayMode attribute. */
    public static final int MARKER_COORD_MASK = 0x00ff0000;

    /** mask for the markerAddress option in the displayMode attribute. */
    public static final int MARKER_ADDRESS_MASK = 0xff000000;

    /** request code for the PlacePicker ActivityResult. */
    private static final int PLACE_PICKER_REQUEST = 1;

    /** constant to get the location marker in the markers array. */
    public static final int LOCATION_MARKER = 0;

    /** constant to get the address marker in the markers array. */
    public static final int ADDRESS_MARKER = 1;

    /** MDKMapsPosition mode enumeration. */
    @IntDef({PLACES, GEOPOINT, GPS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MapsPositionMode {}

    /** GEOPOINT. */
    public static final int GEOPOINT = 0;

    /** GPS. */
    public static final int GPS = 1;

    /** PLACES. */
    public static final int PLACES = 2;

    /** markers on map. */
    private Marker[] markers;

    /** identifier of the message "text changed". */
    private static final int MESSAGE_TEXT_CHANGED = 0;

    /** delay used before value is updated. */
    private static final int UPDATE_DELAY = 200;

    /** Handler for text change cool down. */
    private Handler delayTextChangedHandler = new Handler() {
        @Override
        public void handleMessage(Message p_oMsg) {
            if (p_oMsg.what == MESSAGE_TEXT_CHANGED) {
                Double lat = null;
                Double lng = null;

                try {
                    lat = Double.parseDouble(mdkWidgetDelegate.getLatitudeView().getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    lng = Double.parseDouble(mdkWidgetDelegate.getLongitudeView().getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                getPosition().setLatitude(lat);
                getPosition().setLongitude(lng);

                validate(EnumFormFieldValidator.ON_USER);

                updateOnMapDisplay();
                updateComponent();
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
        if (this.mdkWidgetDelegate.isAutoStart()) {
            super.startAcquisition();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        Activity host = (Activity) getContext();

        try {
            host.startActivityForResult(builder.build(this.getContext()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the request identifier for the PlacePicker action.
     * @return the request identifier
     */
    public int getPlacePickerRequest() {
        return PLACE_PICKER_REQUEST;
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
     * Clears the widget content.
     */
    @Override
    public void clear() {
        this.setLocation(null);

        updateComponent();
    }

    /**
     * Sets the markers on the map of the widget.
     */
    public void initMarkers() {
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
    private Marker addMarker(Position position, BitmapDescriptor bitmap, String title) {
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
    private LatLng getLatLng() {
        LatLng latlng = new LatLng(0, 0);

        if (this.getPosition().getLatitude() != null && this.getPosition().getLongitude() != null) {
            latlng = new LatLng(this.getPosition().getLatitude(), this.getPosition().getLongitude());
        }
        return latlng;
    }

    /**
     * Updates on map displayed values.
     */
    private void updateOnMapDisplay() {
        if (!this.getPosition().isNull()) {
            // this occurs when the fix is done, ie we have a precise location
            List<Address> addresses = getAddresses(this.getLocation(), false);

            if (addresses != null && addresses.size() > 0) {
                if (((MDKMapsPositionWidgetDelegate) this.mdkWidgetDelegate).getAddressTextOnMapView() != null) {
                    String address = addresses.get(0).getAddressLine(0);

                    if (address == null) {
                        address = "";
                    }

                    String city = "";
                    if (addresses.get(0).getPostalCode() != null) {
                        city = addresses.get(0).getPostalCode();
                    }
                    if (addresses.get(0).getLocality() != null) {
                        if (city.length() > 0) {
                            city += " ";
                        }
                        city += addresses.get(0).getLocality();
                    }

                    if (address.length() > 0) {
                        address += "\n";
                    }
                    address += city;

                    ((MDKMapsPositionWidgetDelegate) this.mdkWidgetDelegate).getAddressTextOnMapView().setText(address);
                }
                markers[ADDRESS_MARKER].setPosition(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()));
            }

            if (((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getLocationOnMapView() != null) {
                String location = String.valueOf(this.getPosition().getLatitude()) + " " + String.valueOf(this.getPosition().getLongitude());
                ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getLocationOnMapView().setText(location);
            }

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(getLatLng());
            ((MDKMapsPositionWidgetDelegate) this.mdkWidgetDelegate).getMap().animateCamera(cameraUpdate);

            markers[LOCATION_MARKER].setPosition(getLatLng());

        }
    }

    @Override
    protected void updateComponent() {
        int mode = this.mdkWidgetDelegate.getMode();
        boolean isValid = !this.getPosition().isNull();

        /* latitude input fields */
        if (this.mdkWidgetDelegate.getLatitudeView() != null) {
            this.mdkWidgetDelegate.getLatitudeView().setVisibility(
                    mode == this.GEOPOINT
                            ? View.VISIBLE : View.GONE
            );
        }

        /* longitude input fields */
        if (this.mdkWidgetDelegate.getLongitudeView() != null) {
            this.mdkWidgetDelegate.getLongitudeView().setVisibility(
                    mode == this.GEOPOINT
                            ? View.VISIBLE : View.GONE
            );
        }

        /* clear button */
        if (this.mdkWidgetDelegate.getClearButton() != null) {
            this.mdkWidgetDelegate.getClearButton().setVisibility(
                    mode == this.GEOPOINT
                            ? View.VISIBLE : View.GONE
            );
        }

        /* on map locate button */
        if (this.mdkWidgetDelegate.getLocateButton() != null) {
            this.mdkWidgetDelegate.getLocateButton().setVisibility(
                    mode == this.GEOPOINT
                            ? View.VISIBLE : View.GONE
            );
        }

        /* on map address */
        if (((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getAddressTextOnMapView() != null) {
            ((MDKMapsPositionWidgetDelegate) this.mdkWidgetDelegate).getAddressTextOnMapView().setVisibility(
                    (((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getDisplayMode() & MDKMapsPosition.COORD_MASK) != 0 && isValid
                    ? View.VISIBLE : View.GONE
            );
        }

        /* on map location */
        if (((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getLocationOnMapView() != null) {
            ((MDKMapsPositionWidgetDelegate) this.mdkWidgetDelegate).getLocationOnMapView().setVisibility(
                    (((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getDisplayMode() & MDKMapsPosition.ADDRESS_MASK) != 0 && isValid
                            ? View.VISIBLE : View.GONE
            );
        }

        /* location marker */
        this.markers[LOCATION_MARKER].setVisible(
                (((MDKMapsPositionWidgetDelegate) this.mdkWidgetDelegate).getDisplayMode() & MDKMapsPosition.MARKER_COORD_MASK) != 0 && isValid
        );

        /* address marker */
        this.markers[ADDRESS_MARKER].setVisible(
                (((MDKMapsPositionWidgetDelegate) this.mdkWidgetDelegate).getDisplayMode() & MDKMapsPosition.MARKER_ADDRESS_MASK) != 0 && isValid
        );
    }

    /* delegate accelerator methods */

    /**
     * Sets the mode of the widget.
     * @param mode the mode to set
     */
    @Override
    public void setMode(@MapsPositionMode int mode) {
        this.mdkWidgetDelegate.setMode(mode);
    }

    /**
     * Sets the display mode of the widget.
     * @param displayMode the mode to set
     */
    public void setDisplayMode(int displayMode) {
        ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).setDisplayMode(displayMode);
        updateComponent();
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
}
