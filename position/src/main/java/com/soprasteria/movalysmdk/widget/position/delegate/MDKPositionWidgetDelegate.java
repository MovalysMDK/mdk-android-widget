package com.soprasteria.movalysmdk.widget.position.delegate;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.AttributesHelper;
import com.soprasteria.movalysmdk.widget.position.MDKPosition;
import com.soprasteria.movalysmdk.widget.position.R;

import java.lang.ref.WeakReference;

/**
 * Delegate for the MDKPositionWidgetDelegate widget.
 */
public class MDKPositionWidgetDelegate extends MDKWidgetDelegate {

    /** Latitude hint.*/
    private String latHint;

    /** Longitude hint.*/
    private String lngHint;

    /** latitude view identifier. */
    private int latitudeViewId;

    /** longitude view identifier. */
    private int longitudeViewId;

    /** address view identifier. */
    private int addressViewId;

    /** location info view identifier. */
    private int locationInfoViewId;

    /** address info view identifier. */
    private int addressInfoViewId;

    /** locate button identifier. */
    private int locateButtonId;

    /** maps app open button identifier. */
    private int mapsButtonId;

    /** navigation app open button identifier. */
    private int navButtonId;

    /** clear button identifier. */
    private int clearButtonId;

    /** navigation button on locate animation. */
    private int navButtonAnimationId;

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

    /** address view. */
    private WeakReference<TextView> locationInfoView;

    /** address view. */
    private WeakReference<TextView> addressInfoView;

    /** the time out in seconds to set on the location manager. */
    private int timeout;

    /**
     * Constructor.
     * @param root the root view
     * @param attrs the parameters set
     */
    public MDKPositionWidgetDelegate(ViewGroup root, AttributeSet attrs) {
        super(root, attrs);

        // Position specific fields parsing
        TypedArray typedArray = root.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKPositionComponent);

