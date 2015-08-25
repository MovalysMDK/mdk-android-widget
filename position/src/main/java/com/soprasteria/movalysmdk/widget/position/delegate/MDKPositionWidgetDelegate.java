package com.soprasteria.movalysmdk.widget.position.delegate;

import android.content.res.TypedArray;
import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.soprasteria.movalysmdk.widget.basic.MDKEditText;
import com.soprasteria.movalysmdk.widget.core.MDKBaseWidget;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.R;

import java.lang.ref.WeakReference;

/**
 * Delegate for the MDKPositionWidgetDelegate widget.
 * This delegate implements the core concepts of the component : it handles the fact that the MDKPosition
 * widget can be used alone, or with the addition of an other TextView.
 * In this later case the MDKPosition will act
 * as the master component (as it hosts this delegate), and the TextView will act as the slave component.
 */
public class MDKPositionWidgetDelegate extends MDKWidgetDelegate implements MDKBaseWidget, TextWatcher {

    /** tag for logging. */
    private static final String TAG = MDKPositionWidgetDelegate.class.getSimpleName();

    /** notify change listeners. */
    private MDKChangeListenerDelegate mdkChangeListener;

    /** Latitude hint.*/
    private String latHint;

    /** Longitude hint.*/
    private String lngHint;

    /** latitude view identifier. */
    private int latitudeViewId;

    /** longitude view identifier. */
    private int longitudeViewId;

    /** latitude view. */
    private WeakReference<MDKEditText> latitudeView;

    /** longitude view. */
    private WeakReference<MDKEditText> longitudeView;

    /** current location. */
    private Location location;

    /** true if the data is being written by the command. */
    private boolean writingData = false;

    /**
     * Constructor.
     * @param view  the view
     * @param attrs the parameters set
     */
    public MDKPositionWidgetDelegate(View view, AttributeSet attrs) {
        super(view, attrs);

        this.mdkChangeListener = new MDKChangeListenerDelegate();

        /// initalise the location object
        location = new Location("dummyprovider");

        // Position specific fields parsing
        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKPositionComponent);

        latHint = typedArray.getString(R.styleable.MDKCommons_MDKPositionComponent_latHint);
        if (latHint == null) {
            latHint = view.getContext().getString(R.string.latitude_hint);
        }
        lngHint = typedArray.getString(R.styleable.MDKCommons_MDKPositionComponent_lngHint);
        if (lngHint == null) {
            lngHint = view.getContext().getString(R.string.longitude_hint);
        }

        latitudeViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_latitudeTextViewId, 0);

        if (latitudeViewId == 0) {
            latitudeViewId = R.id.component_internal_latitude;
        }

        final MDKEditText latView = (MDKEditText) view.findViewById(latitudeViewId);

        latitudeView = new WeakReference<>(latView);

        if (latView != null) {
            latView.setHint(latHint);
        }

        longitudeViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_longitudeTextViewId, 0);

        if (longitudeViewId == 0) {
            longitudeViewId = R.id.component_internal_longitude;
        }

        final MDKEditText lngView = (MDKEditText) view.findViewById(longitudeViewId);

        longitudeView = new WeakReference<>(lngView);

        if (lngHint != null) {
            lngView.setHint(lngHint);
        }

        typedArray.recycle();
    }

    /**
     * Sets the latitude hint.
     * @param latHint the new latitude hint
     */
    public void setLatitudeHint(String latHint) {
        this.latHint = latHint;
    }

    /**
     * Sets the longitude hint.
     * @param lngHint the new longitude hint
     */
    public void setLongitudeHint(String lngHint) {
        this.lngHint = lngHint;
    }

    /**
     * Updates the displayed location with the current one.
     */
    private void updateShownLocation() {
        if (!writingData) {
            this.mdkChangeListener.notifyListeners();

            try {
                double lat = Double.parseDouble(this.getLatitudeView().getText().toString());
                double lng = Double.parseDouble(this.getLongitudeView().getText().toString());

                location.setLatitude(lat);
                location.setLongitude(lng);
            } catch (NumberFormatException e) {
                Log.d(TAG, "an error occurred when parsing position");
            }
        }
    }

    /**
     * Register a ChangeListener in delegate.
     * @param listener the ChangeListener to register
     */
    public void registerChangeListener(ChangeListener listener) {
        this.mdkChangeListener.registerChangeListener(listener);
    }

    /**
     * Unregisters a ChangeListener in delegate.
     * @param listener the ChangeListener to unregister
     */
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkChangeListener.unregisterChangeListener(listener);
    }

    /**
     * Called by the view on the onAttachedToWindow event.
     * Initialize the click listeners and updates the date and time views
     */
    public void onAttachedToWindow() {
        // Initialize the latitude component
        if (latitudeViewId != 0) {
            // Handle text changed events on components
            getLatitudeView().addTextChangedListener(this);
        }
        // Initialize the longitude component
        if (longitudeViewId != 0) {
            // Handle text changed events on components
            getLongitudeView().addTextChangedListener(this);
        }

        // Set initial date and time values
        updateShownLocation();
    }

    /**
     * Returns the current location.
     * @return a {@link Location} object
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Sets the current location of the component.
     * @param location the {@link Location} to set
     */
    public void setLocation(Location location) {
        this.writingData = true;

        this.location = location;

        this.getLatitudeView().setText(String.valueOf(location.getLatitude()));
        this.getLongitudeView().setText(String.valueOf(location.getLongitude()));

        this.writingData = false;
    }

    /**
     * Returns the latitude EditText.
     * @return the latitude input field
     */
    public EditText getLatitudeView() {
        return this.latitudeView.get();
    }

    /**
     * Returns the longitude EditText.
     * @return the longitude input field
     */
    public EditText getLongitudeView() {
        return this.longitudeView.get();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // nothing to do
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updateShownLocation();
        this.validate(false, EnumFormFieldValidator.ON_USER);
    }

    @Override
    public void afterTextChanged(Editable s) {
        // nothing to do
    }

    /**
     * Sets the enabled status of the sub widgets.
     * @param enabled true to enable the widgets
     */
    public void setEnabled(boolean enabled) {
        if (this.getLatitudeView() != null) {
            this.getLatitudeView().setEnabled(enabled);
        }
        if (this.getLongitudeView() != null) {
            this.getLongitudeView().setEnabled(enabled);
        }
    }

}
