package com.soprasteria.movalysmdk.widget.positionmaps;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.soprasteria.movalysmdk.widget.positionmaps.delegate.MDKMapsPositionWidgetDelegate;

import java.io.IOException;
import java.io.InputStream;

/**
 * MDK Static Maps Position.
 * Aims at being the readonly version of the Maps component.
 * This behaves similarly to the later, but only displays an image of the selected location instead of a proper MapView.
 * Please refer to {@link MDKMapsPosition} for more details.
 */
public class MDKStaticMapsPosition extends MDKMapsPosition {


    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     */
    public MDKStaticMapsPosition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public MDKStaticMapsPosition(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * Initialization.
     * @param attrs   the layout attributes
     */
    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);
        setReadonly(true);
        typedArray.recycle();
    }

    @Override
    @LayoutRes
    protected int getLayoutResource() {
        return R.layout.mdkwidget_staticpositionmaps_layout;
    }

    @Override
    protected void updateOnMapDisplay() {
        ImageView mapView = (ImageView) ((MDKMapsPositionWidgetDelegate) getMDKWidgetDelegate()).getMapView();

        if (mapView != null) {
            getGoogleMapThumbnail(
                    getLocation().getLatitude(), getLocation().getLongitude(),
                    ((MDKMapsPositionWidgetDelegate) getMDKWidgetDelegate()).getZoom(),
                    mapView
                    );
        }
    }

    /**
     * Initialize the delegates of the widget.
     * @param attrs the xml attributes of the widget
     */
    @Override
    protected void initDelegates(AttributeSet attrs) {
        this.mdkWidgetDelegate = new MDKMapsPositionWidgetDelegate(this, attrs);
    }

    /**
     * Retrieves the thumbnail from google static maps api and displays it.
     * @param lat latitude
     * @param lng longitude
     * @param zoom zoom level
     * @param mapView the mapview to update
     */
    private void  getGoogleMapThumbnail(double lat, double lng, int zoom, final ImageView mapView) {

        //Maps static api limit is 640px
        int width = 640;
        int height = 360;

        final String imageFileURL = "http://maps.google.com/maps/api/staticmap?center="
                + lat + "," + lng + "&zoom=" + zoom + "&size=" + width + "x" + height + "&sensor=false" + "&markers=" + lat + "," + lng;

        new AsyncTask<String,Void,Bitmap>(){
            @Override
            protected Bitmap doInBackground(String[] params) {
                Bitmap bmp = null;
                try {
                    InputStream in = new java.net.URL(params[0]).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (IOException e) {
                    Log.e(getClass().getSimpleName(),"Unable to retrieve map thumbnail",e);
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                mapView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,getContext().getResources().getDimensionPixelSize(R.dimen.map_thumbnail_height)*16/9,getContext().getResources().getDimensionPixelSize(R.dimen.map_thumbnail_height),true));
            }
        }.execute(imageFileURL);

    }

}
