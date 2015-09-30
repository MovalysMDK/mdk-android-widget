package com.soprasteria.movalysmdk.widget.position;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasLocation;
import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.WidgetCommandFactory;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.adapters.AddressSpinnerAdapter;
import com.soprasteria.movalysmdk.widget.position.command.PositionCommandListener;
import com.soprasteria.movalysmdk.widget.position.command.PositionWidgetCommand;
import com.soprasteria.movalysmdk.widget.position.delegate.MDKPositionWidgetDelegate;
import com.soprasteria.movalysmdk.widget.position.filter.PositionInputFilter;
import com.soprasteria.movalysmdk.widget.position.model.Position;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * MDK Position.
 * <p>Representing a position input field, allowing to set a latitude and a longitude or to retrieve it with the GPS function of the device.</p>
 * <p>This widget has the following XML attributes:</p>
 * <ul>
 *     <li>
 *         positionMode: sets one the the following modes on the widget
 *         <ul>
 *             <li>geopoint: the position will be shown as a latitude and a longitude (default option)</li>
 *             <li>address: the position will be displayed has a list of addresses, the user will choose the most appropriate one</li>
 *         </ul>
 *     </li>
 *     <li>autoStart: the widget will start looking for the current position as soon as it is inflated (default is false)</li>
 *     <li>activeGoto: will hide the map action when set to false (default is true)</li>
 *     <li>timeout: the time in seconds before the location gets timed out (default is 30 seconds)</li>
 * </ul>
 *
 * The component display has the following rules:
 * <ul>
 *     <li>
 *         If the component is disabled
 *         <ul>
 *             <li>the address spinner, the latitude and longitude input field, the clear and the acquisition buttons are disabled</li>
 *             <li>the map and direction buttons are active if the location is correct.</li>
 *         </ul>
 *     </li>
 *     <li>
 *         If the component is enabled
 *         <ul>
 *             <li>
 *                If we are address mode
 *                <ul>
 *                   <li>If there are retrieved addresses, the address selection spinner is displayed, and the latitude and longitude input fields are hidden</li>
 *                   <li>In other cases, the address spinner is hidden, the input fields are visible</li>
 *                </ul>
 *             </li>
 *             <li>
 *                 In other cases, the address spinner is hidden, the input fields are visible
 *             </li>
 *         </ul>
 *         The acquisition button is visible and disabled if there is a pending acquisition, it is checked if the coordinates are correct.
 *         The map and navigation buttons are active if the coordinates are correct.
 *         The clear button is visible and disabled if there is a pending acquisition
 *     </li>
 * </ul>
 */
public class MDKPosition extends RelativeLayout implements AdapterView.OnItemSelectedListener, View.OnClickListener, TextWatcher, MDKWidget, HasLocation, HasValidator, HasDelegate, HasChangeListener, PositionCommandListener {

    /** tag for dummy provider. */
    private static final String DUMMY = "dummyprovider";

    /** number of addresses retrieved. */
    private static final int ADDRESSES_LIST_LENGTH = 5;

