package com.soprasteria.movalysmdk.widget.base.helper;

/**
 * Provide information on the MDK state for a widget.
 */
public class StateHelper {

    /**
     * Private constructor.
     */
    private StateHelper() {

    }

    /**
     * Return if the attribute is actually a known MDK state.
     * @param state the state tab
     * @param attr attribute
     * @return boolean true if state find.
     */
    public static boolean hasState(int[] state, int attr) {
        for (int i: state) {
            if (i == attr) {
                return true;
            }
        }
        return false;
    }

}
