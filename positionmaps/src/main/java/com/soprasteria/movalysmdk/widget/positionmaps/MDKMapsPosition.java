package com.soprasteria.movalysmdk.widget.positionmaps;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.soprasteria.movalysmdk.widget.core.delegate.WidgetCommandDelegate;
import com.soprasteria.movalysmdk.widget.position.MDKPosition;
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
 */
public class MDKMapsPosition extends MDKPosition implements GoogleMap.OnMapClickListener {

    private static final int PLACE_PICKER_REQUEST = 1;

    /** MDKMapsPosition mode enumeration. */
    @IntDef({PLACES, GEOPOINT, GPS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MapsPositionMode {
    }

    /** PLACES. */
    public static final int PLACES = 0;
    /** GEOPOINT. */
    public static final int GEOPOINT = 1;
    /** GPS. */
    public static final int GPS = 2;

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

        if (this.mdkWidgetDelegate.isAutoStart()) {
            this.executePositionCommand();
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
}