    /** MDKPosition mode enumeration. */
    @IntDef({GEOPOINT, ADDRESS, INFO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PositionMode {
    }

    /** GEOPOINT. */
    public static final int GEOPOINT = 0;
    /** ADDRESS. */
    public static final int ADDRESS = 1;
    /** INFO. */
    public static final int INFO = 2;

    /** MDK Widget implementation. */
    protected MDKPositionWidgetDelegate mdkWidgetDelegate;

    /** current position. */
    private Position position;

    /** addresses list from current location. */
    private List<Address> addresses;

    /** selected address position in the list. */
    private int selectedAddress;

    /** true if the data is being written by the command. */
    protected boolean writingData = false;

    /** notify change listeners. */
    private MDKChangeListenerDelegate mdkChangeListener;

    /** true if the location is being computed. */
    private boolean acquiringPosition = false;

    /** weak reference to the location command. */
    private WeakReference<WidgetCommand> locationCommand;

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     */
    public MDKPosition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     * @param defStyleAttr the layout defined style
     */
    public MDKPosition(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Inflation on initialization.
     * @param context the android context
     * @param attrs the layout attributes
     */
    private void init(Context context, AttributeSet attrs) {
        this.mdkChangeListener = new MDKChangeListenerDelegate();

        // initialize the location object
        position = new Position();

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(this.getLayoutResource(), this);

        initDelegates(attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelLocationCommand();

        super.onDetachedFromWindow();

    }

    /**
     * Cancels the pending location command if there is one.
     */
    private void cancelLocationCommand() {
        if (locationCommand != null) {
            WidgetCommand command = locationCommand.get();
            if (command != null) {
                command.cancel();
            }
        }
    }

    /**
     * Returns the layout of the widget.
     * @return the layout of the widget
     */
    @LayoutRes
    protected int getLayoutResource() {
        return R.layout.mdkwidget_position_layout;
    }

    /**
     * Initialize the delegates of the widget.
     * @param attrs the xml attributes of the widget
     */
    protected void initDelegates(AttributeSet attrs) {
        this.mdkWidgetDelegate = new MDKPositionWidgetDelegate(this, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.mdkWidgetDelegate.isAutoStart()) {
            this.startAcquisition();
        }
    }

    /**
     * Called when the view is attached to a window.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            final EditText latView = this.mdkWidgetDelegate.getLatitudeView();
            if (latView != null) {
                // Handle text changed events on components
                latView.addTextChangedListener(this);
                latView.setFilters(new InputFilter[]{new PositionInputFilter(-90, 90, 7)});
            }
            final EditText lngView = this.mdkWidgetDelegate.getLongitudeView();
            if (lngView != null) {
                // Handle text changed events on components
                lngView.addTextChangedListener(this);
                lngView.setFilters(new InputFilter[]{new PositionInputFilter(-180, 180, 7)});
            }
            final Spinner addrView = this.mdkWidgetDelegate.getAddressView();
            if (addrView != null) {
                addrView.setOnItemSelectedListener(this);
            }
            final View locateButton = this.mdkWidgetDelegate.getLocateButton();
            if (locateButton != null) {
                locateButton.setOnClickListener(this);
            }
            final View mapsButton = this.mdkWidgetDelegate.getMapsButton();
            if (mapsButton != null) {
                mapsButton.setOnClickListener(this);
            }
            final View navButton = this.mdkWidgetDelegate.getNavButton();
            if (navButton != null) {
                navButton.setOnClickListener(this);
            }
            final View clearButton = this.mdkWidgetDelegate.getClearButton();
            if (clearButton != null) {
                clearButton.setOnClickListener(this);
            }

            updateComponentStatus();
        }
    }

    @Override
    public int[] getValidators() {
        return new int[] {R.string.mdkvalidator_position_class};
    }

    @Override
    public int getTimeOut() {
        return this.mdkWidgetDelegate.getTimeout();
    }

    @Override
    public void acquireLocation(Location location) {
        this.setLocation(location);

        updateComponentStatus();
    }

    @Override
    public void locationChanged(Location location) {
        this.setLocation(location);

        updateComponentStatus();
    }

    @Override
    public void locationFixed(Location location, int precision) {
        this.setLocation(location);

        if (precision == PositionWidgetCommand.FINE_ACCURACY_LEVEL) {
            this.stopAnimationOnLocate();
            this.acquiringPosition = false;

            this.locationCommand.clear();
            this.locationCommand = null;
        }

        updateComponentStatus();
    }

    @Override
    public void onError(int error) {
        this.mdkWidgetDelegate.setError(getResources().getString(error));
    }

    /**
     * Stops the animation on the locate button.
     */
    private void stopAnimationOnLocate() {
        View locateButton = this.mdkWidgetDelegate.getLocateButton();
        if (locateButton != null) {
            locateButton.clearAnimation();
        }
    }

    @Override
    public void locationTimedOut() {
        ((Activity) this.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MDKPosition.this.mdkWidgetDelegate.setError(getResources().getString(R.string.mdkwidget_mdkposition_locate_timeout));

                MDKPosition.this.stopAnimationOnLocate();
                MDKPosition.this.acquiringPosition = false;

                updateComponentStatus();

                MDKPosition.this.locationCommand.clear();
                MDKPosition.this.locationCommand = null;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int error = 0;

        if (v.getId() == this.mdkWidgetDelegate.getClearButtonId()) {
            this.clear();
        } else if (v.getId() == this.mdkWidgetDelegate.getLocateButtonId()) {
            startAcquisition();
        } else if (v.getId() == this.mdkWidgetDelegate.getMapsButtonId()) {
            WidgetCommand command = WidgetCommandFactory.getWidgetCommand("secondary", "", this);
            if (command != null) {
                error = ((WidgetCommand<Location, Integer>) command).execute(this.getContext(), this.getLocation());
            }
        } else if (v.getId() == this.mdkWidgetDelegate.getNavButtonId()) {
            WidgetCommand command = WidgetCommandFactory.getWidgetCommand("tertiary", "", this);
            if (command != null) {
                error = ((WidgetCommand<Location, Integer>) command).execute(this.getContext(), this.getLocation());
            }
        }

        if (error != 0) {
            this.mdkWidgetDelegate.setError(getResources().getString(error));
        }
    }

    @Override
    public String[] getCoordinates() {
        if (this.mdkWidgetDelegate.getMode() == MDKPosition.GEOPOINT) {
            final EditText latView = this.mdkWidgetDelegate.getLatitudeView();
            final EditText lngView = this.mdkWidgetDelegate.getLongitudeView();

            String latitude = null;
            String longitude = null;

            if (latView != null) {
                latitude = latView.getText().toString();
            }
            if (lngView != null) {
                longitude = lngView.getText().toString();
            }

            return new String[]{
                    latitude,
                    longitude
            };
        } else {
            return new String[]{ this.position.getFormattedAddress() };
        }
    }

    /**
     * To call when the focus state of a view has changed.
     * @param focused is component focused
     * @param direction component direction
     * @param previouslyFocusedRect component previous focus state
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused) {
            validate(EnumFormFieldValidator.ON_FOCUS);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        updateComponentStatus();
    }

    /**
     * Returns the {@link Position} stored by the widget.
     * @return the current {@link Position}
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Sets the {@link Position} on the widget.
     * @param position the {@link Position} to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Location getLocation() {
        Location location = null;

        if (!position.isNull()) {
            location = new Location(DUMMY);
            location.setLatitude(position.getLatitude());
            location.setLongitude(position.getLongitude());
        }

        return location;
    }

    /**
     * Sets the current location of the component.
     * @param location the {@link Location} to set
     */
    public void setLocation(Location location) {
        this.writingData = true;

        if (this.mdkWidgetDelegate.getMode() == ADDRESS || this.mdkWidgetDelegate.getMode() == INFO) {
            if (location != null) {
                if (!this.position.isNearTo(location)) {
                    // this occurs when the fix is done, ie we have a precise location
                    List<Address> addresses = getAddresses(location, true);

                    if (addresses != null && !addresses.isEmpty()) {
                        selectedAddress = 1;

                        // we have found a list of addresses, the user will pick one
                        this.position.setAddress(addresses.get(selectedAddress));

                        fillSpinner(addresses, selectedAddress);
                    } else {
                        // no addresses were found
                        this.mdkWidgetDelegate.setError(getResources().getString(R.string.mdkwidget_mdkposition_noaddress));
                    }
                }
            } else {
                Spinner addrView = this.mdkWidgetDelegate.getAddressView();
                if (addrView != null) {
                    addrView.setSelection(0);
                }
            }
        }

        this.position.setPositionFromLocation(location);

        if (this.mdkWidgetDelegate.getLatitudeView() != null) {
            this.mdkWidgetDelegate.getLatitudeView().setText(this.position.getFormattedLatitude());
        }
        if (this.mdkWidgetDelegate.getLongitudeView() != null) {
            this.mdkWidgetDelegate.getLongitudeView().setText(this.position.getFormattedLongitude());
        }
        if (this.mdkWidgetDelegate.getLocationInfoView() != null) {
            this.mdkWidgetDelegate.getLocationInfoView().setText(this.position.getFormattedLocation());
        }
        if (this.mdkWidgetDelegate.getAddressInfoView() != null) {
            this.mdkWidgetDelegate.getAddressInfoView().setText(this.position.getFormattedAddress());
        }

        updateComponentStatus();

        this.writingData = false;
    }

    /**
     * Get the addresses from the location.
     * @param location the location to set
     * @param addEmpty if true, will add the empty element to the list
     * @return the addresses list
     */
    @Nullable
    protected List<Address> getAddresses(Location location, boolean addEmpty) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        addresses = null;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), ADDRESSES_LIST_LENGTH);
            if (addresses != null && !addresses.isEmpty() && addEmpty) {
                // we add an empty element
                addresses.add(0, null);
            }
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), getResources().getString(R.string.mdkwidget_mdkposition_error_getting_addresses), e);
            this.mdkWidgetDelegate.setError(getResources().getString(R.string.mdkwidget_mdkposition_error_getting_addresses));
        }

