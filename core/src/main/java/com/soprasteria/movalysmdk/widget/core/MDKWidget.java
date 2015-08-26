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
package com.soprasteria.movalysmdk.widget.core;

import android.content.Context;

/**
 * Widget that can be included in a RichWidget.
 */
public interface MDKWidget extends MDKBaseWidget {

    /**
     * Returns the widget {@link MDKTechnicalInnerWidgetDelegate} object.
     * @return an {@link MDKTechnicalInnerWidgetDelegate} object
     */
    MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate();

    /**
     * Return android context.
     * @see android.view.View
     * @return android context
     */
    Context getContext();

    /**
     * superOnCreateDrawableState method.
     * @param extraSpace the extra space
     * @return int[] ..
     */
    int[] superOnCreateDrawableState(int extraSpace);

    /**
     * callMergeDrawableStates method.
     * @param baseState the base state
     * @param additionalState the additional state
     */
    void callMergeDrawableStates(int[] baseState, int[] additionalState);
}
