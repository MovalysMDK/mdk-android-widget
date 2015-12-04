package com.soprasteria.movalysmdk.widget.position;

import android.content.Context;
import android.content.res.TypedArray;
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
import android.text.InputType;
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
import com.soprasteria.movalysmdk.widget.core.behavior.HasEditFields;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.model.Position;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasPosition;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.AttributesHelper;
import com.soprasteria.movalysmdk.widget.core.helper.CommandHelper;
import com.soprasteria.movalysmdk.widget.core.listener.AsyncWidgetCommandListener;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.adapters.AddressSpinnerAdapter;
import com.soprasteria.movalysmdk.widget.position.command.PositionWidgetCommand;
import com.soprasteria.movalysmdk.widget.position.delegate.MDKPositionWidgetDelegate;
import com.soprasteria.movalysmdk.widget.position.filter.PositionInputFilter;
import com.soprasteria.movalysmdk.widget.position.helper.PositionHelper;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * MDK Position.
 * <p>Representing a position input field, allowing to set a latitude and a longitude or to retrieve it with the GPS function of the device.</p>
 * <p>This widget has the following XML attributes:</p>
 * <ul>
 * <li>
 * positionMode: sets one the the following modes on the widget
 * <ul>
 * <li>geopoint: the position will be shown as a latitude and a longitude (default option)</li>
 * <li>address: the position will be displayed has a list of addresses, the user will choose the most appropriate one</li>
 * <li>info: the input is deactivated, the component wil display the current location.</li>
 * </ul>
 * </li>
 * <li>autoStart: the widget will start looking for the current position as soon as it is inflated (default is false)</li>
 * <li>activeGoto: will hide the map action when set to false (default is true)</li>
 * <li>timeout: the time in seconds before the location gets timed out (default is 30 seconds)</li>
 * </ul>
 * <p/>
 * The component display has the following rules:
 * <ul>
 * <li>
 * If the component is disabled
 * <ul>
 * <li>the address spinner, the latitude and longitude input field, the clear and the acquisition buttons are disabled</li>
 * <li>the map and direction buttons are active if the location is correct.</li>
 * </ul>
 * </li>
 * <li>
 * If the component is enabled
 * <ul>
 * <li>
 * If we are address mode
 * <ul>
 * <li>If there are retrieved addresses, the address selection spinner is displayed, and the latitude and longitude input fields are hidden</li>
 * <li>In other cases, the address spinner is hidden, the input fields are visible</li>
 * </ul>
 * </li>
 * <li>
 * In other cases, the address spinner is hidden, the input fields are visible
 * </li>
 * </ul>
 * The acquisition button is visible and disabled if there is a pending acquisition, it is checked if the coordinates are correct.
 * The map and navigation buttons are active if the coordinates are correct.
 * The clear button is visible and disabled if there is a pending acquisition
 * </li>
 * </ul>
 */
public class MDKPosition extends RelativeLayout implements AdapterView.OnItemSelectedListener, View.OnClickListener, TextWatcher, MDKWidget, HasEditFields, HasPosition, HasValidator, HasDelegate, HasChangeListener, AsyncWidgetCommandListener<Location> {

    /**
     * tag for dummy provider.
     */
    private static final String DUMMY = "dummyprovider";

    /**
     * number of addresses retrieved.
     */
    private static final int ADDRESSES_LIST_LENGTH = 5;

