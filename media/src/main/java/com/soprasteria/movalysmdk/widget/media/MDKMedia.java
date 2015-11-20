/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
package com.soprasteria.movalysmdk.widget.media;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.types.HasMedia;
import com.soprasteria.movalysmdk.widget.core.behavior.types.IsNullable;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.helper.ActivityHelper;
import com.soprasteria.movalysmdk.widget.core.helper.AttributesHelper;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentActionHandler;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentActionHelper;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Widget for choosing a media.
 * Three different types of media:
 * <ul>
 *     <li>Photos</li>
 *     <li>Videos (Not yet implemented)</li>
 *     <li>Files (Not yet implemented)</li>
 * </ul>
 * <ul>For photos and videos:
 *      <li>Displays a thumbnail with a placeholder</li>
 *      <li>Upon click, asks the user to choose between:
 *          <ul>
 *              <li>Taking a photo/video with the camera app</li>
 *              <li>Picking a photo/video from the gallery app</li>
 *          </ul>
 *      </li>
 *      <li>Then displays the photo inside the ImageView</li>
 * </ul>
 * <ul>For files:
 *      <li>Displays a textview with a file chooser command button</li>
 *      <li>Opens a new activity for choosing a file</li>
 *      <li>Then displays the photo inside the ImageView</li>
 * </ul>
 */
public class MDKMedia extends RelativeLayout implements MDKWidget, HasDelegate, HasValidator, HasMedia, IsNullable, View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener, MDKWidgetComponentActionHandler {

    /** Alpha applied when component is disabled. */
    private static final int DISABLED_ALPHA = 70;

    /** Media type constant for photo. **/
    public static final int TYPE_PHOTO = 0;

    /** Media type constant for photo. **/
    public static final int TYPE_VIDEO = 1;

    /** Media type constant for photo. **/
    public static final int TYPE_FILE = 2;

    /** Reference
    private WeakReference<AlertDialog> fullPhotoDialog;

    /**
     * Enumeration listing possible MDKMedia modes.
     */
    @IntDef({TYPE_PHOTO,TYPE_VIDEO,TYPE_FILE})
    @Retention(RetentionPolicy.SOURCE)
    @interface MediaType {
    }

    /** The thumbnail ImageView. **/
    private WeakReference<ImageView> thumbnail;

    /** The picker overlay. **/
    private WeakReference<View> overlay;

    /** The default widget delegate. */
    private MDKWidgetDelegate mdkWidgetDelegate;

    /** Media type. **/
    @MediaType private int mediaType;

    /** The widget's media file's uri. **/
    private Uri rawMediaUri;

    /** The widget's modified media file's uri. **/
    private Uri modifiedMediaUri;

    /** The widget's modified media svg layer. **/
    private String svgLayer;

    /** Temporary uri to use when capturing a media. **/
    private Uri tempFileUri;

