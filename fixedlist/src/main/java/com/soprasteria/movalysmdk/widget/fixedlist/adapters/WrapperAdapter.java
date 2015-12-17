package com.soprasteria.movalysmdk.widget.fixedlist.adapters;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soprasteria.movalysmdk.widget.fixedlist.FixedListItemClickListener;
import com.soprasteria.movalysmdk.widget.fixedlist.FixedListRemoveListener;
import com.soprasteria.movalysmdk.widget.fixedlist.R;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper adapter for the MDKFixedList widget.
 * Wraps the set adapter to add a click ability on the items of the list, and set a delete button for each of them.
 *
 * @param <W> the class of the view holder of the wrapper
 */
public class WrapperAdapter<W extends WrapperViewHolder> extends RecyclerView.Adapter<W> implements View.OnClickListener {

    /**
     * tag for debugging.
     */
    private static final String TAG = WrapperAdapter.class.getSimpleName();

    /**
     * tag for debugging.
     */
    private static final String ON_CREATE_VIEW_HOLDER = "onCreateViewHolder";

    /**
     * the wrapped adapter.
     */
    private RecyclerView.Adapter adapter;

    /**
     * the registered listeners to call when an item is removed from the list.
     */
    private List<FixedListRemoveListener> removeListener;

    /**
     * the registered listeners to call when an item is clicked in the list.
     */
    private List<FixedListItemClickListener> itemClickListeners;

    /**
     * the class of the view holder element of the wrapper.
     */
    private Class<W> viewHolderClass;

    /**
     * the layout of the view holder of the wrapper.
     */
    private int viewHolderLayout;

    /**
     * the identifier of the layout replaced with the list item view.
     */
    private int innerItemId;

    /**
     * the identifier of the delete button in the layout of the view holder.
     */
    private int deleteId;

    /**
     * true if the views should be enabled.
     */
    private boolean isEnabled = true;

    /**
     * true if the views should be readonly.
     */
    private boolean isReadonly = false;

    /**
     * Constructor.
     *
     * @param adapter          the adapter to wrap
     * @param viewHolderClass  the class of the view holder
     * @param viewHolderLayout the layout of the view holder
     * @param innerItemId      the identifier of the layout replaced with the list item view
     * @param deleteId         the identifier of the delete button in the layout
     */
    public WrapperAdapter(RecyclerView.Adapter adapter, Class<W> viewHolderClass, @LayoutRes int viewHolderLayout, @IdRes int innerItemId, @IdRes int deleteId) {
        this.adapter = adapter;
        this.viewHolderClass = viewHolderClass;
        this.viewHolderLayout = viewHolderLayout;
        this.innerItemId = innerItemId;
        this.deleteId = deleteId;

        this.removeListener = new ArrayList<>();
        this.itemClickListeners = new ArrayList<>();

        this.adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                WrapperAdapter.this.notifyDataSetChanged();
            }
        });
    }

    /**
     * Returns the wrapped adapter.
     *
     * @return the wrapped adapter
     */
    public RecyclerView.Adapter getAdapter() {
        return this.adapter;
    }

    @Override
    public W onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder adapterViewHolder = this.adapter.onCreateViewHolder(parent, viewType);

        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(viewHolderLayout, null);

        ViewGroup inner = (ViewGroup) v.findViewById(innerItemId);
        inner.addView(adapterViewHolder.itemView);

        if (isReadonly) {
            View delete = v.findViewById(deleteId);
            if (delete != null) {
                delete.setVisibility(View.GONE);
            }
        }

        W vh = null;

        try {
            vh = viewHolderClass
                    .getConstructor(View.class, RecyclerView.ViewHolder.class, Integer.TYPE)
                    .newInstance(v, adapterViewHolder, deleteId);
        } catch (InstantiationException e) {
            Log.e(TAG, ON_CREATE_VIEW_HOLDER, e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, ON_CREATE_VIEW_HOLDER, e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, ON_CREATE_VIEW_HOLDER, e);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, ON_CREATE_VIEW_HOLDER, e);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(W holder, int position) {
        this.adapter.onBindViewHolder(holder.getViewHolder(), position);
        if (holder.getDeleteButton() != null) {
            holder.getDeleteButton().setTag(R.id.fixedlist_item_position, position);
            holder.getDeleteButton().setOnClickListener(this);
        }

        holder.itemView.setTag(R.id.fixedlist_item_position, position);
        holder.itemView.setTag(R.id.fixedlist_item_root_view, true);
        holder.itemView.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag(R.id.fixedlist_item_root_view) != null) {
            this.notifyItemClickListeners((int) v.getTag(R.id.fixedlist_item_position));
        } else if (v.getId() == deleteId) {
            this.notifyItemDeleteListeners((int) v.getTag(R.id.fixedlist_item_position));
        }
    }

    /**
     * Called on destroy.
     */
    public void destroy() {
        this.removeListener.clear();
        this.itemClickListeners.clear();
    }

    /**
     * Sets the enabled status of the widget on the adapter.
     *
     * @param isEnabled true to enable the wrapper
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Sets the readonly status of the widget on the adapter.
     *
     * @param isReadonly true to enable the wrapper
     */
    public void setReadonly(boolean isReadonly) {
        this.isReadonly = isReadonly;
    }

    /**
     * Notifies the registered delete listeners.
     *
     * @param position the position of the deleted item
     */
    private void notifyItemDeleteListeners(int position) {
        if (isEnabled && !isReadonly) {
            for (FixedListRemoveListener listener : this.removeListener) {
                listener.onRemoveItemClick(position);
            }
        }
    }

    /**
     * Notifies the registered click listeners.
     *
     * @param position the position of the clicked item
     */
    private void notifyItemClickListeners(int position) {
        if (isEnabled && !isReadonly) {
            for (FixedListItemClickListener listener : this.itemClickListeners) {
                listener.onItemClick(position);
            }
        }
    }

    /**
     * Adds a remove listener to the registered ones.
     *
     * @param listener the listener to add
     */
    public void addRemoveListener(FixedListRemoveListener listener) {
        this.removeListener.add(listener);
    }

    /**
     * Adds a click listener to the the registered ones.
     *
     * @param listener the listener to add
     */
    public void addItemClickListener(FixedListItemClickListener listener) {
        this.itemClickListeners.add(listener);
    }

    /**
     * Method to compute fixedList height.
     *
     * @param v The viewGroup
     * @return the height of the fixedList in order to be set in layout params
     */
    public int computeHeight(ViewGroup v) {
        int height = 0;
        for (int i = 0; i < this.getItemCount(); i++) {
            W vh = this.createViewHolder(v, this.getItemViewType(i));
            this.onBindViewHolder(vh, i);
            v.addView(vh.itemView);
            vh.itemView.measure(vh.itemView.getMeasuredWidth(), RecyclerView.LayoutParams.WRAP_CONTENT);
            height += vh.itemView.getMeasuredHeight();
        }
        return height;
    }
}
