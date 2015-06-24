package com.soprasteria.movalysmdk.widget.core.selector;

import android.view.View;

import com.soprasteria.movalysmdk.widget.core.R;
import com.soprasteria.movalysmdk.widget.core.helper.StateHelper;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;

/**
 * Handle the change of MDK mandatory state on a MDK widget.
 */
public class SimpleMandatoryRichSelector implements RichSelector {

    /**
     * Switch the mandatory state of the rich widget.
     * @param state the new mandatory state
     * @param view the widget's view where the change of state happened
     */
    public void onStateChange(int[] state, View view) {

        if (StateHelper.hasState(R.attr.state_mandatory, state)) {
            if (view instanceof HasLabel) {
                CharSequence label = ((HasLabel) view).getLabel();

                if (!label.toString().endsWith(view.getContext().getString(R.string.mandatory_char))) {
                    label = label.toString() + view.getContext().getString(R.string.mandatory_char);
                    ((HasLabel) view).setLabel(label);
                }
            }

        } else {
            if (view instanceof HasLabel) {
                CharSequence label = ((HasLabel) view).getLabel();

                if (label.toString().endsWith(view.getContext().getString(R.string.mandatory_char))) {
                    label = label.subSequence(0, label.length() - view.getContext().getString(R.string.mandatory_char).length());
                    ((HasLabel) view).setLabel(label);
                }
            }
        }
    }
}
