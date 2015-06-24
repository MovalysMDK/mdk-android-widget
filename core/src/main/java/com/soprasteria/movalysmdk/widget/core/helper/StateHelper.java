package com.soprasteria.movalysmdk.widget.core.helper;

/**
 * Provide information on the MDK state for a widget.
 */
public class StateHelper {

    /**
     * Private constructor.
     */
    private StateHelper() {
        // private because utility class
    }

    /**
     * Return true if the attribute is actually known by the MDK drawable state.
     * @param state MDK drawable state
     * @param attr attribute
     * @return boolean true the attribute is found
     */
    //FIXME: reverse parameter order
    public static boolean hasState(int attr, int[] state) {
        for (int i: state) {
            if (i == attr) {
                return true;
            }
        }
        return false;
    }

}
