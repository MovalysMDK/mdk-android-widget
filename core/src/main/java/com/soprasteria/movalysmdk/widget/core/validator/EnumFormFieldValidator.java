package com.soprasteria.movalysmdk.widget.core.validator;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Abstract class on validation mode
 * <p>
 *     This abstract class is used to access to available validation modes.<br/>
 * </p>
 */
public abstract class EnumFormFieldValidator implements FormFieldValidator {

    /** EnumValidationMode validation mode enumeration. */
    @IntDef({VALIDATE, ON_FOCUS, ON_USER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EnumValidationMode {
    }

    /** VALIDATE mode. */
    public static final int VALIDATE = 0;
    /** ON_FOCUS mode. */
    public static final int ON_FOCUS = 1;
    /** ON_USER mode. */
    public static final int ON_USER = 2;
}
