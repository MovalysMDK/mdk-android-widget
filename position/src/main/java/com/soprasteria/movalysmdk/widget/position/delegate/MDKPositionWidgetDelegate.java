package com.soprasteria.movalysmdk.widget.position.delegate;

import android.content.Context;
import android.content.res.TypedArray;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.soprasteria.movalysmdk.widget.core.MDKBaseWidget;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.MDKPosition;
import com.soprasteria.movalysmdk.widget.position.R;
import com.soprasteria.movalysmdk.widget.position.adapters.AddressSpinnerAdapter;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

/**
 * Delegate for the MDKPositionWidgetDelegate widget.
 * This delegate implements the core concepts of the component : it handles the fact that the MDKPosition
 * widget can be used alone, or with the addition of an other TextView.
 * In this later case the MDKPosition will act
 * as the master component (as it hosts this delegate), and the TextView will act as the slave component.
 */
public class MDKPositionWidgetDelegate extends MDKWidgetDelegate implements MDKBaseWidget, TextWatcher, AdapterView.OnItemSelectedListener {

    /** tag for logging. */
    private static final String TAG = MDKPositionWidgetDelegate.class.getSimpleName();

    /** notify change listeners. */
    private MDKChangeListenerDelegate mdkChangeListener;

    /** Latitude hint.*/
    private String latHint;

    /** Longitude hint.*/
    private String lngHint;

    /** Address hint. */
    private String addressHint;

    /** latitude view identifier. */
    private int latitudeViewId;

    /** longitude view identifier. */
    private int longitudeViewId;

    /** address view identifier. */
    private int addressViewId;

    /** widget mode. */
    private int mode = -1;

    /** true if the localization should be started on widget inflation. */
    private boolean autoStart;

    /** true if the action used to launch the map app should be visible. */
    private boolean activateGoto;

    /** latitude view. */
    private WeakReference<EditText> latitudeView;

    /** longitude view. */
    private WeakReference<EditText> longitudeView;

    /** address view. */
    private WeakReference<Spinner> addressView;

    /** current location. */
    private Location location;

    /** true if the data is being written by the command. */
    private boolean writingData = false;

    /**
     * Constructor.
     * @param root the root view
     * @param attrs the parameters set
     */
    public MDKPositionWidgetDelegate(ViewGroup root, AttributeSet attrs) {
        super(root, attrs);

        this.mdkChangeListener = new MDKChangeListenerDelegate();

        /// initialize the location object
        location = new Location("dummyprovider");

        // Position specific fields parsing
        TypedArray typedArray = root.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKPositionComponent);

        latitudeViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_latitudeTextViewId, 0);

        if (latitudeViewId == 0) {
            latitudeViewId = R.id.component_internal_latitude;
        }

        final EditText latView = (EditText) root.findViewById(latitudeViewId);

        latView.addTextChangedListener(this);

        latitudeView = new WeakReference<>(latView);

        longitudeViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_longitudeTextViewId, 0);

        if (longitudeViewId == 0) {
            longitudeViewId = R.id.component_internal_longitude;
        }

        final EditText lngView = (EditText) root.findViewById(longitudeViewId);

        lngView.addTextChangedListener(this);

        longitudeView = new WeakReference<>(lngView);

        addressViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_addressSpinnerViewId, 0);
        if (addressViewId == 0) {
            addressViewId = R.id.component_internal_address;
        }

        final Spinner addrView = (Spinner) root.findViewById(addressViewId);

        addrView.setOnItemSelectedListener(this);

        addressView = new WeakReference<>(addrView);

        // set the mode of the widget
        this.setMode(root, typedArray);

        this.setAutoStart(typedArray.getBoolean(R.styleable.MDKCommons_MDKPositionComponent_autoStart, false));

        this.setActivateGoto(typedArray.getBoolean(R.styleable.MDKCommons_MDKPositionComponent_activeGoto, true));

        typedArray.recycle();
    }

    /**
     * Sets the processing mode of the view based on the given attributes.
     * @param root the view being processed
     */
    private void setMode(ViewGroup root, TypedArray typedArray) {
        if (mode == -1) {
            this.mode = typedArray.getInt(R.styleable.MDKCommons_MDKPositionComponent_positionMode, 0);
        }

        if (mode == 0) {
            // mode == 0 -> lat & long
            latHint = typedArray.getString(R.styleable.MDKCommons_MDKPositionComponent_latHint);
            if (latHint == null) {
                latHint = root.getContext().getString(R.string.latitude_hint);
            }
            lngHint = typedArray.getString(R.styleable.MDKCommons_MDKPositionComponent_lngHint);
            if (lngHint == null) {
                lngHint = root.getContext().getString(R.string.longitude_hint);
            }

            final EditText latView = latitudeView.get();
            final EditText lngView = longitudeView.get();

            if (latView != null) {
                latView.setHint(latHint);
            }
            if (lngView != null) {
                lngView.setHint(lngHint);
            }
        } else {
            // mode == 1 -> addresses spinner
            addressHint = typedArray.getString(R.styleable.MDKCommons_MDKPositionComponent_addressHint);
            if (addressHint == null) {
                addressHint = root.getContext().getString(R.string.address_hint);
            }
        }

        toggleViews();
    }

    /**
     * Sets the mode of the widget.
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
        toggleViews();
    }

    /**
     * Toggles the sub views visibility depending on the mode of the widget.
     */
    private void toggleViews() {
        if (mode == 0) {
            addressView.get().setVisibility(View.GONE);
            latitudeView.get().setVisibility(View.VISIBLE);
            longitudeView.get().setVisibility(View.VISIBLE);
        } else {
            addressView.get().setVisibility(View.VISIBLE);
            latitudeView.get().setVisibility(View.GONE);
            longitudeView.get().setVisibility(View.GONE);
        }
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

            if (this.mode == 0) {
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
    public void setLocation(Context context, Location location) {
        this.writingData = true;

        this.location = location;

        if (this.mode == 0) {
            this.getLatitudeView().setText(String.valueOf(location.getLatitude()));
            this.getLongitudeView().setText(String.valueOf(location.getLongitude()));
        } else {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            AddressSpinnerAdapter adapter = new AddressSpinnerAdapter(context, R.layout.mdkwidget_position_layout_address_item, addresses);

            this.addressView.get().setAdapter(adapter);
        }

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateShownLocation();
        //this.validate(false, EnumFormFieldValidator.ON_USER);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Nothing to do
    }

    /**
     * Returns true if the widget is set to auto-start.
     * @return true if the widget is set to auto-start
     */
    public boolean isAutoStart() {
        return autoStart;
    }

    /**
     * Sets whether the widget should automatically start the localization on inflate.
     * @param autoStart true to start the localization on inflate
     */
    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
        if (autoStart) {
            ((MDKPosition)this.valueObject.getView()).executePositionCommand();
        }
    }

    /**
     * Returns true if the widget should display an action button to launch an external localization app.
     * @return true if the widget should display an action button to launch an external localization app.
     */
    public boolean isActivateGoto() {
        return activateGoto;
    }

    /**
     * Sets whether the widget should display an action button to launch an external localization app.
     * @param activateGoto true if the widget should display an action button to launch an external localization app
     */
    public void setActivateGoto(boolean activateGoto) {
        this.activateGoto = activateGoto;
        ((MDKPosition)this.valueObject.getView()).setMapButtonVisibility();
    }
}
