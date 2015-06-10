package com.soprasteria.movalysmdk.widget.base.helper;

/**
 * Created by abelliard on 09/06/2015.
 */
public class StateHelper {

    public static boolean hasState(int[] state, int attr) {
        for (int i: state) {
            if (i == attr) {
                return true;
            }
        }
        return false;
    }

}
