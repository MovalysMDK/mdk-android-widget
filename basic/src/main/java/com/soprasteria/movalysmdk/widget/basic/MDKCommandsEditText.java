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
import android.util.AttributeSet;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.behavior.HasCommands;
import com.soprasteria.movalysmdk.widget.core.delegate.WidgetCommandDelegate;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;

/**
 * Abstract class extending the {@link MDKEditText} to add commands to the logic.
 * Any widget with commands and an EditText field should inherit this class.
 */
public abstract class MDKCommandsEditText extends MDKEditText implements HasCommands {

    /** CommandDelegate attribute. */
    protected WidgetCommandDelegate commandDelegate;

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     */
    public MDKCommandsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    /**
     * Constructor.
     * @param context the android context
     * @param attrs the layout attributes
     * @param style the layout defined style
     */
    public MDKCommandsEditText(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);

        init(attrs);
    }

    /**
     * Initialization.
     * @param attrs the layout attributes
     */
    private final void init(AttributeSet attrs) {
        this.commandDelegate = new WidgetCommandDelegate(this, attrs);
    }

    @Override
    public WidgetCommandDelegate getWidgetCommandDelegate() {
        return this.commandDelegate;
    }

    @Override
    public void onClick(View v) {
        String text = this.getText().toString();
        if (text != null && text.length() > 0) {
            this.commandDelegate.getWidgetCommand(v.getId()).execute(this.getContext(), getCommandInput());
        }
    }

    /**
     * Registers the command listeners.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!isInEditMode()) {
            this.commandDelegate.registerCommands(this);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        if (this.getMDKWidgetDelegate() != null && this.commandDelegate != null && !isInEditMode() &&
                this.getMDKWidgetDelegate().validate(false, EnumFormFieldValidator.ON_USER)) {
            completeLocalValueOnValidation();
        }
    }

    /**
     * Called after a text change when the input value is validated.
     * Should update the inner widget object (if it exists)
     */
    protected void completeLocalValueOnValidation() {
        // nothing to do
    }

    /**
     * Returns the value stored in the widget to the proper type for command execution.
     * @param <T> the type of the returned value
     * @return the converted value
     */
    protected <T> T getCommandInput() {
        return (T) this.getText().toString();
    }
}
