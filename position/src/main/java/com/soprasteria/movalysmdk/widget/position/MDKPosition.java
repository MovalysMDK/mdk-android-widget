package com.soprasteria.movalysmdk.widget.position;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasCommands;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLocation;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.WidgetCommandDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.adapters.AddressSpinnerAdapter;
import com.soprasteria.movalysmdk.widget.position.command.MapWidgetCommand;
import com.soprasteria.movalysmdk.widget.position.command.PositionCommandListener;
import com.soprasteria.movalysmdk.widget.position.command.PositionWidgetCommand;
import com.soprasteria.movalysmdk.widget.position.delegate.MDKPositionWidgetDelegate;
import com.soprasteria.movalysmdk.widget.position.filter.PositionInputFilter;
import com.soprasteria.movalysmdk.widget.position.model.Position;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
 * TODO explain rules
 * si le composant est disable ->
 *      address / latitude / longitude / clear / acquisition sont disable
 *      bouton carte actif uniquement si latitude & longitude OK
 * si le composant est enable ->
 *      si mode address,
 *          si ensemble adresses valide -> address visible et actif, longitude & latitude invisible et non actifs
 *          sinon address invisible et inactive, latitude & longitude visible et actifs si la saisie est autorisee (mode a ajouter)
 *      sinon
 *          address invisible et inactive, latitude longitude visibles et actifs si saisie manuelle autorisee
 *      bouton d'acquisition visible et disable si acquisition en cours, check si latitude & longitude valides
 *      bouton map visible et active si latitude & longitude valides
 *      bouton clear visible et actif pas d'acquisition en cours
 */
public class MDKPosition extends RelativeLayout implements AdapterView.OnItemSelectedListener, TextWatcher, MDKWidget, HasLocation, HasValidator, HasCommands, HasDelegate, HasChangeListener, PositionCommandListener {

    /** tag for dummy provider. */
    private static final String DUMMY = "dummyprovider";

