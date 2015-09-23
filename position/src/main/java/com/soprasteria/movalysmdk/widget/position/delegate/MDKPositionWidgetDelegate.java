package com.soprasteria.movalysmdk.widget.position.delegate;

import android.content.Context;
import android.content.res.TypedArray;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.soprasteria.movalysmdk.widget.core.MDKBaseWidget;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.MDKPosition;
import com.soprasteria.movalysmdk.widget.position.R;
import com.soprasteria.movalysmdk.widget.position.adapters.AddressSpinnerAdapter;
import com.soprasteria.movalysmdk.widget.position.filter.PositionInputFilter;
import com.soprasteria.movalysmdk.widget.position.model.Position;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

/**
 * Delegate for the MDKPositionWidgetDelegate widget.
 */
public class MDKPositionWidgetDelegate extends MDKWidgetDelegate implements MDKBaseWidget, TextWatcher, AdapterView.OnItemSelectedListener {

    /** tag for logging. */
    private static final String TAG = MDKPositionWidgetDelegate.class.getSimpleName();

    /** notify change listeners. */
    protected MDKChangeListenerDelegate mdkChangeListener;

    /** Latitude hint.*/
    protected String latHint;

    /** Longitude hint.*/
    protected String lngHint;

    /** Address hint. */
    private String addressHint;

    /** latitude view identifier. */
    private int latitudeViewId;

    /** longitude view identifier. */
    private int longitudeViewId;

    /** address view identifier. */
    private int addressViewId;

    /** widget mode. */
    protected int mode = -1;

    /** true if the localization should be started on widget inflation. */
    protected boolean autoStart;

    /** true if the action used to launch the map app should be visible. */
    private boolean activateGoto;

    /** latitude view. */
    protected WeakReference<EditText> latitudeView;

    /** longitude view. */
    protected WeakReference<EditText> longitudeView;

    /** address view. */
    private WeakReference<Spinner> addressView;

    /** current position. */
    protected Position position;

    /** true if the data is being written by the command. */
    protected boolean writingData = false;

    /** the time out in seconds to set on the location manager. */
    private int timeout;

    /**
     * Constructor.
     * @param root the root view
     * @param attrs the parameters set
     */
    public MDKPositionWidgetDelegate(ViewGroup root, AttributeSet attrs) {
        super(root, attrs);

        this.mdkChangeListener = new MDKChangeListenerDelegate();

        /// initialize the location object
        position = new Position(new Location("dummyprovider"));

        // Position specific fields parsing
        TypedArray typedArray = root.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKPositionComponent);

        latitudeViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_latitudeTextViewId, 0);

        if (latitudeViewId == 0) {
            latitudeViewId = R.id.component_internal_latitude;
        }

        final EditText latView = (EditText) root.findViewById(latitudeViewId);

        latView.addTextChangedListener(this);
        latView.setFilters(new InputFilter[] { new PositionInputFilter(-90, 90, 6) });

        latitudeView = new WeakReference<>(latView);

        longitudeViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_longitudeTextViewId, 0);

        if (longitudeViewId == 0) {
            longitudeViewId = R.id.component_internal_longitude;
        }

        final EditText lngView = (EditText) root.findViewById(longitudeViewId);

        lngView.addTextChangedListener(this);
        lngView.setFilters(new InputFilter[] { new PositionInputFilter(-180, 180, 6) });

        longitudeView = new WeakReference<>(lngView);

        addressViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_addressSpinnerViewId, 0);
        if (addressViewId == 0) {
            addressViewId = R.id.component_internal_address;
        }

        final Spinner addrView = (Spinner) root.findViewById(addressViewId);

        if (addrView != null) {
            addrView.setOnItemSelectedListener(this);

            addressView = new WeakReference<>(addrView);
        }

        this.timeout = typedArray.getInt(R.styleable.MDKCommons_MDKPositionComponent_timeout, 30);

        this.autoStart = typedArray.getBoolean(R.styleable.MDKCommons_MDKPositionComponent_autoStart, false);

        this.setActivateGoto(typedArray.getBoolean(R.styleable.MDKCommons_MDKPositionComponent_activeGoto, true));

        typedArray.recycle();

        // set the mode of the widget
        this.setMode(root, attrs);

        toggleViews(this.mode);
    }

    /**
     * Sets the processing mode of the view based on the given attributes.
     * @param root the view being processed
     * @param attrs the parameters set
     */
    protected void setMode(ViewGroup root, AttributeSet attrs) {
        // Position specific fields parsing
        TypedArray typedArray = root.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKPositionComponent);

        if (mode == -1) {
            this.mode = typedArray.getInt(R.styleable.MDKCommons_MDKPositionComponent_positionMode, MDKPosition.GEOPOINT);
        }

        if (mode == MDKPosition.GEOPOINT) {
            // mode == 0 -> lat & long
            latHint = typedArray.getString(R.styleable.MDKCommons_MDKPositionComponent_latHint);
            if (latHint == null) {
                latHint = root.getContext().getString(R.string.mdkwidget_mdkposition_latitude_hint);
            }
            lngHint = typedArray.getString(R.styleable.MDKCommons_MDKPositionComponent_lngHint);
            if (lngHint == null) {
                lngHint = root.getContext().getString(R.string.mdkwidget_mdkposition_longitude_hint);
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
                addressHint = root.getContext().getString(R.string.mdkwidget_mdkposition_address_hint);
            }
        }

        typedArray.recycle();

    }

    /**
     * Sets the mode of the widget.
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
        toggleViews(this.mode);
    }

    /**
     * Resets the view.
     */
    public void resetViews() {
        toggleViews(this.mode);
    }

    /**
     * Toggles the sub views visibility depending on the mode of the widget.
     */
    protected void toggleViews(int mode) {
        if (mode == MDKPosition.GEOPOINT) {
            final Spinner addrView = getAddressView();
            if (addrView != null) {
                addrView.setVisibility(View.GONE);
            }
            final EditText latView = getLatitudeView();
            if (latView != null) {
                latView.setVisibility(View.VISIBLE);
            }
            final EditText lngView = getLongitudeView();
            if (lngView != null) {
                lngView.setVisibility(View.VISIBLE);
            }
        } else {
            final Spinner addrView = getAddressView();
            if (addrView != null) {
                addrView.setVisibility(View.VISIBLE);
            }
            latitudeView.get().setVisibility(View.GONE);
            longitudeView.get().setVisibility(View.GONE);
        }
    }

    /**
     * Returns the address selection spinner.
     * @return the address spinner, or null if none exist (in the case of the MDKMapsPosition component)
     */
    private Spinner getAddressView() {
        if (this.addressView != null) {
            return this.addressView.get();
        }
        return null;
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
    protected void updateShownLocation() {
        if (!writingData) {
            this.mdkChangeListener.notifyListeners();

            if (this.mode == MDKPosition.GEOPOINT) {
                try {
                    double lat = Double.parseDouble(this.getLatitudeView().getText().toString());
                    double lng = Double.parseDouble(this.getLongitudeView().getText().toString());

                    position.setLatitude(lat);
                    position.setLongitude(lng);
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
        // TODO : utiliser le modele Position
        Location location = new Location("dummyprovider");

        location.setLatitude(position.getLatitude());
        location.setLongitude(position.getLongitude());

        return location;
    }

    /**
     * Sets the current location of the component.
     * @param context an Android context
     * @param location the {@link Location} to set
     * @param isAccurate true if the location is accurate
     */
    public void setLocation(Context context, Location location, boolean isAccurate) {
        this.writingData = true;

        if (location != null) {
            if (this.mode == MDKPosition.GEOPOINT) {
                this.getLatitudeView().setText(String.valueOf(location.getLatitude()));
                this.getLongitudeView().setText(String.valueOf(location.getLongitude()));
            } else if (!this.position.equals(location) || !this.position.hasAddresses()) {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
                    if (addresses != null && addresses.size() > 0) {
                        addresses.add(0, null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (isAccurate) {
                    if (addresses != null && addresses.size() > 0) {
                        toggleViews(MDKPosition.ADDRESS);

                        this.position.setAddresses(addresses);
                        this.position.setSelectedAddress(0);

                        fillSpinner(context, addresses, 0);
                    } else {
                        Toast.makeText(getContext(), R.string.mdkwidget_mdkposition_noaddress, Toast.LENGTH_SHORT).show();
                        this.mode = MDKPosition.GEOPOINT;

                        toggleViews(this.mode);

                        this.getLatitudeView().setText(String.valueOf(location.getLatitude()));
                        this.getLongitudeView().setText(String.valueOf(location.getLongitude()));
                    }
                } else {
                    toggleViews(MDKPosition.GEOPOINT);

                    this.getLatitudeView().setEnabled(false);
                    this.getLatitudeView().setText(String.valueOf(location.getLatitude()));

                    this.getLongitudeView().setEnabled(false);
                    this.getLongitudeView().setText(String.valueOf(location.getLongitude()));
                }
            }

            this.position.setPositionFromLocation(location);
        } else {
            if (this.mode != MDKPosition.GEOPOINT) {
                toggleViews(MDKPosition.GEOPOINT);
                this.getLatitudeView().setEnabled(false);
                this.getLongitudeView().setEnabled(false);
            }
        }

        this.writingData = false;
    }

    /**
     * Fills the spinner adapter and sets the selection.
     * @param context an Android context
     * @param addresses the addresses list
     * @param selectedPosition the position to select
     */
    private void fillSpinner(Context context, List<Address> addresses, int selectedPosition) {
        if (addresses != null && addresses.size() > 0) {
            AddressSpinnerAdapter adapter = new AddressSpinnerAdapter(context, R.layout.mdkwidget_position_layout_address_item, addresses);
            Spinner addrView = this.getAddressView();
            if (addrView != null) {
                addrView.setAdapter(adapter);
            }
            addrView.setSelection(selectedPosition);
        }
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
        this.position.setSelectedAddress(position);
        updateShownLocation();
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

    /**
     * Clears the widget content.
     */
    public void clear() {
        this.position.setPositionFromLocation(new Location("dummyprovider"));

        EditText lat = this.getLatitudeView();
        if (lat != null) {
            lat.setText("");
        }

        EditText lng = this.getLongitudeView();
        if (lng != null) {
            lng.setText("");
        }

        Spinner addr = this.getAddressView();
        if (addr != null) {
            addr.setSelection(0);
        }
    }

    public int getTimeout() {
        return this.timeout;
    }

    @Override
    public Parcelable onSaveInstanceState(Parcelable superState) {

        // Save the android view instance state
        Parcelable state = super.onSaveInstanceState(superState);

        // Save the widget
        Bundle bundle = new Bundle();
        bundle.putParcelable("state", state);
        bundle.putParcelable("position", this.position);

        return bundle;
    }

    @Override
    public Parcelable onRestoreInstanceState(View view, Parcelable state) {
        Bundle bundle = (Bundle) state;

        // Restore the widget
        Parcelable parcelable = bundle.getParcelable("state");
        this.position = bundle.getParcelable("position");

        this.setLocation(getContext(), this.position.getLocation(), true);

        this.fillSpinner(getContext(), this.position.getAddresses(), this.position.getSelectedAddress());

        // Restore the android view instance state
        return super.onRestoreInstanceState(view, parcelable);
    }

    /**
     * Returns an array of strings for validation.
     * Given the mode of the widget:
     * <ul>
     *     <li>geopoint: returns an array of strings representing the coordinates shown by the component</li>
     *     <li>address: returns a string with the address on one line.</li>
     * </ul>
     * @return the coordinates of the component
     */
    public String[] getCoordinates() {
        if (this.mode == MDKPosition.GEOPOINT) {
            return new String[]{
                    this.getLatitudeView().getText() != null ? this.getLatitudeView().getText().toString() : null,
                    this.getLongitudeView().getText() != null ? this.getLongitudeView().getText().toString() : null
            };
        } else {
            return new String[]{ this.position.getStringAddress() };
        }
    }
}
