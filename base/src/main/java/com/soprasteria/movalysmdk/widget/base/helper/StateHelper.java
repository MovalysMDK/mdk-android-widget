package com.soprasteria.movalysmdk.widget.base.helper;

public class StateHelper {

    /**
     * Private constructor
     */
    private StateHelper() {

    }

    public static boolean hasState(int[] state, int attr) {
        for (int i: state) {
            if (i == attr) {
                return true;
            }
        }
        return false;
    }

}
