package com.soprasteria.movalysmdk.widget.position;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.location.Location;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.WidgetCommandDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.position.command.MapWidgetCommand;
import com.soprasteria.movalysmdk.widget.position.command.PositionCommandListener;
import com.soprasteria.movalysmdk.widget.position.command.PositionWidgetCommand;
import com.soprasteria.movalysmdk.widget.position.delegate.MDKPositionWidgetDelegate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
 */
public class MDKPosition extends RelativeLayout implements MDKWidget, HasLocation, HasValidator, HasCommands, HasDelegate, HasChangeListener, PositionCommandListener {

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
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(this.getLayoutResource(), this);

        clear = (ImageButton) findViewById(R.id.component_internal_clear);
        clear.setOnClickListener(this);

        initDelegates(attrs);

        setMapButtonVisibility();
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

        // localization should always be activated, even on not validated widget
        this.commandDelegate = new WidgetCommandDelegate(this, attrs, false, true);

        if (this.mdkWidgetDelegate.isAutoStart()) {
            this.executePositionCommand();
        }
    }

    /**
     * Set the map action button visibility based on the activateGoto attribute.
     */
    public void setMapButtonVisibility() {
        if (this.mdkWidgetDelegate != null && !this.mdkWidgetDelegate.isActivateGoto() && this.commandDelegate != null) {
            this.commandDelegate.setCommandVisibility(WidgetCommandDelegate.SECOND_COMMAND, View.GONE);
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
            // Call the attachedToWindow method on delegate
            this.mdkWidgetDelegate.onAttachedToWindow();
            // Call validate to enable or not send button
            this.mdkWidgetDelegate.validate(false, EnumFormFieldValidator.VALIDATE);
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
    public void computingLocation(Location location) {
        this.mdkWidgetDelegate.setLocation(getContext(), location, false);
        this.commandDelegate.setCheckedCommand(WidgetCommandDelegate.FIRST_COMMAND, false);
    }

    @Override
    public void locationChanged(Location location) {
        this.mdkWidgetDelegate.setLocation(getContext(), location, false);
        this.commandDelegate.setCheckedCommand(WidgetCommandDelegate.FIRST_COMMAND, true);
    }

    @Override
    public void locationFixed(Location location) {
        this.mdkWidgetDelegate.setLocation(getContext(), location, true);
        this.commandDelegate.setCheckedCommand(WidgetCommandDelegate.FIRST_COMMAND, true);
    }

    @Override
    public void locationTimedOut() {
        ((Activity) this.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), getResources().getString(R.string.mdkwidget_mdkposition_locate_timeout), Toast.LENGTH_SHORT).show();
                MDKPosition.this.commandDelegate.setCheckedCommand(WidgetCommandDelegate.FIRST_COMMAND, false);
                MDKPosition.this.mdkWidgetDelegate.resetViews();
            }
        });
    }

    @Override
    public String[] getCoordinates() {
        return this.mdkWidgetDelegate.getCoordinates();
    }

    @Override
    public Location getLocation() {
        return this.mdkWidgetDelegate.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        this.mdkWidgetDelegate.setLocation(getContext(), location, true);
    }

    @Override
    public void setLatitudeHint(String latHint) {
        this.mdkWidgetDelegate.setLatitudeHint(latHint);
    }

    @Override
    public void setLongitudeHint(String lngHint) {
        this.mdkWidgetDelegate.setLongitudeHint(lngHint);
    }

    // TODO : setAdressHint...?

    @Override
    public void onClick(View v) {
        if (v.getId() == clear.getId()) {
            this.mdkWidgetDelegate.clear();
        } else {
            WidgetCommand command = this.commandDelegate.getWidgetCommandById(v.getId());
            if (command instanceof PositionWidgetCommand) {
                ((PositionWidgetCommand) command).execute(this.getContext(), this);
            } else if (command instanceof MapWidgetCommand) {
                ((MapWidgetCommand) command).execute(this.getContext(), this.mdkWidgetDelegate.getLocation());
            }
        }
    }

    /**
     * Executes the localization command.
     */
    public void executePositionCommand() {
        WidgetCommand command = this.commandDelegate.getWidgetCommand(WidgetCommandDelegate.FIRST_COMMAND);
        ((PositionWidgetCommand) command).execute(this.getContext(), this);
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
        this.mdkWidgetDelegate.setEnabled(enabled);
    }

    /**
     * Sets the mode of the widget.
     * @param mode the mode to set
     */
    public void setMode(@PositionMode int mode) {
        this.mdkWidgetDelegate.setMode(mode);
    }

    /**
     * Sets whether the widget should display an action button to launch an external localization app.
     * @param activateGoto true if the widget should display an action button to launch an external localization app
     */
    public void setActivateGoto(boolean activateGoto) {
        this.mdkWidgetDelegate.setActivateGoto(activateGoto);
    }

    /**
     * Sets whether the widget should automatically start the localization on inflate.
     * @param autoStart true to start the localization on inflate
     */
    public void setAutoStart(boolean autoStart) {
        this.mdkWidgetDelegate.setAutoStart(autoStart);
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
        this.mdkWidgetDelegate.registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkWidgetDelegate.unregisterChangeListener(listener);
    }

    /* save / restore */

    @Override
    public Parcelable onSaveInstanceState() {
        // Save the android view instance state
        Parcelable state = super.onSaveInstanceState();
        // Save the MDKWidgetDelegate instance state
        state = this.mdkWidgetDelegate.onSaveInstanceState(state);

        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        // Restore the MDKWidgetDelegate instance state
        Parcelable innerState = this.mdkWidgetDelegate.onRestoreInstanceState(this, state);
        // Restore the android view instance state
        super.onRestoreInstanceState(innerState);
    }
}