    /** Thumbnail placeholder drawable resource. **/
    private int placeholderRes;

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     */
    public MDKMedia(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context,attrs);
        }
    }


    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes
     * @param defStyleAttr the style
     */
    public MDKMedia(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Instanciates the widget delegate and initializes the attributes.
     * @param context the context
     * @param attrs attributes
     */
    private void init(Context context, AttributeSet attrs) {

        // Create the widget delegate
        mdkWidgetDelegate = new MDKWidgetDelegate(this, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(this.getLayoutResource(), this);

        this.thumbnail = new WeakReference<>((ImageView) findViewById(R.id.thumbnail));
        this.overlay = new WeakReference<>(findViewById(R.id.overlay));

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKMediaComponent);

        switch(AttributesHelper.getIntFromIntAttribute(typedArray, R.styleable.MDKCommons_MDKMediaComponent_media_type, TYPE_PHOTO)){
            case TYPE_PHOTO:
                setMediaType(TYPE_PHOTO);
                break;
            case TYPE_VIDEO:
                setMediaType(TYPE_VIDEO);
                break;
            case TYPE_FILE:
                setMediaType(TYPE_FILE);
                break;
            default:
                break;
        }

        setOverlayEnabled(isEnabled() && !isReadonly());

        setPlaceholder(AttributesHelper.getIntFromIntAttribute(typedArray, R.styleable.MDKCommons_MDKMediaComponent_placeholder, 0));

        typedArray.recycle();

        findViewById(R.id.overlay).setOnClickListener(this);
        this.setOnCreateContextMenuListener(this);
    }


    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();

        //register as handler
        MDKWidgetComponentActionHelper helper = ((MDKWidgetApplication)getContext().getApplicationContext()).getMDKWidgetComponentActionHelper();
        helper.registerActivityResultHandler(mdkWidgetDelegate.getUniqueId(), this);
    }

    @Override
    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();

        //unregister as handler
        MDKWidgetComponentActionHelper helper = ((MDKWidgetApplication)getContext().getApplicationContext()).getMDKWidgetComponentActionHelper();
        helper.unregisterActivityResultHandler(mdkWidgetDelegate.getUniqueId());
    }

    /**
     * Returns the thumbnail view.
     * @return the thumbnail view
     */
    public ImageView getThumbnailView() {
        return this.thumbnail.get();
    }

    /**
     * Returns the thumbnail view.
     * @return the thumbnail view
     */
    public View getOverlayView() {
        return this.overlay.get();
    }


    /**
     * Returns the layout of the widget.
     * @return the layout of the widget
     */
    @LayoutRes
    protected int getLayoutResource() {
        return R.layout.mdkwidget_layout;
    }

    @Override
    public MDKWidgetDelegate getMDKWidgetDelegate() {
        return mdkWidgetDelegate;
    }

    @Override
    public int[] getValidators() {
        return new int[] {R.string.mdkvalidator_mandatory_class};
    }

    @Override
    public boolean validate() {
        return validate(EnumFormFieldValidator.VALIDATE);
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return mdkWidgetDelegate.validate(true, validationMode);
    }

    @Override
    public void setError(CharSequence error) {
        mdkWidgetDelegate.setError(error);
    }

    @Override
    public void addError(MDKMessages error) {
        mdkWidgetDelegate.addError(error);
    }

    @Override
    public void clearError() {
        mdkWidgetDelegate.clearError();
    }

    @Override
    public Object getValueToValidate() {
        return rawMediaUri;
    }

    @Override
    public MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate() {
        return mdkWidgetDelegate;
    }

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return new int[0];
    }

    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mdkWidgetDelegate.callMergeDrawableStates(baseState, additionalState);
    }

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return mdkWidgetDelegate;
    }

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
        setOverlayEnabled(isEnabled() && !readonly);
    }

    @Override
    public boolean isReadonly() {
        return mdkWidgetDelegate.isReadonly();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        ImageView thumbnailView = getThumbnailView();
        if (thumbnailView != null) {
            thumbnailView.setAlpha(isEnabled() ? 255 : DISABLED_ALPHA);
        }
        setOverlayEnabled(enabled && !isReadonly());
    }

    @Override
    public void onClick(View v) {
        if(!isReadonly() && isEnabled() && rawMediaUri==null) {
                showContextMenu();
        }else if(!isReadonly() && isEnabled()) {
            Uri mediaUri = modifiedMediaUri!=null?modifiedMediaUri:rawMediaUri;
            try {
                ImageView iv = new ImageView(getContext());
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                iv.setAdjustViewBounds(true);
                iv.setImageBitmap(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mediaUri));

                AlertDialog ad = new AlertDialog.Builder(getContext()).create();
                ad.getWindow().setLayout(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
                ad.setView(iv);
                ad.setButton(AlertDialog.BUTTON_POSITIVE,getResources().getString(R.string.mdkwidget_mdkmedia_edit_photo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //Starting photo edition
                        Intent drawingIntent = new Intent(getContext(), DrawingLayoutActivity.class);
                        drawingIntent.putExtra(DrawingLayoutActivity.REQUEST_URI_KEY, rawMediaUri);
                        drawingIntent.putExtra(DrawingLayoutActivity.REQUEST_SVG_KEY, svgLayer);
                        ActivityHelper.startActivityForResult(getContext(), drawingIntent, mdkWidgetDelegate.getUniqueId());

                        dialog.dismiss();
                    }
                });
                ad.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.mdkwidget_mdkmedia_remove_photo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        reset();
                        dialog.dismiss();
                    }
                });
                ad.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.mdkwidget_mdkmedia_close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                ad.show();
            } catch (IOException e) {
                Log.w(this.getClass().getSimpleName(), "Error trying to access file: " + mediaUri, e);
            }
        }
    }

    @Override
    public boolean showContextMenu() {
        return !isReadonly() && isEnabled() && super.showContextMenu();
    }

    /**
     * Enables or disables the overlay.
     * @param enabled the enabled property of the overlay
     */
    private void setOverlayEnabled(boolean enabled){
        View v = getOverlayView();
        if(v!=null){
            if(!enabled) {
                v.setVisibility(GONE);
            }else{
                v.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        switch (this.mediaType) {
            case TYPE_PHOTO:
                initPhotoMenu(menu);
                break;
            case TYPE_VIDEO:
                initVideoMenu(menu);
                break;
            case TYPE_FILE:
                break;
            default:
                break;
        }
    }

    /**
     * Initializes the photo context menu.
     * @param menu the menu to initialize
     */
    private void initPhotoMenu(ContextMenu menu){
        menu.add(0, 0, 1, R.string.mdkwidget_mdkmedia_take_photo).setOnMenuItemClickListener(this);
        menu.add(0, 1, 2, R.string.mdkwidget_mdkmedia_choose_photo).setOnMenuItemClickListener(this);
    }

    /**
     * Initializes the video context menu.
     * @param menu the menu to initialize
     */
    private void initVideoMenu(ContextMenu menu){
        menu.add(0, 2, 1, R.string.mdkwidget_mdkmedia_take_video).setOnMenuItemClickListener(this);
        menu.add(0, 3, 2, R.string.mdkwidget_mdkmedia_choose_video).setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                tempFileUri = getOutputMediaFileUri(getContext(),TYPE_PHOTO);
                if(tempFileUri !=null) {
                    ActivityHelper.startActivityForResult(getContext(), new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri), mdkWidgetDelegate.getUniqueId());
                }
                break;
            case 1:
                Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ActivityHelper.startActivityForResult(getContext(), pickPhotoIntent, mdkWidgetDelegate.getUniqueId());
                break;
            case 2:
                tempFileUri = getOutputMediaFileUri(getContext(),TYPE_VIDEO);
                if(tempFileUri !=null) {
                    ActivityHelper.startActivityForResult(getContext(), new Intent(MediaStore.ACTION_VIDEO_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri), mdkWidgetDelegate.getUniqueId());
                }
                break;
            case 3:
                Intent pickVideoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                ActivityHelper.startActivityForResult(getContext(), pickVideoIntent, mdkWidgetDelegate.getUniqueId());
                break;
            default:
                break;
        }

        return false;
    }

    /**
     * Restores the widget to its initial state.
     */
    private void reset() {
        rawMediaUri =null;
        modifiedMediaUri = null;
        svgLayer = null;

        if(getThumbnailView()!=null) {
            getThumbnailView().setImageDrawable(ContextCompat.getDrawable(getContext(),placeholderRes));
        }
    }

    @Override
    public void handleActivityResult(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){

            if(data!=null) {

                String action = data.getAction();

                if (action != null && action.equals(DrawingLayoutActivity.DRAWING_ACTION)) {
                    handleDrawingActivityResult(data);
                    return;
                }
            }

            switch (this.mediaType) {
                case TYPE_PHOTO:
                    handlePhotoActivityResult(data);
                    break;
                case TYPE_VIDEO:
                    break;
                case TYPE_FILE:
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles the result of a drawing activity (bitmap and svg),
     * stores it and updates the thumbnail.
     * @param data the activity result data
     */
    private void handleDrawingActivityResult(Intent data) {
        if((data.getParcelableExtra(DrawingLayoutActivity.RESULT_URI_KEY)).equals(rawMediaUri)){
            modifiedMediaUri = null;
            updateThumbnail(rawMediaUri);
        }else{

            svgLayer = data.getStringExtra(DrawingLayoutActivity.RESULT_SVG_KEY);
            modifiedMediaUri = data.getParcelableExtra(DrawingLayoutActivity.RESULT_URI_KEY);

            updateThumbnail(modifiedMediaUri);
        }
    }

    /**
     * Reads the result of the photo picking/capturing activity,
     * stores it as the widget's mediaUri and displays thumbnail.
     * @param data the activity result data
     */
    private void handlePhotoActivityResult(Intent data) {
        Uri imageUri;

        //intent is null when in the case of camera app result
        //so we have to take the uri we previously stored
        if(data!=null){
            imageUri = data.getData();
        }else if(tempFileUri!=null){
            imageUri=tempFileUri;
            tempFileUri=null;
        }else{
            return;
        }

        setMediaUri(imageUri);

        //Starting photo edition
        Intent drawingIntent = new Intent(getContext(),DrawingLayoutActivity.class);
        drawingIntent.putExtra(DrawingLayoutActivity.REQUEST_URI_KEY, imageUri);

        ActivityHelper.startActivityForResult(getContext(), drawingIntent, mdkWidgetDelegate.getUniqueId());
    }


    /**
     * Creates a file to store the captured media.
     * @param context the context
     * @param type media type
     * @return the created file's uri
     */
    public static Uri getOutputMediaFileUri(Context context, int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        String dirName;
        if(context.getApplicationInfo().labelRes!=0){
            dirName = context.getString(context.getApplicationInfo().labelRes).replace(" ","_");
        }else{
            dirName=context.getClass().getSimpleName();
        }
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), dirName);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(context.getClass().getSimpleName(), "failed to create directory");

        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == TYPE_PHOTO){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return Uri.fromFile(mediaFile);
    }

    @Override
    public Uri getMediaUri() {
        return modifiedMediaUri!=null?modifiedMediaUri:rawMediaUri;
    }

    @Override
    public void setMediaUri(Uri uri) {
        this.rawMediaUri = uri;
        switch (mediaType){
            case TYPE_PHOTO:
                if(modifiedMediaUri==null) {
                    updateThumbnail(rawMediaUri);
                }
                break;
            case TYPE_VIDEO:
                break;
            case TYPE_FILE:
                break;
            default:
                break;
        }
    }

    @Override
    public int getMediaType() {
        return mediaType;
    }

    @Override
    public void setMediaType(@MediaType int type) {
        this.mediaType=type;
    }

    @Override
    public void setPlaceholder(int drawableRes) {
        int res = drawableRes;
        if(drawableRes==0) {
            switch (mediaType) {
                case TYPE_PHOTO:
                    res = R.drawable.default_photo_placeholder;
                    break;
                case TYPE_VIDEO:
                    break;
                case TYPE_FILE:
                    break;
                default:
                    break;
            }
        }

        this.placeholderRes = res;
        if(rawMediaUri ==null && getThumbnailView()!=null){
            getThumbnailView().setImageDrawable(ContextCompat.getDrawable(getContext(),placeholderRes));
        }
    }

    @Override
    public int getPlaceholder() {
        return placeholderRes;
    }

    @Override
    public String getModifiedPhotoSvg() {
        return svgLayer;
    }

    @Override
    public void setModifiedPhotoSvg(String svg) {
        //TODO
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable state = super.onSaveInstanceState();

        // Save the MDKWidgetDelegate instance state
        state = this.mdkWidgetDelegate.onSaveInstanceState(state);

        // Save the uri
        Bundle bundle = new Bundle();
        bundle.putParcelable("state", state);
        bundle.putInt("type", mediaType);
        bundle.putInt("placeholder", placeholderRes);
        bundle.putParcelable("raw_uri", rawMediaUri);
        bundle.putParcelable("modified_uri", modifiedMediaUri);
        bundle.putString("svg_layer", svgLayer);

        return bundle;

    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            //restore media type
            int type = bundle.getInt("type");
            switch (type) {
                case TYPE_PHOTO:
                    setMediaType(TYPE_PHOTO);
                    break;
                case TYPE_VIDEO:
                    setMediaType(TYPE_VIDEO);
                    break;
                case TYPE_FILE:
                    setMediaType(TYPE_FILE);
                    break;
                default:
                    break;
            }

            //restore placeholder
            int placeholder = bundle.getInt("placeholder");
            if (placeholder != 0) {
                setPlaceholder(placeholder);
            }

            //restore uri(s)
            Uri modifiedUri = bundle.getParcelable("modified_uri");
            if (modifiedUri != null) {
                modifiedMediaUri = modifiedUri;
                updateThumbnail(modifiedUri);
            }
            Uri rawUri = bundle.getParcelable("raw_uri");
            if (rawUri != null) {
                setMediaUri(rawUri);
            }

            //restore modified photo svg
            svgLayer = bundle.getString("svg_layer");

            Parcelable parcelable = bundle.getParcelable("state");
            parcelable = this.mdkWidgetDelegate.onRestoreInstanceState(this, parcelable);
            super.onRestoreInstanceState(parcelable);

            return;
        }
        // Restore the android view instance state
        super.onRestoreInstanceState(state);
    }

    /**
     * Updates the thumbnail with the mediaUri.
     * @param mediaUri the media uri
     */
    private void updateThumbnail(final Uri mediaUri){
        ImageView iv = getThumbnailView();
        if (iv != null) {
            iv.post(new Runnable() {
                @Override
                public void run() {
                    ImageView iv2 = getThumbnailView();
                    if (iv2 != null) {
                        try {
                            //Extract thumbnail from bitmap and display it
                            iv2.setImageBitmap(ThumbnailUtils.extractThumbnail(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mediaUri), iv2.getWidth(), iv2.getHeight()));
                        } catch (IOException e) {
                            Log.w(this.getClass().getSimpleName(), "Error trying to access file: " + mediaUri, e);
                        }
                    }
                }
            });
        }
    }
}
