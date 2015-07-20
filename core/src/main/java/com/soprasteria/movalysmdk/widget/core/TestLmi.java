package com.soprasteria.movalysmdk.widget.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.IntDef;

@Retention(RetentionPolicy.SOURCE)
@IntDef({TestLmi.TOTO})
public @interface TestLmi {

    public static int TOTO = 1;
}

