package com.soprasteria.movalysmdk.widget.fixedlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.soprasteria.movalysmdk.widget.core.MDKTechnicalInnerWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKTechnicalWidgetDelegate;
import com.soprasteria.movalysmdk.widget.core.MDKWidget;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasLabel;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.behavior.NoScrollable;
import com.soprasteria.movalysmdk.widget.core.behavior.types.IsNullable;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.fixedlist.adapters.WrapperAdapter;
import com.soprasteria.movalysmdk.widget.fixedlist.delegate.MDKFixedListWidgetDelegate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MDK Fixed list component.
 * <p/>
 * Used to display a list of elements and give the possibility to add / remove items directly from the list.
 * <p/>
 * The component relies on the {@link WrapperAdapter} class to wrap the given adapter and add
 * <ul>
 * <li>The click ability on the items of the list</li>
 * <li>A delete button for each item.</li>
 * </ul>
 * The wrapping view holder may be customized with the given parameters:
 * <ul>
 * <li>wrapperViewHolderClass: defines the class of the wrapper view holder</li>
 * <li>wrapperViewHolderLayout: defines the layout of the wrapper view holder</li>
 * <li>wrapperViewHolderInnerItemId: defines the identifier of the layout in the wrapper which will be hosting the list item view</li>
 * <li>wrapperViewHolderDeleteId: defines the identifier of the delete button in the wrapper view holder</li>
 * </ul>
 * <p/>
 * The component also allows to have an "add" button which may indifferently be inside the layout, or be a MDKCommandButton widget
 * which will communicate through a BroadcastListener.
 * <p/>
 * The additional available attributes are the following:
 * <ul>
 * <li>addButtonViewId: defines the identifier of the "Add" button</li>
 * <li>layoutManagerClass: the class of the layout manager to apply on the component</li>
 * <li>layoutManagerOrientation: the orientation to set on the layout manager</li>
 * </ul>
 * <p/>
 * Some LayoutManager implementations can be found in the layoutmanagers package:
 * <ul>
 * <li>WrapLinearLayoutManager: vertical layout manager, allows to use the widget wrapped in a layout.</li>
 * </ul>
 * The setup layout attributes for the layout managers allow to set a class and an orientation.
 * Should you need to set more parameters on your preferred layout manager, you should set it directly on the view in your code as follows:
 * <pre>
 * {@code
 * MDKFixedList fixedList = new MDKFixedList(context);
 * fixedList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
 * }
 * </pre>
 * <p/>
 * Also please note that the standard Android layout managers implementations do not allow to wrap the widget, so you may have to adapt your layouts.
 */
public class MDKFixedList extends RecyclerView implements View.OnClickListener, MDKWidget, HasLabel, HasValidator, HasDelegate, FixedListRemoveListener, IsNullable {

    /**
     * Reference widget id tag in Broadcast.
     */
    private static final String REFERENCE_WIDGET = "referenceWidget";

    /**
     * Command widget string tag in Broadcast.
     */
    private static final String COMMAND_WIDGET = "command";

    /**
     * Debugging tag.
     */
    private static final String TAG = MDKFixedList.class.getSimpleName();

    /**
     * Debugging tag.
     */
    private static final String ON_ATTACHED_TO_WINDOW = "onAttachedToWindow";

    /**
     * The MDKWidgetDelegate handling the component logic.
     */
    private MDKFixedListWidgetDelegate mdkWidgetDelegate;

    /**
     * add element listener.
     */
    private List<FixedListAddListener> addListeners;

    /**
     * Widget specific validators.
     */
    protected int[] specificValidators;

    /**
     * The dataObserver fot the adapter.
     */
    private AdapterDataObserver adapterDataObserver;

    /**
     * The height of the fixedList.
     */
    private int paramHeight;

    /**
     * true if the component should be resized in onAttachedToWindow.
     * This should occur after a screen rotate
     */
    private boolean needToResize = false;

