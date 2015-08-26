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
package com.soprasteria.movalysmdk.widget.basic;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.AttributeSet;

import com.soprasteria.movalysmdk.widget.basic.model.Email;
import com.soprasteria.movalysmdk.widget.core.behavior.HasEmail;

/**
 * MDK Email
 * <p>Representing an editable text validate with an email regexp</p>
 * <p>
 *     This widget present a command who send the intent Intent.ACTION_SEND to the
 *     Android system with the component text by default.
 * </p>
 * <p>The validation regexp is stored in R.string.email_regexp</p>
 */
public class MDKEmail extends MDKCommandsEditText implements HasEmail {

    /** Keyword instanceState. */
    private static final String INSTANCE_STATE = "instanceState";

    /** Keyword innerState. */
    private static final String INNER_STATE = "innerState";

    /** email object. */
    private Email email;

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     */
    public MDKEmail(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     * @param style the layout defined style
     */
    public MDKEmail(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Initialization.
     * @param attrs the layout attributes
     */
    private final void init(AttributeSet attrs) {
        setSpecificAttributes(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, new int[]{R.string.mdkwidget_mdkemail_validator_class});
        this.email = new Email();
    }

    @Override
    protected Email getCommandInput() {
        return this.email;
    }

    @Override
    protected void completeLocalValueOnValidation() {
        this.email.setTo(new String[]{this.getText().toString()});
    }

    @Override
    public void setEmail(String[] email) {
        this.email = Email.stringArrayToEmail(email);

        if (this.email.getTo() != null && this.email.getTo().length > 0) {
            this.setText(this.email.getTo()[0]);
        } else {
            this.setText(null);
        }
    }

    @Override
    public String[] getEmail() {
        this.email.setTo(new String[]{this.getText().toString()});

        return Email.emailToStringArray(this.email);
    }

    /* save / restore */

    /**
     * Method called to store data before pausing the activity.
     * @return activity's state
     */
    @Override
    public Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        // Save the android view instance state
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        // Save the MDKWidgetDelegate instance state
        bundle.putParcelable(INNER_STATE, this.mdkWidgetDelegate.onSaveInstanceState(bundle.getParcelable(INSTANCE_STATE)));
        // Save the email object
        bundle.putStringArray("email", Email.emailToStringArray(this.email));
        return bundle;
    }

    /**
     * Called when the activity is being re-initialized from a previously saved state, given here in onSavedInstanceState.
     * @param state of the activity
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.mdkWidgetDelegate.onRestoreInstanceState(this, bundle.getParcelable(INNER_STATE));
            // Restore the email object
            this.email = Email.stringArrayToEmail(bundle.getStringArray("email"));
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);

    }
}
