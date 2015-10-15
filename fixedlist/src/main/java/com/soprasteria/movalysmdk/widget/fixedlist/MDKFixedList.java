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
import com.soprasteria.movalysmdk.widget.core.behavior.HasChangeListener;
import com.soprasteria.movalysmdk.widget.core.behavior.HasDelegate;
import com.soprasteria.movalysmdk.widget.core.behavior.HasValidator;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKChangeListenerDelegate;
import com.soprasteria.movalysmdk.widget.core.listener.ChangeListener;
import com.soprasteria.movalysmdk.widget.core.message.MDKMessages;
import com.soprasteria.movalysmdk.widget.core.validator.EnumFormFieldValidator;
import com.soprasteria.movalysmdk.widget.fixedlist.adapters.WrapperAdapter;
import com.soprasteria.movalysmdk.widget.fixedlist.delegate.MDKFixedListWidgetDelegate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * MDK Fixed list component.
 *
 * Used to display a list of elements and give the possibility to add / remove items directly from the list.
 *
 * The component will wrap the given adapter to add the click ability on its items.
 * This wrapping will also add a delete button on each item.
 *
 * The available attributes are the following:
 * <ul>
 *     <li>addButtonViewId: defines the identifier of the "Add" button</li>
 *     <li>wrapperViewHolderClass: defines the class of the wrapper view holder</li>
 *     <li>wrapperViewHolderLayout: defines the layout of the wrapper view holder</li>
 *     <li>wrapperViewHolderDeleteId: defines the identifier of the delete button in the wrapper view holder</li>
 *     <li>layoutManagerClass: the class of the layout manager to apply on the component</li>
 *     <li>layoutManagerOrientation: the orientation to set on the layout manager</li>
 * </ul>
 */
public class MDKFixedList extends RecyclerView implements View.OnClickListener, MDKWidget, HasValidator, HasDelegate, HasChangeListener {

    /** Reference widget id tag in Broadcast. */
    private static final String REFERENCE_WIDGET = "referenceWidget";

    /** Command widget string tag in Broadcast. */
    private static final String COMMAND_WIDGET = "command";

    /** Debugging tag. */
    private static final String TAG = MDKFixedList.class.getSimpleName();

    /** Debugging tag. */
    private static final String ON_ATTACHED_TO_WINDOW = "onAttachedToWindow";

    /** The MDKWidgetDelegate handling the component logic. */
    private MDKFixedListWidgetDelegate mdkWidgetDelegate;

    /** notify change listeners. */
    private MDKChangeListenerDelegate mdkChangeListener;

    /** add element listener. */
    private List<FixedListAddListener> addListeners;

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
     * @param context the context
     * @param attrs attributes set
     */
    public MDKFixedList(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Constructor.
     * @param context the context
     * @param attrs attributes set
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

            // we should only do this if no LayoutManager was set
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

            if (this.mdkWidgetDelegate.getAddButton() != null) {
                this.mdkWidgetDelegate.getAddButton().setOnClickListener(this);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (!isInEditMode()) {
            LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(actionReceiver);
        }
        super.onDetachedFromWindow();
    }

    /**
     * Return the IntentFilters handled by the widget.
     * @return a IntentFilter array containing IntentFilters
     */
    protected IntentFilter[] getBroadcastIntentFilters() {
        return new IntentFilter[] {
                new IntentFilter(getResources().getString(R.string.mdkcommand_fixedlist_action))
        };
    }

    /**
     * Instantiate the MDKWidgetDelegate.
     * @param context the context
     * @param attrs attributes set
     */
    private void init(Context context, AttributeSet attrs) {
        this.mdkWidgetDelegate = new MDKFixedListWidgetDelegate(this, attrs);
        this.mdkChangeListener = new MDKChangeListenerDelegate();
        this.addListeners = new ArrayList<>();
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
        adapter.registerAdapterDataObserver(new AdapterDataObserver() {

            @Override
            public void onChanged() {
                super.onChanged();
                // TODO : change title
            }

        });

        WrapperAdapter wrapperAdapter = new WrapperAdapter(adapter, this.mdkWidgetDelegate.getWrapperViewHolderClass(),
                this.mdkWidgetDelegate.getWrapperViewHolderLayout(), this.mdkWidgetDelegate.getWrapperViewHolderDeleteId());
        super.setAdapter(wrapperAdapter);
    }

    @Override
    public Adapter getAdapter() {
        WrapperAdapter wrapperAdapter = (WrapperAdapter) super.getAdapter();
        return wrapperAdapter.getAdapter();
    }

    /**
     * Add a {@link FixedListAddListener} element.
     * @param listener the listener to add
     */
    public void addAddListener(FixedListAddListener listener) {
        this.addListeners.add(listener);
    }

    /**
     * Remove a {@link FixedListAddListener} element.
     * @param listener the listener to remove
     */
    public void addRemoveListener(FixedListRemoveListener listener) {
        ((WrapperAdapter) super.getAdapter()).addRemoveListener(listener);
    }

    /**
     * Add a {@link FixedListItemClickListener} element.
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
    public boolean validate() {
        return this.mdkWidgetDelegate.validate(true, EnumFormFieldValidator.VALIDATE);
    }

    @Override
    public boolean validate(@EnumFormFieldValidator.EnumValidationMode int validationMode) {
        return this.mdkWidgetDelegate.validate(true, validationMode);
    }

    @Override
    public Object getValueToValidate() {
        // TODO
        return null;
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        this.mdkChangeListener.registerChangeListener(listener);
    }

    @Override
    public void unregisterChangeListener(ChangeListener listener) {
        this.mdkChangeListener.unregisterChangeListener(listener);
    }

    @Override
    public int[] getValidators() {
        // TODO
        return new int[0];
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
}