    /**
     * MDKPosition mode enumeration.
     */
    @IntDef({GEOPOINT, ADDRESS, INFO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PositionMode {
    }

    /**
     * GEOPOINT.
     */
    public static final int GEOPOINT = 0;
    /**
     * ADDRESS.
     */
    public static final int ADDRESS = 1;
    /**
     * INFO.
     */
    public static final int INFO = 2;

    /**
     * MDK Widget implementation.
     */
    protected MDKPositionWidgetDelegate mdkWidgetDelegate;

    /**
     * current position.
     */
    private Position position;

    /**
     * addresses list from current location.
     */
    private List<Address> addresses;

    /**
     * selected address position in the list.
     */
    private int selectedAddress;

    /**
     * true if the data is being written by the command.
     */
    protected boolean writingData = false;

    /**
     * notify change listeners.
     */
    private MDKChangeListenerDelegate mdkChangeListener;

    /**
     * true if the location is being computed.
     */
    private boolean acquiringPosition = false;

    /**
     * Constructor.
     *
     * @param context the android context
     * @param attrs   the layout attributes
     */
    public MDKPosition(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     *
     * @param context      the android context
     * @param attrs        the layout attributes
     * @param defStyleAttr the layout defined style
     */
    public MDKPosition(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Inflation on initialization.
     *
     * @param context the android context
     * @param attrs   the layout attributes
     */
    private void init(Context context, AttributeSet attrs) {
        this.mdkChangeListener = new MDKChangeListenerDelegate();

        // initialize the location object
        position = new Position();


        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(this.getLayoutResource(), this);

        initDelegates(attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        setReadonly(AttributesHelper.getBooleanFromBooleanAttribute(typedArray, R.styleable.MDKCommons_readonly, false));
        typedArray.recycle();
    }

    /**
     * Returns the layout of the widget.
     *
     * @return the layout of the widget
     */
    @LayoutRes
    protected int getLayoutResource() {
        return R.layout.mdkwidget_position_layout;
    }

    /**
     * Initialize the delegates of the widget.
     *
     * @param attrs the xml attributes of the widget
     */
    protected void initDelegates(AttributeSet attrs) {
        this.mdkWidgetDelegate = new MDKPositionWidgetDelegate(this, attrs);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode() && this.mdkWidgetDelegate.isAutoStart()) {
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
    protected void onDetachedFromWindow() {
        CommandHelper.removeCommandListenerOnWidget(this.getContext(), this, PositionWidgetCommand.class);

        super.onDetachedFromWindow();

    }

    @Override
    public int[] getValidators() {
        return new int[]{R.string.mdkvalidator_position_class};
    }

    @Override
    public int getTimeOut() {
        return this.mdkWidgetDelegate.getTimeout();
    }

    @Override
    public void onStart(Location location) {
        this.setLocation(location);

        updateComponentStatus();
    }

    @Override
    public void onUpdate(Location location) {
        this.setLocation(location);

        updateComponentStatus();
    }

    @Override
    public void onFinish(Location location) {
        this.setLocation(location);

        this.stopAnimationOnLocate();
        this.acquiringPosition = false;

        CommandHelper.removeCommandOnWidget(this.getContext(), this, PositionWidgetCommand.class, true);

        updateComponentStatus();
    }

    @Override
    public void onError(int errorType) {
        CommandHelper.removeCommandOnWidget(this.getContext(), this, PositionWidgetCommand.class, true);

        int errorMessage = 0;

        if (errorType == PositionWidgetCommand.NO_GPS) {
            errorMessage = R.string.mdkcommand_position_error_gps_disabled;
        } else if (errorType == PositionWidgetCommand.TIME_OUT) {
            errorMessage = R.string.mdkwidget_mdkposition_locate_timeout;

            this.stopAnimationOnLocate();
            this.acquiringPosition = false;

        }

        updateComponentStatus();


        this.mdkWidgetDelegate.setError(getResources().getString(errorMessage));
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
    public void onClick(View v) {
        int error = 0;

        if (v.getId() == this.mdkWidgetDelegate.getClearButtonId()) {
            this.clear();
        } else if (v.getId() == this.mdkWidgetDelegate.getLocateButtonId()) {
            startAcquisition();
        } else if (v.getId() == this.mdkWidgetDelegate.getMapsButtonId()) {
            CommandHelper.startCommandOnWidget(this, "secondary", this.getLocation());
        } else if (v.getId() == this.mdkWidgetDelegate.getNavButtonId()) {
            CommandHelper.startCommandOnWidget(this, "tertiary", this.getLocation());
        }

        if (error != 0) {
            this.mdkWidgetDelegate.setError(getResources().getString(error));
        }
    }

    /**
     * To call when the focus state of a view has changed.
     *
     * @param focused               is component focused
     * @param direction             component direction
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
     *
     * @return the current {@link Position}
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    /**
     * Sets the {@link Position} on the widget.
     *
     * @param position the {@link Position} to set
     */
    @Override
    public void setPosition(Position position) {
        this.setLocation(position.getLocation());
    }

    /**
     * Returns the current location of the component.
     *
     * @return the current location of the component
     */
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
     *
     * @param location the {@link Location} to set
     */
    public void setLocation(Location location) {
        this.writingData = true;

        if (this.mdkWidgetDelegate.getMode() == ADDRESS || this.mdkWidgetDelegate.getMode() == INFO) {
            if (location != null) {
                if (!PositionHelper.isNearTo(this.position, location)) {
                    // this occurs when the fix is done, ie we have a precise location
                    getAddresses(location, true);

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
            this.mdkWidgetDelegate.getLatitudeView().setText(PositionHelper.getFormattedLatitude(this.position));
        }
        if (this.mdkWidgetDelegate.getLongitudeView() != null) {
            this.mdkWidgetDelegate.getLongitudeView().setText(PositionHelper.getFormattedLongitude(this.position));
        }
        if (this.mdkWidgetDelegate.getLocationInfoView() != null) {
            this.mdkWidgetDelegate.getLocationInfoView().setText(PositionHelper.getFormattedLocation(this.position));
        }
        if (this.mdkWidgetDelegate.getAddressInfoView() != null) {
            this.mdkWidgetDelegate.getAddressInfoView().setText(PositionHelper.getFormattedAddress(this.position));
        }

        updateComponentStatus();

        this.writingData = false;
    }

    /**
     * Get the addresses from the location.
     *
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
     *
     * @param addresses the addresses list
     * @param selection the selection to make on the spinner
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

        // we do not use setLocation here because we only update the input fields.
        // there is no need to update the other fields
        this.position.setPositionFromLocation(location);

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
            CommandHelper.startAsyncCommandOnWidget(this.getContext(), this, "primary", this);

            this.acquiringPosition = true;

            if (this.mdkWidgetDelegate.getLocateButtonAnimationId() != 0) {
                this.mdkWidgetDelegate.getLocateButton().startAnimation(AnimationUtils.loadAnimation(getContext(), this.mdkWidgetDelegate.getLocateButtonAnimationId()));
            }

            updateComponentStatus();
        }
    }

    /**
     * set the enable status on a view.
     *
     * @param view      the view to set
     * @param isEnabled true if the view should be enabled
     */
    private void setEnabledView(View view, boolean isEnabled) {
        if (view != null) {
            view.setEnabled(isEnabled);
        }
    }

    /**
     * set the visible status on a view.
     *
     * @param view    the view to set
     * @param visible true if the view should be visible
     */
    private void setVisibleView(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * set the checked status on a view.
     *
     * @param view    the view to set
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
        if (!isReadonly()) {
            setEnabledView(this.mdkWidgetDelegate.getAddressView(),
                    isEnabled() && this.mdkWidgetDelegate.getMode() == ADDRESS && this.hasAddresses() && !acquiringPosition);
        } else {
            setEnabledView(this.mdkWidgetDelegate.getAddressView(), false);
            setVisibleView(this.mdkWidgetDelegate.getLocateButton(), false);
            setVisibleView(this.mdkWidgetDelegate.getClearButton(), false);
        }
        /* address spinner setVisible */
        setVisibleView(this.mdkWidgetDelegate.getAddressView(), this.mdkWidgetDelegate.getMode() == ADDRESS && this.hasAddresses());

        /* location info views */
        setVisibleView(this.mdkWidgetDelegate.getLocationInfoView(), this.mdkWidgetDelegate.getMode() == INFO);

        /* address info view */
        setVisibleView(this.mdkWidgetDelegate.getAddressInfoView(), this.mdkWidgetDelegate.getMode() == INFO);

        /* clear button setEnable */
        setEnabledView(this.mdkWidgetDelegate.getClearButton(), isEnabled() && !isReadonly() && !acquiringPosition);

        /* locate button setEnable */
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        setEnabledView(this.mdkWidgetDelegate.getLocateButton(), isEnabled() && !isReadonly() && !acquiringPosition && isProviderEnabled);
        /* locate button setChecked */
        setCheckedView(this.mdkWidgetDelegate.getLocateButton(), isValid);

        /* map button setEnable */
        setEnabledView(this.mdkWidgetDelegate.getMapsButton(), isValid);

        /* navigation button setEnable */
        setEnabledView(this.mdkWidgetDelegate.getNavButton(), isValid);
    }

    /**
     * Returns true if the component knows a list of addresses, and one was selected.
     *
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

    @Override
    public View[] getEditFields() {
        return new View[]{
                this.mdkWidgetDelegate.getLatitudeView(),
                this.mdkWidgetDelegate.getLongitudeView()
        };
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
    public void setReadonly(boolean readonly) {
        this.mdkWidgetDelegate.setReadonly(readonly);

        if (this.mdkWidgetDelegate.getLongitudeView() != null && this.mdkWidgetDelegate.getLatitudeView() != null) {
            EditText lon = this.mdkWidgetDelegate.getLongitudeView();
            EditText lat = this.mdkWidgetDelegate.getLatitudeView();

            if (readonly) {
                setClickable(false);

                lon.setInputType(InputType.TYPE_NULL);
                lat.setInputType(InputType.TYPE_NULL);
                lon.setFocusable(false);
                lat.setFocusable(false);

                lon.setMovementMethod(null);
                lat.setMovementMethod(null);

                lon.setKeyListener(null);
                lat.setKeyListener(null);
            } else {
                lon.setFocusableInTouchMode(true);
                lat.setFocusableInTouchMode(true);
                lon.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                lat.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            }
        }

        updateComponentStatus();
    }

    @Override
    public boolean isReadonly() {
        return this.mdkWidgetDelegate.isReadonly();
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
        return this.position;
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
     *
     * @param mode the mode to set
     */
    public void setMode(@PositionMode int mode) {
        this.mdkWidgetDelegate.setMode(mode);
        updateComponentStatus();
    }

    /**
     * Sets the timeout to use for location.
     *
     * @param timeout the timeout to set
     */
    public void setTimeOut(int timeout) {
        this.mdkWidgetDelegate.setTimeOut(timeout);
    }

    /**
     * Sets whether the widget should display an action button to launch an external localization app.
     *
     * @param activateGoto true if the widget should display an action button to launch an external localization app
     */
    public void setActivateGoto(boolean activateGoto) {
        this.mdkWidgetDelegate.setActivateGoto(activateGoto);
        updateComponentStatus();
    }

    /**
     * Sets whether the widget should automatically start the localization on inflate.
     *
     * @param autoStart true to start the localization on inflate
     */
    public void setAutoStart(boolean autoStart) {
        this.mdkWidgetDelegate.setAutoStart(autoStart);
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

        if (this.addresses != null) {
            Parcelable[] addrs;

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

        CommandHelper.restoreAsyncCommandsOnWidget(this.getContext(), this);

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

        setLocation(this.position.getLocation());

        if (this.acquiringPosition && this.mdkWidgetDelegate.getLocateButton() != null && this.mdkWidgetDelegate.getLocateButtonAnimationId() != 0) {
            this.mdkWidgetDelegate.getLocateButton().startAnimation(AnimationUtils.loadAnimation(getContext(), this.mdkWidgetDelegate.getLocateButtonAnimationId()));
        }

        updateComponentStatus();
    }
}