    /** MDKPosition mode enumeration. */
    @IntDef({GEOPOINT, ADDRESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PositionMode {
    }

    /** GEOPOINT. */
    public static final int GEOPOINT = 0;
    /** ADDRESS. */
    public static final int ADDRESS = 1;

    /** WidgetCommandDelegate attribute. */
    protected WidgetCommandDelegate commandDelegate;

    /** MDK Widget implementation. */
    protected MDKPositionWidgetDelegate mdkWidgetDelegate;

    /** clear button. */
    private ImageButton clear;

    /** current position. */
    private Position position;

    /** double formatter. */
    private NumberFormat formatter;

    /** true if the data is being written by the command. */
    protected boolean writingData = false;

    /** notify change listeners. */
    private MDKChangeListenerDelegate mdkChangeListener;

    /** true if the location is being computed. */
    private boolean acquiringPosition = false;

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

        formatter = new DecimalFormat("#0.0000000");

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(this.getLayoutResource(), this);

        clear = (ImageButton) findViewById(R.id.component_internal_clear);
        clear.setOnClickListener(this);

        initDelegates(attrs);
    }

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

        // the action buttons enabled status should be processed by the component, not on validation
        this.commandDelegate = new WidgetCommandDelegate(this, attrs, false, false);

        this.commandDelegate.setCommandAnimation(WidgetCommandDelegate.FIRST_COMMAND, R.anim.locate_rotation);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.mdkWidgetDelegate.isAutoStart()) {
            this.startAcquisition(null);
        }
    }

    /**
     * Called when the view is attached to a window.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.commandDelegate.registerCommands(this);

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

            // Call validate to enable or not send button
            boolean isValid = this.mdkWidgetDelegate.validate(false, EnumFormFieldValidator.VALIDATE);
            // The command delegate enabling on validation may conflict with the component rules.
            // We set the enable status of the map button manually.
            this.commandDelegate.setEnabledCommand(WidgetCommandDelegate.SECOND_COMMAND, isValid);

            updateComponent();
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
        this.setLocation(location, false);

        updateComponent();
    }

    @Override
    public void locationChanged(Location location) {
        this.setLocation(location, false);

        updateComponent();
    }

    @Override
    public void locationFixed(Location location, int precision) {
        this.setLocation(location, true);

        if (precision == PositionWidgetCommand.FINE_ACCURACY_LEVEL) {
            this.commandDelegate.stopAnimationOnCommand(WidgetCommandDelegate.FIRST_COMMAND);
            this.acquiringPosition = false;
        }

        updateComponent();
    }

    @Override
    public void locationTimedOut() {
        ((Activity) this.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO: no toast...
                Toast.makeText(getContext(), getResources().getString(R.string.mdkwidget_mdkposition_locate_timeout), Toast.LENGTH_SHORT).show();

                MDKPosition.this.commandDelegate.stopAnimationOnCommand(WidgetCommandDelegate.FIRST_COMMAND);
                MDKPosition.this.acquiringPosition = false;

                updateComponent();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == clear.getId()) {
            this.clear();
        } else {
            WidgetCommand command = this.commandDelegate.getWidgetCommandById(v.getId());
            if (command instanceof PositionWidgetCommand) {
                startAcquisition(command);
            } else if (command instanceof MapWidgetCommand) {
                ((MapWidgetCommand) command).execute(this.getContext(), this.getLocation());
            }
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
            return new String[]{ this.position.getStringAddress() };
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

        updateComponent();
    }

    public Position getPosition() {
        return this.position;
    }

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

    @Override
    public void setLocation(Location location) {
        this.position.setPositionFromLocation(location);
    }

    /**
     * Sets the current location of the component.
     * @param location the {@link Location} to set
     * @param isAccurate true if the location is accurate
     */
    public void setLocation(Location location, boolean isAccurate) {
        this.writingData = true;

        String latitude = "";
        String longitude = "";

        if (location != null) {
            if (this.mdkWidgetDelegate.getMode() == MDKPosition.GEOPOINT) {
                this.position.setPositionFromLocation(location);
            } else if (!this.position.equals(location) || !this.position.hasAddresses()) {
                if (isAccurate) {
                    // this occurs when the fix is done, ie we have a precise location
                    List<Address> addresses = getAddresses(location);

                    if (addresses != null && addresses.size() > 0) {
                        // we have found a list of addresses, the user will pick one
                        this.position.setAddresses(addresses);
                        this.position.setSelectedAddress(1);

                        fillSpinner(addresses);
                    } else {
                        // no addresses were found
                        // TODO: autre...
                        Toast.makeText(getContext(), R.string.mdkwidget_mdkposition_noaddress, Toast.LENGTH_SHORT).show();
                    }
                }
                this.position.setPositionFromLocation(location);
            }

            latitude = formatter.format(location.getLatitude());
            latitude = latitude.replaceAll(",", ".");
            longitude = formatter.format(location.getLongitude());
            longitude = longitude.replaceAll(",", ".");
        } else {
            this.position.setPositionFromLocation(null);
        }

        this.mdkWidgetDelegate.getLatitudeView().setText(latitude);
        this.mdkWidgetDelegate.getLongitudeView().setText(longitude);

        updateComponent();

        this.writingData = false;
    }

    /**
     * Get the addresses from the location.
     * @param location the location to set
     * @return the addresses list
     */
    @Nullable
    private List<Address> getAddresses(Location location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;

        try {
            // TODO: parametrer le nombre d'addresses
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            if (addresses != null && addresses.size() > 0) {
                // we add an empty element
                addresses.add(0, null);
            }
        } catch (IOException e) {
            // TODO: a virer
            e.printStackTrace();
        }
        return addresses;
    }

    /**
     * Fills the spinner adapter and sets the selection.
     * @param addresses the addresses list
     */
    private void fillSpinner(List<Address> addresses) {
        if (addresses != null && addresses.size() > 0) {
            AddressSpinnerAdapter adapter = new AddressSpinnerAdapter(getContext(), R.layout.mdkwidget_position_layout_address_item, addresses);
            Spinner addrView = this.mdkWidgetDelegate.getAddressView();

            if (addrView != null) {
                addrView.setAdapter(adapter);
                addrView.setSelection(this.position.getSelectedAddress());
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
            try {
                double lat = Double.parseDouble(this.mdkWidgetDelegate.getLatitudeView().getText().toString());
                double lng = Double.parseDouble(this.mdkWidgetDelegate.getLongitudeView().getText().toString());

                position.setLatitude(lat);
                position.setLongitude(lng);
            } catch (NumberFormatException e) {
                // TODO a faire mieux
                e.printStackTrace();
            }
            validate(EnumFormFieldValidator.ON_USER);

            updateComponent();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // nothing to do
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position.setSelectedAddress(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Nothing to do
    }

    /**
     * Clears the widget content.
     */
    public void clear() {
        if (this.mdkWidgetDelegate.getMode() == ADDRESS) {
            this.position.setSelectedAddress(0);

            Spinner addrView = this.mdkWidgetDelegate.getAddressView();
            if (addrView != null) {
                addrView.setSelection(this.position.getSelectedAddress());
            }
        } else {
            this.setLocation(null, false);
        }

        updateComponent();
    }

    /**
     * Start the location acquisition.
     * @param command the location command (may be null)
     */
    protected void startAcquisition(WidgetCommand command) {
        if (this.commandDelegate != null) {
            if (command == null) {
                command = this.commandDelegate.getWidgetCommand(WidgetCommandDelegate.FIRST_COMMAND);
            }

            this.acquiringPosition = true;

            this.commandDelegate.startAnimationOnCommand(getContext(), WidgetCommandDelegate.FIRST_COMMAND);

            updateComponent();

            ((PositionWidgetCommand) command).execute(this.getContext(), this);
        }
    }

    /**
     * Changes the status of the sub components based on the state of MDKPosition.
     */
    protected void updateComponent() {
        boolean isValid = !this.position.isNull();

        /* latitude input text setEnable */
        this.mdkWidgetDelegate.getLatitudeView().setEnabled(
                isEnabled() && this.mdkWidgetDelegate.getMode() != ADDRESS
        );
        /* latitude input text setVisible */
        this.mdkWidgetDelegate.getLatitudeView().setVisibility(
                this.mdkWidgetDelegate.getMode() != ADDRESS || !this.position.hasAddresses()
                        ? View.VISIBLE : View.GONE
        );

        /* longitude input text setEnable */
        this.mdkWidgetDelegate.getLongitudeView().setEnabled(
                isEnabled() && this.mdkWidgetDelegate.getMode() != ADDRESS
        );
        /* longitude input text setVisible */
        this.mdkWidgetDelegate.getLongitudeView().setVisibility(
                this.mdkWidgetDelegate.getMode() != ADDRESS || !this.position.hasAddresses()
                        ? View.VISIBLE : View.GONE
        );

        /* address spinner setEnable */
        if (this.mdkWidgetDelegate.getAddressView() != null) {
            this.mdkWidgetDelegate.getAddressView().setEnabled(
                    isEnabled() && this.mdkWidgetDelegate.getMode() == ADDRESS && this.position.hasAddresses()
            );
            /* address spinner setVisible */
            this.mdkWidgetDelegate.getAddressView().setVisibility(
                    this.mdkWidgetDelegate.getMode() == ADDRESS && this.position.hasAddresses()
                            ? View.VISIBLE : View.GONE
            );
        }

        /* clear button setEnable */
        this.clear.setEnabled(
                isEnabled() && !acquiringPosition
        );

        /* location button setEnable */
        this.commandDelegate.setEnabledCommand(WidgetCommandDelegate.FIRST_COMMAND,
                isEnabled() && !acquiringPosition
        );
        /* location button setChecked */
        this.commandDelegate.setCheckedCommand(WidgetCommandDelegate.FIRST_COMMAND, isValid);

        /* map button setEnable */
        this.commandDelegate.setEnabledCommand(WidgetCommandDelegate.SECOND_COMMAND,
                isValid
        );
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

    @Override
    public WidgetCommandDelegate getWidgetCommandDelegate() {
        return this.commandDelegate;
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

        if (isValidated) {
            this.commandDelegate.setCheckedCommand(WidgetCommandDelegate.FIRST_COMMAND, true);
        }

        return isValidated;
    }

    @Override
    public boolean validate() {
        boolean isValidated = this.mdkWidgetDelegate.validate(true, EnumFormFieldValidator.VALIDATE);

        if (isValidated) {
            this.commandDelegate.setCheckedCommand(WidgetCommandDelegate.FIRST_COMMAND, true);
        }

        return isValidated;
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
        updateComponent();
    }

    /**
     * Sets whether the widget should display an action button to launch an external localization app.
     * @param activateGoto true if the widget should display an action button to launch an external localization app
     */
    public void setActivateGoto(boolean activateGoto) {
        this.mdkWidgetDelegate.setActivateGoto(activateGoto);
        updateComponent();
    }

    /**
     * Sets whether the widget should automatically start the localization on inflate.
     * @param autoStart true to start the localization on inflate
     */
    public void setAutoStart(boolean autoStart) {
        this.mdkWidgetDelegate.setAutoStart(autoStart);
        if (autoStart) {
            startAcquisition(null);
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

        this.fillSpinner(this.position.getAddresses());

        if (this.acquiringPosition) {
            this.commandDelegate.startAnimationOnCommand(getContext(), WidgetCommandDelegate.FIRST_COMMAND);
        }

        updateComponent();
    }
}