        return addresses;
    }

    /**
     * Fills the spinner adapter and sets the selection.
     * @param addresses the addresses list
     */
    private void fillSpinner(List<Address> addresses, int selection) {
        if (addresses != null && !addresses.isEmpty()) {
            AddressSpinnerAdapter adapter = new AddressSpinnerAdapter(getContext(), R.layout.mdkwidget_position_layout_address_item, addresses);
            Spinner addrView = this.mdkWidgetDelegate.getAddressView();

            if (addrView != null) {
                addrView.setAdapter(adapter);
                addrView.setSelection(selection);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // nothing to do
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!writingData) {
            updateLocationOnTextChanged();
        }
    }

    /**
     * Updates the location from the values in the input field when their values change.
     */
    protected void updateLocationOnTextChanged() {
        Location location = new Location(DUMMY);

        if (this.mdkWidgetDelegate.getLatitudeView() != null && this.mdkWidgetDelegate.getLatitudeView().getText().length() > 0) {
            try {
                location.setLatitude(Double.parseDouble(this.mdkWidgetDelegate.getLatitudeView().getText().toString()));
            } catch (NumberFormatException e) {
                this.mdkWidgetDelegate.setError(getResources().getString(R.string.mdkwidget_mdkposition_error_parsing_location));
            }
        }

        if (this.mdkWidgetDelegate.getLongitudeView() != null && this.mdkWidgetDelegate.getLongitudeView().getText().length() > 0) {
            try {
                location.setLongitude(Double.parseDouble(this.mdkWidgetDelegate.getLongitudeView().getText().toString()));
            } catch (NumberFormatException e) {
                this.mdkWidgetDelegate.setError(getResources().getString(R.string.mdkwidget_mdkposition_error_parsing_location));
            }
        }

        setLocation(location);

        validate(EnumFormFieldValidator.ON_USER);

        updateComponentStatus();
    }

    @Override
    public void afterTextChanged(Editable s) {
        // nothing to do
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.selectedAddress = position;
        this.position.setAddress(addresses.get(this.selectedAddress));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Nothing to do
    }

    /**
     * Clears the widget content.
     */
    public void clear() {
        this.setLocation(null);
    }

    /**
     * Start the location acquisition.
     */
    protected void startAcquisition() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        boolean isProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (this.mdkWidgetDelegate.getLocateButton() != null && isProviderEnabled) {
            WidgetCommand commandToExecute = WidgetCommandFactory.getWidgetCommand("primary", "", this);

            cancelLocationCommand();

            locationCommand = new WeakReference<>(commandToExecute);

            this.acquiringPosition = true;

            if (this.mdkWidgetDelegate.getLocateButtonAnimationId() != 0) {
                this.mdkWidgetDelegate.getLocateButton().startAnimation(AnimationUtils.loadAnimation(getContext(), this.mdkWidgetDelegate.getLocateButtonAnimationId()));
            }

            updateComponentStatus();

            if (commandToExecute != null) {
                ((WidgetCommand<PositionCommandListener, Void>) commandToExecute).execute(this.getContext(), this);
            }
        }
    }

    /**
     * set the enable status on a view.
     * @param view the view to set
     * @param isEnabled true if the view should be enabled
     */
    private void setEnabledView(View view, boolean isEnabled) {
        if (view != null) {
            view.setEnabled(isEnabled);
        }
    }

    /**
     * set the visible status on a view.
     * @param view the view to set
     * @param visible true if the view should be visible
     */
    private void setVisibleView(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * set the checked status on a view.
     * @param view the view to set
     * @param checked true if the view should be checked
     */
    private void setCheckedView(View view, boolean checked) {
        if (view != null && view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
    }

    /**
     * Changes the status of the sub components based on the state of MDKPosition.
     */
    protected void updateComponentStatus() {
        boolean isValid = !this.position.isNull();

        /* latitude input text setEnable */
        setEnabledView(this.mdkWidgetDelegate.getLatitudeView(), isEnabled() && this.mdkWidgetDelegate.getMode() == GEOPOINT);
        /* latitude input text setVisible */
        setVisibleView(this.mdkWidgetDelegate.getLatitudeView(),
                this.mdkWidgetDelegate.getMode() == GEOPOINT || (this.mdkWidgetDelegate.getMode() == ADDRESS && !this.hasAddresses()));

        /* longitude input text setEnable */
        setEnabledView(this.mdkWidgetDelegate.getLongitudeView(), isEnabled() && this.mdkWidgetDelegate.getMode() == GEOPOINT);
        /* longitude input text setVisible */
        setVisibleView(this.mdkWidgetDelegate.getLongitudeView(),
                this.mdkWidgetDelegate.getMode() == GEOPOINT || (this.mdkWidgetDelegate.getMode() == ADDRESS && !this.hasAddresses()));

        /* address spinner setEnable */
        setEnabledView(this.mdkWidgetDelegate.getAddressView(),
                isEnabled() && this.mdkWidgetDelegate.getMode() == ADDRESS && this.hasAddresses() && !acquiringPosition);
        /* address spinner setVisible */
        setVisibleView(this.mdkWidgetDelegate.getAddressView(), this.mdkWidgetDelegate.getMode() == ADDRESS && this.hasAddresses());

        /* location info views */
        setVisibleView(this.mdkWidgetDelegate.getLocationInfoView(), this.mdkWidgetDelegate.getMode() == INFO);

        /* address info view */
        setVisibleView(this.mdkWidgetDelegate.getAddressInfoView(), this.mdkWidgetDelegate.getMode() == INFO);

        /* clear button setEnable */
        setEnabledView(this.mdkWidgetDelegate.getClearButton(), isEnabled() && !acquiringPosition);

        /* locate button setEnable */
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        setEnabledView(this.mdkWidgetDelegate.getLocateButton(), isEnabled() && !acquiringPosition && isProviderEnabled);
        /* locate button setChecked */
        setCheckedView(this.mdkWidgetDelegate.getLocateButton(), isValid);

        /* map button setEnable */
        setEnabledView(this.mdkWidgetDelegate.getMapsButton(), isValid);

        /* navigation button setEnable */
        setEnabledView(this.mdkWidgetDelegate.getNavButton(), isValid);
    }

    /**
     * Returns true if the component knows a list of addresses, and one was selected.
     * @return true if the component knows a list of addresses, and one was selected
     */
    public boolean hasAddresses() {
        if (this.addresses == null) {
            return false;
        } else {
            // there should be at least two elements as there is an empty address
            return this.addresses.size() > 1 && this.position.getAddress() != null;
        }
    }

    /* technical delegate methods */

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    /* rich selector methods */

    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mergeDrawableStates(baseState, additionalState);
    }

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.mdkWidgetDelegate != null) {
            return this.mdkWidgetDelegate.superOnCreateDrawableState(extraSpace);
        } else {
            // first called in the super constructor
            return super.onCreateDrawableState(extraSpace);
        }
    }

    /* delegate accelerator methods */

    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.mdkWidgetDelegate.isMandatory();
    }

    @Override
    public void addError(MDKMessages error) {
        this.mdkWidgetDelegate.addError(error);
    }

    @Override
    public void setError(CharSequence error) {
        this.mdkWidgetDelegate.setError(error);
    }

    @Override
    public void clearError() {
        this.mdkWidgetDelegate.clearError();
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        boolean isValidated = this.mdkWidgetDelegate.validate(true, validationMode);

        updateComponentStatus();

        return isValidated;
    }

    @Override
    public boolean validate() {
        boolean isValidated = this.mdkWidgetDelegate.validate(true, EnumFormFieldValidator.VALIDATE);

        updateComponentStatus();

        return isValidated;
    }

    @Override
    public Object getValueToValidate() {
        return getCoordinates();
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.mdkChangeListener.registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkChangeListener.unregisterChangeListener(listener);
    }

    @Override
    public void setLatitudeHint(String latHint) {
        this.mdkWidgetDelegate.setLatitudeHint(latHint);
    }

    @Override
    public void setLongitudeHint(String lngHint) {
        this.mdkWidgetDelegate.setLongitudeHint(lngHint);
    }

    /**
     * Sets the mode of the widget.
     * @param mode the mode to set
     */
    public void setMode(@PositionMode int mode) {
        this.mdkWidgetDelegate.setMode(mode);
        updateComponentStatus();
    }

    /**
     * Sets the timeout to use for location.
     * @param timeout the timeout to set
     */
    public void setTimeOut(int timeout) {
        this.mdkWidgetDelegate.setTimeOut(timeout);
    }

    /**
     * Sets whether the widget should display an action button to launch an external localization app.
     * @param activateGoto true if the widget should display an action button to launch an external localization app
     */
    public void setActivateGoto(boolean activateGoto) {
        this.mdkWidgetDelegate.setActivateGoto(activateGoto);
        updateComponentStatus();
    }

    /**
     * Sets whether the widget should automatically start the localization on inflate.
     * @param autoStart true to start the localization on inflate
     */
    public void setAutoStart(boolean autoStart) {
        this.mdkWidgetDelegate.setAutoStart(autoStart);
        if (autoStart) {
            startAcquisition();
        }
    }

    /* save / restore */

    @Override
    public Parcelable onSaveInstanceState() {
        // Save the android view instance state
        Parcelable state = super.onSaveInstanceState();
        // Save the MDKWidgetDelegate instance state
        state = this.mdkWidgetDelegate.onSaveInstanceState(state);

        // Save the widget
        Bundle bundle = (Bundle) state;
        bundle.putParcelable("position", this.position);
        bundle.putBoolean("acquiringPosition", this.acquiringPosition);

        Parcelable[] addrs = null;

        if (this.addresses != null) {
            addrs = new Parcelable[this.addresses.size()];

            for (int rank = 0; rank < this.addresses.size(); rank++) {
                addrs[rank] = this.addresses.get(rank);
            }
            bundle.putParcelableArray("addresses", addrs);
        }

        bundle.putInt("selectedAddress", this.selectedAddress);

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        // Restore the MDKWidgetDelegate instance state
        Parcelable innerState = this.mdkWidgetDelegate.onRestoreInstanceState(this, state);

        // Restore the android view instance state
        super.onRestoreInstanceState(innerState);

        Bundle bundle = (Bundle) state;

        this.position = bundle.getParcelable("position");
        this.acquiringPosition = bundle.getBoolean("acquiringPosition");
        this.selectedAddress = bundle.getInt("selectedAddress");

        Parcelable[] inAddresses = bundle.getParcelableArray("addresses");
        if (inAddresses != null) {
            this.addresses = new ArrayList<>();

            for (Parcelable address : inAddresses) {
                this.addresses.add((Address) address);
            }

            this.fillSpinner(this.addresses, selectedAddress);
        }

        if (this.acquiringPosition && this.mdkWidgetDelegate.getLocateButton() != null && this.mdkWidgetDelegate.getLocateButtonAnimationId() != 0) {
            this.mdkWidgetDelegate.getLocateButton().startAnimation(AnimationUtils.loadAnimation(getContext(), this.mdkWidgetDelegate.getLocateButtonAnimationId()));
        }

        updateComponentStatus();
    }
}
