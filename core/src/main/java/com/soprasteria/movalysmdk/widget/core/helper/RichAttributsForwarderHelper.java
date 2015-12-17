package com.soprasteria.movalysmdk.widget.core.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.R;

/**
 * Helper class to parse forward attributes.
 */
public class RichAttributsForwarderHelper {

    /**
     * index of SANS typeface.
     */
    private static final int SANS = 1;
    /**
     * index of SERIF typeface.
     */
    private static final int SERIF = 2;
    /**
     * index of MONOSPACE typeface.
     */
    private static final int MONOSPACE = 3;

    /**
     * Constructor.
     */
    private RichAttributsForwarderHelper() {
        // nothing to do
    }

    /**
     * forward attribut from Rich widget to inner views.
     * <p>
     *     forward the text color parameter,
     *     the text size parameter,
     *     the ems parameter,
     *     the type face parameter,
     *     the font family parameter,
     *     the text style parameter and
     *     the text paramter.
     * </p>
     * @param context the android context
     * @param attrs the widget attributes
     * @param touchedViews the array of View to forward attributes
     */
    public static void parseAttributs(Context context, AttributeSet attrs, View... touchedViews) {

        // TEXT
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RichTextView);

        int textSize = -1;
        int typefaceIndex = -1;
        int ems = -1;
        ColorStateList textColor = null;
        String fontFamily = null;
        int styleIndex = -1;
        CharSequence text = null;

        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);

            if (attr == R.styleable.RichTextView_android_textColor) {
                textColor = typedArray.getColorStateList(attr);
            } else if (attr == R.styleable.RichTextView_android_textSize) {
                textSize = typedArray.getDimensionPixelSize(attr, -1);
            } else if (attr == R.styleable.RichTextView_android_ems) {
                ems = typedArray.getInt(attr, -1);
            } else if (attr == R.styleable.RichTextView_android_typeface) {
                typefaceIndex = typedArray.getInt(attr, -1);
            } else if (attr == R.styleable.RichTextView_android_fontFamily) {
                fontFamily = typedArray.getString(attr);
            } else if (attr == R.styleable.RichTextView_android_textStyle) {
                styleIndex = typedArray.getInt(attr, -1);
            } else if (attr == R.styleable.RichTextView_android_text) {
                text = typedArray.getText(attr);
            }

        }

        // edit touchedViews
        if (touchedViews != null) {
            for (View v : touchedViews) {
                if (v instanceof TextView) {
                    if (textColor != null) {
                        ((TextView) v).setTextColor(textColor);
                    }
                    if (textSize != -1) {
                        ((TextView) v).setTextSize(textSize);
                    }
                    if (ems != -1) {
                        ((TextView) v).setEms(ems);
                    }
                    if (text != null) {
                        ((TextView) v).setText(text);
                    }
                    if (fontFamily != null
                            || styleIndex != -1
                            || typefaceIndex != -1) {
                        setTypefaceFromAttrs((TextView) v, fontFamily, typefaceIndex, styleIndex);
                    }
                }
            }
        }

        typedArray.recycle();

    }

    /**
     * Set the typeface on the inner component from attributes.
     * @param textView the touched textView
     * @param fontFamily the typeface family as String
     * @param typefaceIndex the typeface index
     * @param styleIndex the style index
     */
    private static void setTypefaceFromAttrs(TextView textView, String fontFamily, int typefaceIndex, int styleIndex) {
        Typeface tf = null;
        if (fontFamily != null) {
            tf = Typeface.create(fontFamily, styleIndex);
            if (tf != null) {
                textView.setTypeface(tf);
                return;
            }
        }
        switch (typefaceIndex) {
            case SANS:
                tf = Typeface.SANS_SERIF;
                break;

            case SERIF:
                tf = Typeface.SERIF;
                break;

            case MONOSPACE:
                tf = Typeface.MONOSPACE;
                break;

            default:
                // nothing to do
                break;
        }

        textView.setTypeface(tf, styleIndex);
    }
}
