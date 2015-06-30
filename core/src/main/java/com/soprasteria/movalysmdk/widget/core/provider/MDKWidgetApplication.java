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
package com.soprasteria.movalysmdk.widget.core.provider;

/**
 * Provide the MDKWidgetComponentProvider instance.
 *
 * <p>This interface is usable on Android Application class only.</p>
 */
public interface MDKWidgetApplication {

    /**
     * Log tag for Log.* methods.
     */
    String LOG_TAG = "MdkWidget";

    /**
     * Return the implementation of MDKWidgetComponentProvider.
     * <p>The returned instance must be a singleton.</p>
     * @return the singleton of MDKWidgetComponentProvider implementation
     */
    MDKWidgetComponentProvider getMDKWidgetComponentProvider();

}
