package com.soprasteria.movalysmdk.widget.base.helper;

/**
 * StateHelper class definition.
 */
public class StateHelper {

    /**
     * Private constructor.
     */
    private StateHelper() {

    }

    /**
     * hasState method.
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
