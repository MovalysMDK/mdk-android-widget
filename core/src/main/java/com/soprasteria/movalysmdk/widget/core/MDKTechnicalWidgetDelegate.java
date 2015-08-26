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

/**
 * Technical Delegate giving access to the inner components of the widgets.
 * This helps having a common behaviour between Rich Components and their related widgets.
 */
public interface MDKTechnicalWidgetDelegate {

    /**
     * Set the root id.
     * <p>The layout contains the error view.</p>
     * @param rootId the id of a view
     */
    void setRootViewId(int rootId);

    /**
     * Set the label id.
     * <p>The view used as label for this widget.</p>
     * @param labelId the id of a view
     */
    void setLabelViewId(int labelId);

    /**
     * Set the helper id.
     * <p>The view used as helper for this widget.</p>
     * @param helperId the id of a view
     */
    void setHelperViewId(int helperId);

    /**
     * Set the error id.
     * <p>The view used as error for this widget.</p>
     * @param errorId the id of a view
     */
    void setErrorViewId(int errorId);

    /**
     * Set if the error view is not in the same layout as the others
     * widget (used for commons errors in a layout).
     * <p>If this value is passed to true, the root id must be set as well.</p>
     * @param useRootIdOnlyForError true if the error is not in the same layout as
     *                              the other sub widget
     */
    void setUseRootIdOnlyForError(boolean useRootIdOnlyForError);

}