    /**
     * Broadcast receiver for the add button.
     */
    private BroadcastReceiver actionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getIntExtra(REFERENCE_WIDGET, 0) == getId() && "primary".equals(intent.getStringExtra(COMMAND_WIDGET))) {
                for (FixedListAddListener listener : MDKFixedList.this.addListeners) {
                    listener.onAddClick();
                }
            }
        }
    };

    /**
     * Constructor.
     *
     * @param context the context
     * @param attrs   attributes set
     */
    public MDKFixedList(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     *
     * @param context  the context
     * @param attrs    attributes set
     * @param defStyle the style
     */
    public MDKFixedList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            for (IntentFilter intentFilter : getBroadcastIntentFilters()) {
                if (intentFilter != null) {
                    LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(actionReceiver, intentFilter);
                }
            }

            // if the method getLayoutManager() returns null, then no LayoutManager was set programmatically on the component.
            // we then set one from the delegate configuration.
            if (this.getLayoutManager() == null) {
                RecyclerView.LayoutManager mLayoutManager = null;

                try {
                    mLayoutManager = this.mdkWidgetDelegate.getLayoutManagerClass()
                            .getConstructor(RecyclerView.class, Integer.TYPE, Boolean.TYPE)
                            .newInstance(this, this.mdkWidgetDelegate.getLayoutManagerOrientation(), false);
                } catch (InstantiationException e) {
                    Log.e(TAG, ON_ATTACHED_TO_WINDOW, e);
                } catch (IllegalAccessException e) {
                    Log.e(TAG, ON_ATTACHED_TO_WINDOW, e);
                } catch (InvocationTargetException e) {
                    Log.e(TAG, ON_ATTACHED_TO_WINDOW, e);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, ON_ATTACHED_TO_WINDOW, e);
                }

                setLayoutManager(mLayoutManager);
            }

            if (needToResize) {
                resize();
            }

            if (this.mdkWidgetDelegate.getAddButton() != null) {
                this.mdkWidgetDelegate.getAddButton().setOnClickListener(this);
            }

            View addButton = this.getMDKWidgetDelegate().getAddButton();
            if (addButton != null) {
                addButton.setVisibility(isReadonly() ? View.GONE : View.VISIBLE);
            }
            WrapperAdapter wrapperAdapter = (WrapperAdapter) super.getAdapter();
            if (wrapperAdapter != null) {
                wrapperAdapter.setReadonly(isReadonly());
                wrapperAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (!isInEditMode()) {
            LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(actionReceiver);
            this.addListeners.clear();
            ((WrapperAdapter) super.getAdapter()).destroy();
        }
        super.onDetachedFromWindow();
    }

    /**
     * Return the IntentFilters handled by the widget.
     *
     * @return a IntentFilter array containing IntentFilters
     */
    protected IntentFilter[] getBroadcastIntentFilters() {
        return new IntentFilter[]{
                new IntentFilter(getResources().getString(R.string.mdkcommand_fixedlist_action))
        };
    }

    /**
     * Instantiate the MDKWidgetDelegate.
     *
     * @param context the context
     * @param attrs   attributes set
     */
    private void init(Context context, AttributeSet attrs) {
        this.mdkWidgetDelegate = new MDKFixedListWidgetDelegate(this, attrs);
        this.paramHeight = -1;
        this.adapterDataObserver = new AdapterDataObserver() {
            @Override
            public void onChanged() {
                resize();
                super.onChanged();
            }
        };
        this.addListeners = new ArrayList<>();
    }

    /**
     * Called to compute the size of the component given its content.
     */
    private void resize() {
        if (this.paramHeight == -1 && this.getLayoutParams() != null) {
            this.paramHeight = this.getLayoutParams().height;
        }
        if (this.getLayoutManager() instanceof NoScrollable && this.paramHeight == LayoutParams.WRAP_CONTENT) {
            ((NoScrollable) this.getLayoutManager()).updateDimension(MDKFixedList.this);
        }
    }

    @Override
    public void onClick(View v) {
        if (this.mdkWidgetDelegate.getAddButton() != null && v.getId() == this.mdkWidgetDelegate.getAddButton().getId()) {
            for (FixedListAddListener listener : this.addListeners) {
                listener.onAddClick();
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        WrapperAdapter wrapperAdapter = new WrapperAdapter<>(adapter, this.mdkWidgetDelegate.getWrapperViewHolderClass(),
                this.mdkWidgetDelegate.getWrapperViewHolderLayout(), this.mdkWidgetDelegate.getWrapperViewHolderInnerItemId(),
                this.mdkWidgetDelegate.getWrapperViewHolderDeleteId());
        wrapperAdapter.addRemoveListener(this);
        if (this.getAdapter() != null) {
            this.getAdapter().unregisterAdapterDataObserver(this.adapterDataObserver);
        }
        super.setAdapter(wrapperAdapter);
        this.getAdapter().registerAdapterDataObserver(this.adapterDataObserver);

        needToResize = adapter.getItemCount() > 0;
    }

    @Override
    public Adapter getAdapter() {
        WrapperAdapter wrapperAdapter = (WrapperAdapter) super.getAdapter();
        if (wrapperAdapter != null) {
            return wrapperAdapter.getAdapter();
        } else {
            return null;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (this.mdkWidgetDelegate.getAddButton() != null) {
            this.mdkWidgetDelegate.getAddButton().setEnabled(enabled);
        }
        ((WrapperAdapter) super.getAdapter()).setEnabled(enabled);
    }

    /**
     * Add a {@link FixedListAddListener} element.
     *
     * @param listener the listener to add
     */
    public void addAddListener(FixedListAddListener listener) {
        this.addListeners.add(listener);
    }

    /**
     * Remove a {@link FixedListAddListener} element.
     *
     * @param listener the listener to remove
     */
    public void addRemoveListener(FixedListRemoveListener listener) {
        ((WrapperAdapter) super.getAdapter()).addRemoveListener(listener);
    }

    /**
     * Add a {@link FixedListItemClickListener} element.
     *
     * @param listener the listener to add
     */
    public void addItemClickListener(FixedListItemClickListener listener) {
        ((WrapperAdapter) super.getAdapter()).addItemClickListener(listener);
    }

    /* technical delegate methods */

    @Override
    public MDKTechnicalWidgetDelegate getTechnicalWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public MDKTechnicalInnerWidgetDelegate getTechnicalInnerWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    @Override
    public MDKFixedListWidgetDelegate getMDKWidgetDelegate() {
        return this.mdkWidgetDelegate;
    }

    /* rich selector methods */

    @Override
    public int[] superOnCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.mdkWidgetDelegate != null) {
            return this.mdkWidgetDelegate.superOnCreateDrawableState(extraSpace);
        } else {
            // first called in the super constructor
            return super.onCreateDrawableState(extraSpace);
        }
    }

    @Override
    public void callMergeDrawableStates(int[] baseState, int[] additionalState) {
        mergeDrawableStates(baseState, additionalState);
    }

    /* delegate accelerator methods */

    @Override
    public void setMandatory(boolean mandatory) {
        this.mdkWidgetDelegate.setMandatory(mandatory);
    }

    @Override
    public boolean isMandatory() {
        return this.mdkWidgetDelegate.isMandatory();
    }

    @Override
    public void setReadonly(boolean readonly) {
        this.getMDKWidgetDelegate().setReadonly(readonly);
        View addButton = this.getMDKWidgetDelegate().getAddButton();
        if (addButton != null) {
            addButton.setVisibility(readonly ? View.GONE : View.VISIBLE);
        }
        WrapperAdapter wrapperAdapter = (WrapperAdapter) super.getAdapter();
        if (wrapperAdapter != null) {
            wrapperAdapter.setReadonly(readonly);
            wrapperAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean isReadonly() {
        return this.getMDKWidgetDelegate().isReadonly();
    }

    @Override
    public void addError(MDKMessages error) {
        this.mdkWidgetDelegate.addError(error);
    }

    @Override
    public void setError(CharSequence error) {
        this.mdkWidgetDelegate.setError(error);
    }

    @Override
    public void clearError() {
        this.mdkWidgetDelegate.clearError();
    }

    @Override
    public CharSequence getLabel() {
        return this.mdkWidgetDelegate.getLabel();
    }

    @Override
    public void setLabel(CharSequence label) {
        this.mdkWidgetDelegate.setLabel(label);
    }

    @Override
    public boolean validate() {
        return this.mdkWidgetDelegate.validate(true, EnumFormFieldValidator.VALIDATE);
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return this.mdkWidgetDelegate.validate(true, validationMode);
    }

    @Override
    public Object getValueToValidate() {
        if (this.getAdapter().getItemCount() > 0) {
            return this.getAdapter().getItemCount();
        } else {
            return null;
        }
    }

    @Override
    public int[] getValidators() {
        int[] basicValidators = {R.string.mdkvalidator_mandatory_class};
        int[] validators;

        if (this.specificValidators != null && this.specificValidators.length > 0) {
            validators = Arrays.copyOf(basicValidators, basicValidators.length + this.specificValidators.length);

            System.arraycopy(this.specificValidators, 0, validators, basicValidators.length, this.specificValidators.length);
        } else {
            validators = basicValidators;
        }

        return validators;
    }

    /* save / restore */

    @Override
    public Parcelable onSaveInstanceState() {
        // Save the android view instance state
        Parcelable state = super.onSaveInstanceState();

        // Save the MDKWidgetDelegate instance state
        state = this.mdkWidgetDelegate.onSaveInstanceState(state);

        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        // Restore the MDKWidgetDelegate instance state
        Parcelable innerState = this.mdkWidgetDelegate.onRestoreInstanceState(this, state);

        // Restore the android view instance state
        super.onRestoreInstanceState(innerState);
    }

    @Override
    public void onRemoveItemClick(int position) {
        validate(EnumFormFieldValidator.ON_USER);
    }

    /**
     * Method to get the WrapperAdapter.
     *
     * @return the wrapperAdapter
     */
    public WrapperAdapter getWrapperAdapter() {
        return (WrapperAdapter) super.getAdapter();
    }
}