        latitudeViewId = AttributesHelper.getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKPositionComponent_latitudeTextViewId,
                R.id.component_internal_latitude);

        final EditText latView = (EditText) root.findViewById(latitudeViewId);

        latitudeView = new WeakReference<>(latView);

        longitudeViewId = AttributesHelper.getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKPositionComponent_longitudeTextViewId,
                R.id.component_internal_longitude);

        final EditText lngView = (EditText) root.findViewById(longitudeViewId);

        longitudeView = new WeakReference<>(lngView);

        addressViewId = AttributesHelper.getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKPositionComponent_addressSpinnerViewId,
                R.id.component_internal_address);

        final Spinner addrView = (Spinner) root.findViewById(addressViewId);

        if (addrView != null) {
            addressView = new WeakReference<>(addrView);
        }

        locationInfoViewId = AttributesHelper.getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKPositionComponent_locationInfoViewId,
                R.id.component_internal_info_location);

        final TextView locInfoView = (TextView) root.findViewById(locationInfoViewId);

        if (locInfoView != null) {
            locationInfoView = new WeakReference<>(locInfoView);
        }

        addressInfoViewId = AttributesHelper.getIntFromResourceAttribute(typedArray, R.styleable.MDKCommons_MDKPositionComponent_addressInfoViewId,
                R.id.component_internal_info_address);

        final TextView addrInfoView = (TextView) root.findViewById(addressInfoViewId);

        if (addrInfoView != null) {
            addressInfoView = new WeakReference<>(addrInfoView);
        }

        locateButtonId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_locateButtonViewId, 0);
        mapsButtonId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_mapsButtonViewId, 0);
        navButtonId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_navButtonViewId, 0);
        clearButtonId = this.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons).getResourceId(R.styleable.MDKCommons_clearButtonViewId, 0);

        this.timeout = typedArray.getInt(R.styleable.MDKCommons_MDKPositionComponent_timeout, 10);

        this.autoStart = typedArray.getBoolean(R.styleable.MDKCommons_MDKPositionComponent_autoStart, false);

        this.setActivateGoto(typedArray.getBoolean(R.styleable.MDKCommons_MDKPositionComponent_activeGoto, true));

        latHint = AttributesHelper.getStringFromStringAttribute(typedArray, R.styleable.MDKCommons_MDKPositionComponent_latHint,
                root.getContext().getString(R.string.mdkwidget_mdkposition_latitude_hint));

        lngHint = AttributesHelper.getStringFromStringAttribute(typedArray, R.styleable.MDKCommons_MDKPositionComponent_lngHint,
                root.getContext().getString(R.string.mdkwidget_mdkposition_longitude_hint));

        if (latView != null) {
            latView.setHint(latHint);
        }
        if (lngView != null) {
            lngView.setHint(lngHint);
        }

        if (this.mode == -1) {
            this.mode = typedArray.getInt(R.styleable.MDKCommons_MDKPositionComponent_positionMode, MDKPosition.GEOPOINT);
        }

        navButtonAnimationId = typedArray.getResourceId(R.styleable.MDKCommons_MDKPositionComponent_locateButtonAnimationId, 0);

        typedArray.recycle();
    }

    /**
     * Returns the mode of the widget.
     * @return the mode of the widget
     */
    public int getMode() {
        return this.mode;
    }

    /**
     * Sets the mode of the widget.
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * sets the timeout to apply on the location action.
     * @param timeout the timeout to set
     */
    public void setTimeOut(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Returns the latitude input view.
     * @return the latitude input view
     */
    public EditText getLatitudeView() {
        if (this.latitudeViewId != 0) {
            return this.latitudeView.get();
        }
        return null;
    }

    /**
     * Returns the longitude input view.
     * @return the longitude input view
     */
    public EditText getLongitudeView() {
        if (this.longitudeViewId != 0) {
            return this.longitudeView.get();
        }
        return null;
    }

    /**
     * Returns the address spinner.
     * @return the address spinner
     */
    public Spinner getAddressView() {
        if (this.addressView != null) {
            return this.addressView.get();
        }
        return null;
    }

    /**
     * Returns the location info view.
     * @return the location info view
     */
    public TextView getLocationInfoView() {
        if (this.locationInfoView != null) {
            return this.locationInfoView.get();
        }
        return null;
    }

    /**
     * Returns the address info view.
     * @return the address info view
     */
    public TextView getAddressInfoView() {
        if (this.addressInfoView != null) {
            return this.addressInfoView.get();
        }
        return null;
    }

    /**
     * Returns the identifier of the locate button.
     * @return the identifier of the locate button
     */
    public int getLocateButtonId() {
        return this.locateButtonId;
    }

    /**
     * Sets the identifier of the locate button.
     * @param locateButtonId the identifier to set
     */
    public void setLocateButtonId(int locateButtonId) {
        this.locateButtonId = locateButtonId;
    }

    /**
     * Returns the locate button.
     * @return the locate button
     */
    public View getLocateButton() {
        if (this.locateButtonId != 0) {
            return reverseFindViewById(this.locateButtonId);
        }
        return null;
    }

    /**
     * Returns the identifier of the maps button.
     * @return the identifier of the maps button
     */
    public int getMapsButtonId() {
        return this.mapsButtonId;
    }

    /**
     * Returns the maps button.
     * @return the maps button
     */
    public View getMapsButton() {
        if (this.mapsButtonId != 0) {
            return reverseFindViewById(this.mapsButtonId);
        }
        return null;
    }

    /**
     * Returns the identifier of the nav button.
     * @return the identifier of the nav button
     */
    public int getNavButtonId() {
        return this.navButtonId;
    }

    /**
     * Returns the nav button.
     * @return the nav button
     */
    public View getNavButton() {
        if (this.navButtonId != 0) {
            return reverseFindViewById(this.navButtonId);
        }
        return null;
    }

    /**
     * Returns the identifier of the clear button.
     * @return the identifier of the clear button
     */
    public int getClearButtonId() {
        return this.clearButtonId;
    }

    /**
     * Returns the clear button.
     * @return the clear button
     */
    public View getClearButton() {
        if (this.clearButtonId != 0) {
            return reverseFindViewById(this.clearButtonId);
        }
        return null;
    }

    /**
     * Returns the animation set for location command button.
     * @return the animation for location command
     */
    public int getLocateButtonAnimationId() {
        return navButtonAnimationId;
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
    }

    /**
     * Returns true if the widget should display an action button to launch an external localization app.
     * @return true if the widget should display an action button to launch an external localization app
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
    }

    /**
     * Returns the timeout set for location action.
     * @return the timeout set for location action
     */
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

        return bundle;
    }

    @Override
    public Parcelable onRestoreInstanceState(View view, Parcelable state) {
        Bundle bundle = (Bundle) state;

        // Restore the widget
        Parcelable parcelable = bundle.getParcelable("state");

        // Restore the android view instance state
        return super.onRestoreInstanceState(view, parcelable);
    }
}
