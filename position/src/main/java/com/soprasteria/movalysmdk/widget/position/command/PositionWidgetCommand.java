package com.soprasteria.movalysmdk.widget.position.command;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.soprasteria.movalysmdk.widget.core.command.AsyncWidgetCommand;
import com.soprasteria.movalysmdk.widget.core.helper.ApplicationPermissionHelper;
import com.soprasteria.movalysmdk.widget.core.listener.AsyncWidgetCommandListener;
import com.soprasteria.movalysmdk.widget.position.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Primary command class for the position widget.
 * Will try and locate the device and update the widget consequently
 */
public class PositionWidgetCommand implements AsyncWidgetCommand<AsyncWidgetCommandListener, Void> {

    /** update timer value. */
    private static final int UPDATE_TIMER = 500;

    /** coarse accuracy level. */
    public static final int COARSE_ACCURACY_LEVEL = 1000;
    /** fine accuracy level. */
    public static final int FINE_ACCURACY_LEVEL = 50;

    /** time out error. */
    public static final int TIME_OUT = 0;

    /** no gps error. */
    public static final int NO_GPS = 1;

    /** android context. */
    private Context context;

    /** the location manager. */
    private LocationManager locationManager;

    /** the two listeners. */
    private LocationListener oListenerFine, oListenerCoarse;

    /** current location. */
    private Location location;

    /** true if a location is available. */
    private boolean locationAvailable;

    /** callback commandListener. */
    private WeakReference<AsyncWidgetCommandListener> commandListener;

    /** timer for timeout management. */
    private Timer timerTimeout = new Timer();

    /**
     * Send an email using the email parameters.
     * <p>This method call the ACTION_SEND Intent.</p>
     *
     * @param context the Android context
     * @param listener the listeners of the command
     * @return null
     */
    @Override
    public Void execute(Context context, AsyncWidgetCommandListener listener) {
        this.context = context;
        this.commandListener = new WeakReference<>(listener);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        registerLocationListeners();

        start();

        return null;
    }

    @Override
    public AsyncWidgetCommandListener getListener() {
        return this.commandListener.get();
    }

    @Override
    public void setListener(AsyncWidgetCommandListener listener) {
        if (listener == null) {
            this.commandListener = null;
        } else {
            this.commandListener = new WeakReference<>(listener);
        }
    }

