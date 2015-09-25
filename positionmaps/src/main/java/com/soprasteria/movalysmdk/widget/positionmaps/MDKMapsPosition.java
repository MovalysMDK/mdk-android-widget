package com.soprasteria.movalysmdk.widget.positionmaps;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.soprasteria.movalysmdk.widget.core.delegate.WidgetCommandDelegate;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.MDKPosition;
import com.soprasteria.movalysmdk.widget.position.model.Position;
import com.soprasteria.movalysmdk.widget.positionmaps.delegate.MDKMapsPositionWidgetDelegate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
 *
 * TODO
 * appliquer les masks au displayMode
 * appliquer le mapsMode
 */
public class MDKMapsPosition extends MDKPosition implements GoogleMap.OnMapClickListener {

    /** mask for the coord option in the displayMode attribute. */
    public static int COORD_MASK = 0x000000ff;

    /** mask for the address option in the displayMode attribute. */
    public static int ADDRESS_MASK = 0x0000ff00;

    /** mask for the markerCoord option in the displayMode attribute. */
    public static int MARKER_COORD_MASK = 0x00ff0000;

    /** mask for the markerAddress option in the displayMode attribute. */
    public static int MARKER_ADDRESS_MASK = 0xff000000;

    private static final int PLACE_PICKER_REQUEST = 1;

    /** constant to get the location marker in the markers array. */
    public static final int LOCATION_MARKER = 0;

    /** constant to get the address marker in the markers array. */
    public static final int ADDRESS_MARKER = 1;

    /** MDKMapsPosition mode enumeration. */
    @IntDef({PLACES, GEOPOINT, GPS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MapsPositionMode {}

    /** PLACES. */
    public static final int PLACES = 0;

    /** GEOPOINT. */
    public static final int GEOPOINT = 1;

    /** GPS. */
    public static final int GPS = 2;

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
                try {
                    // TODO : a revoir
                    double lat = Double.parseDouble(mdkWidgetDelegate.getLatitudeView().getText().toString());
                    double lng = Double.parseDouble(mdkWidgetDelegate.getLongitudeView().getText().toString());

                    getPosition().setLatitude(lat);
                    getPosition().setLongitude(lng);
                } catch (NumberFormatException e) {
                    // TODO a faire mieux
                    e.printStackTrace();
                }

                validate(EnumFormFieldValidator.ON_USER);
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

        this.commandDelegate = new WidgetCommandDelegate(this, attrs);

        // we register the on map locate button as an action
        this.commandDelegate.manuallyAddCommand(WidgetCommandDelegate.FIRST_COMMAND, R.id.component_internal_map_getpos, false, false);

        // Set the markers
        initMarkers();

        toggleDisplayMode();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.mdkWidgetDelegate.isAutoStart()) {
            super.startAcquisition(null);
        }
    }

    /**
     *
     */
    private void toggleDisplayMode() {
        int visible;

        if ((((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getMode() & this.COORD_MASK) != 0) {
            visible = View.VISIBLE;
        } else {
            visible = View.GONE;
        }
        ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getAddressTextOnMapView().setVisibility(visible);

        if ((((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getMode() & this.ADDRESS_MASK) != 0) {
            visible = View.VISIBLE;
        } else {
            visible = View.GONE;
        }
        ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getLocationOnMapView().setVisibility(visible);

        this.markers[LOCATION_MARKER].setVisible((((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getMode() & this.MARKER_COORD_MASK) != 0);

        this.markers[ADDRESS_MARKER].setVisible((((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).getMode() & this.MARKER_ADDRESS_MASK) != 0);
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

    /**
     * Sets the mode of the widget.
     * @param mode the mode to set
     */
    @Override
    public void setMode(@MapsPositionMode int mode) {
        ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).setMode(mode);
    }

    /**
     * Sets the display mode of the widget.
     * @param displayMode the mode to set
     */
    public void setDisplayMode(int displayMode) {
        ((MDKMapsPositionWidgetDelegate)this.mdkWidgetDelegate).setDisplayMode(displayMode);
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
}
