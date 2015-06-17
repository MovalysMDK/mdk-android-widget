package com.soprasteria.movalysmdk.widget.base;

import android.view.View;

import com.soprasteria.movalysmdk.widget.base.helper.StateHelper;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;

/**
 * Created by abelliard on 09/06/2015.
 */
public class SimpleMandatoryRichSelector implements RichSelector {

    // TODO search string in RES
    /** Constant. */
    private static final String MANDATORY_CHAR = " (*)";

    @Override
    public void onStateChange(int[] state, View v) {

        if (StateHelper.hasState(state, R.attr.state_mandatory)) {
            if (v instanceof HasLabel) {
                CharSequence label = ((HasLabel) v).getLabel();

                if (!label.toString().endsWith(MANDATORY_CHAR)) {
                    label = label.toString() + MANDATORY_CHAR;
                    ((HasLabel) v).setLabel(label);
                }
            }

        } else {
            if (v instanceof HasLabel) {
                CharSequence label = ((HasLabel) v).getLabel();

                if (label.toString().endsWith(MANDATORY_CHAR)) {
                    label = label.subSequence(0, label.length() - MANDATORY_CHAR.length());
                    ((HasLabel) v).setLabel(label);
                }
            }
        }
    }
}