    /**
     * Starts the location command.
     */
    private void start() {
        AsyncWidgetCommandListener listener = this.commandListener.get();
        if (listener != null) {
            listener.onStart(location);


            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Criteria oFine = new Criteria();
                oFine.setAccuracy(Criteria.ACCURACY_FINE);

                // Will keep updating about every 500 ms until
                // accuracy is about 50 meters to get accurate fix.
                ApplicationPermissionHelper.checkPermission(context, listener.getMDKWidgetDelegate(), R.string.mdkcommand_position_error_permission);
                locationManager.requestLocationUpdates(locationManager.getBestProvider(oFine, true), UPDATE_TIMER, FINE_ACCURACY_LEVEL, oListenerFine);
            }

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Criteria oCoarse = new Criteria();
                oCoarse.setAccuracy(Criteria.ACCURACY_COARSE);

                // Will keep updating about every 500 ms until
                // accuracy is about 1000 meters to get accurate fix.
                ApplicationPermissionHelper.checkPermission(context, listener.getMDKWidgetDelegate(), R.string.mdkcommand_position_error_permission);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_TIMER, COARSE_ACCURACY_LEVEL, oListenerCoarse);
            }


            timerTimeout.schedule(new TimerTask() {
                @Override
                public void run() {

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AsyncWidgetCommandListener listener = PositionWidgetCommand.this.commandListener.get();
                            if (listener != null) {
                                listener.onError(TIME_OUT);
                            }
                        }
                    });

                    PositionWidgetCommand.this.cancel();
                    cancel();
                }
            }, listener.getTimeOut() * 1000);
        }
    }

    /**
     * Stops the location command.
     */
    @Override
    public void cancel() {
        if (timerTimeout != null) {
            timerTimeout.cancel();
            timerTimeout.purge();
            timerTimeout = null;
        }

        AsyncWidgetCommandListener listener = null;
        if (PositionWidgetCommand.this.commandListener != null) {
            listener = PositionWidgetCommand.this.commandListener.get();
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (listener != null) {
                ApplicationPermissionHelper.checkPermission(context, listener.getMDKWidgetDelegate(), R.string.mdkcommand_position_error_permission);
            }
            locationManager.removeUpdates(oListenerFine);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (listener != null) {
                ApplicationPermissionHelper.checkPermission(context, listener.getMDKWidgetDelegate(), R.string.mdkcommand_position_error_permission);
            }
            locationManager.removeUpdates(oListenerCoarse);
        }
    }

    /**
     * Updates the dialog ui to use the last currentLocation.
     */
    private void onCurrentLocationChange() {
        AsyncWidgetCommandListener listener = commandListener.get();
        if (location != null && listener != null) {
            listener.onUpdate(location);
        }
    }

    /**
     * registers the 2 commandListener on Coarse and Fine location.
     */
    private void registerLocationListeners() {
        AsyncWidgetCommandListener listener = commandListener.get();

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Initialize criteria for location providers
        Criteria oFine = new Criteria();
        oFine.setAccuracy(Criteria.ACCURACY_FINE);
        Criteria oCoarse = new Criteria();
        oCoarse.setAccuracy(Criteria.ACCURACY_COARSE);

        // Get at least something from the device,
        // could be very inaccurate though
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (listener != null) {
                ApplicationPermissionHelper.checkPermission(context, listener.getMDKWidgetDelegate(), R.string.mdkcommand_position_error_permission);
            }
            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(oFine, true));
            this.onCurrentLocationChange();
        } else {

            if (listener != null) {
                listener.onError(NO_GPS);
            }
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (listener != null) {
                ApplicationPermissionHelper.checkPermission(context, listener.getMDKWidgetDelegate(), R.string.mdkcommand_position_error_permission);
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            this.onCurrentLocationChange();
        }

        if (oListenerFine == null || oListenerCoarse == null) {
            createLocationListeners();
        }
    }

    /**
     * Creates LocationListeners.
     */
    private void createLocationListeners() {
        oListenerCoarse = new PositionWidgetLocationListener(COARSE_ACCURACY_LEVEL);

        oListenerFine = new PositionWidgetLocationListener(FINE_ACCURACY_LEVEL);
    }

    /**
     * called when the fine location get the defined accuracy.
     * @param precision the precision of the location found
     */
    private void locationOk(int precision) {
        AsyncWidgetCommandListener listener = this.commandListener.get();
        if (listener != null) {
            if (precision == PositionWidgetCommand.FINE_ACCURACY_LEVEL) {
                listener.onFinish(location);
                cancel();
            } else {
                listener.onUpdate(location);
            }
        }
    }

    /**
     * Returns true if a location has been made available.
     * @return true if a location has been made available
     */
    public boolean isLocationAvailable() {
        return locationAvailable;
    }

    /**
     * Location listeners.
     */
    private class PositionWidgetLocationListener implements LocationListener {

        /** the desired location precision. */
        private int precision;

        /**
         * Constructor.
         * @param precision the desired location precision
         */
        public PositionWidgetLocationListener(int precision) {
            this.precision = precision;
        }

        @Override
        public void onLocationChanged(Location location) {
            PositionWidgetCommand.this.location = location;
            PositionWidgetCommand.this.onCurrentLocationChange();
            // we check timeout != null because after removeUpdates, we may get some other calls to onLocationChanged
            // we do not want that on the MDK components listening to that action
            if (location != null && location.getAccuracy() < precision && location.hasAccuracy() && timerTimeout != null) {
                AsyncWidgetCommandListener listener = PositionWidgetCommand.this.commandListener.get();
                if (listener != null) {
                    ApplicationPermissionHelper.checkPermission(context, listener.getMDKWidgetDelegate(), R.string.mdkcommand_position_error_permission);
                }
                locationManager.removeUpdates(this);
                PositionWidgetCommand.this.locationOk(precision);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.OUT_OF_SERVICE:
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    PositionWidgetCommand.this.locationAvailable = false;
                    break;
                case LocationProvider.AVAILABLE:
                    PositionWidgetCommand.this.locationAvailable = true;
                    break;
                default:
                    PositionWidgetCommand.this.locationAvailable = false;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            // nothing to do
        }

        @Override
        public void onProviderDisabled(String provider) {
            // nothing to do
        }
    }
}
