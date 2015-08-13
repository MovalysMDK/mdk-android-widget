package com.soprasteria.movalysmdk.widget.sample.selector;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.soprasteria.movalysmdk.widget.core.MDKBaseRichWidget;
import com.soprasteria.movalysmdk.widget.core.helper.StateHelper;
import com.soprasteria.movalysmdk.widget.core.selector.RichSelector;
import com.soprasteria.movalysmdk.widget.sample.R;

/**
 * Rich selector to make the text bold if mandatory.
 */
public class BoldMandatorySelector implements RichSelector {

    @Override
    public void onStateChange(int[] state, View view) {

        TextView tv = null;
        if (view instanceof TextView) {
            tv = (TextView) view;
        } else if (view instanceof MDKBaseRichWidget && ((MDKBaseRichWidget) view).getInnerWidget() instanceof TextView ) {
            tv = (TextView) ((MDKBaseRichWidget) view).getInnerWidget();
        }

        if (tv != null) {
            setTypefaceBold(tv, StateHelper.hasState(R.attr.state_mandatory, state));
        }
    }

    /**
     * Sets the typeface to bold on the given {@link TextView}.
     * @param textView the {@link TextView} to set
     * @param bold true to set the typeface to bold
     */
    private void setTypefaceBold(TextView textView, boolean bold) {
        Typeface tf = textView.getTypeface();

        int setTypeFace = Typeface.NORMAL;

        if (bold) {
            if (tf != null && tf.isItalic()) {
                setTypeFace = Typeface.BOLD_ITALIC;
            } else {
                setTypeFace = Typeface.BOLD;
            }
        } else {
            if (tf != null && tf.isItalic()) {
                setTypeFace = Typeface.ITALIC;
            }
        }

        textView.setTypeface(Typeface.create(textView.getTypeface(), setTypeFace));

    }
}
