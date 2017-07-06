package com.btc.common.extension.view.recyclerView.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.extension.view.recyclerView.viewHolder.ExtendedRecyclerViewHolder;

@Accessors(prefix = "_")
public abstract class WrappedRecyclerViewAdapter<TViewHolder extends ExtendedRecyclerViewHolder>
    extends RecyclerViewAdapter<TViewHolder> {

    public static final int VIEW_TYPE_HEADER = newViewType();

    public static final int VIEW_TYPE_FOOTER = newViewType();

    public static final int VIEW_TYPE_INNER = newViewType();

    public final void notifyInnerItemsChanged() {
        final val innerItemCount = getInnerItemCount();
        if (innerItemCount > 0) {
            notifyItemRangeChanged(getInnerItemAdapterPosition(0), innerItemCount);
        }
    }

    public int getFooterItemCount() {
        return 0;
    }

    public int getHeaderItemCount() {
        return 0;
    }

    @Override
    public TViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final TViewHolder holder;

        if (isHeaderItemViewType(viewType)) {
            holder = onCreateHeaderItemViewHolder(parent, viewType);
        } else if (isInnerItemViewType(viewType)) {
            holder = onCreateInnerItemViewHolder(parent, viewType);
        } else if (isFooterItemViewType(viewType)) {
            holder = onCreateFooterItemViewHolder(parent, viewType);
        } else {
            throw new IllegalArgumentException("Unknown view type: " + viewType);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final TViewHolder holder, final int position) {
        final int viewType = getItemViewType(position);
        if (isHeaderItemViewType(viewType)) {
            onBindHeaderItemViewHolder(holder, position);
        } else if (isInnerItemViewType(viewType)) {
            onBindInnerItemViewHolder(holder, position);
        } else if (isFooterItemViewType(viewType)) {
            onBindFooterItemViewHolder(holder, position);
        } else {
            throw new IllegalArgumentException(
                "Unknown view type: " + viewType + " at position" + position);
        }
    }

    @Override
    public int getItemViewType(final int position) {
        final int viewType;

        final int headerItemCount = getHeaderItemCount();
        final int innerItemCount = getInnerItemCount();
        final int footerItemCount = getFooterItemCount();

        final int headerItemIndex = headerItemCount;
        final int innerItemIndex = headerItemIndex + innerItemCount;
        final int footerItemIndex = innerItemIndex + footerItemCount;

        if (0 <= position && position < headerItemIndex) {
            viewType = getHeaderItemViewType(position);
        } else if (headerItemIndex <= position && position < innerItemIndex) {
            viewType = getInnerItemViewType(position);
        } else if (innerItemIndex <= position && position < footerItemIndex) {
            viewType = getFooterItemViewType(position);
        } else {
            throw new IllegalArgumentException("Illegal position: " + position);
        }

        return viewType;
    }

    @Override
    public int getItemCount() {
        return getHeaderItemCount() + getInnerItemCount() + getFooterItemCount();
    }

    public abstract int getInnerItemCount();

    protected int getFooterItemAdapterPosition(final int relativePosition) {
        return relativePosition + getHeaderItemCount() + getInnerItemCount();
    }

    protected int getFooterItemRelativePosition(final int position) {
        return position - getHeaderItemCount() - getInnerItemCount();
    }

    protected int getFooterItemViewType(final int position) {
        return VIEW_TYPE_FOOTER;
    }

    protected int getHeaderItemAdapterPosition(final int position) {
        return position;
    }

    protected int getHeaderItemRelativePosition(final int position) {
        return position;
    }

    protected int getHeaderItemViewType(final int position) {
        return VIEW_TYPE_HEADER;
    }

    protected int getInnerItemAdapterPosition(final int relativePosition) {
        return relativePosition + getHeaderItemCount();
    }

    protected int getInnerItemRelativePosition(final int position) {
        return position - getHeaderItemCount();
    }

    protected int getInnerItemViewType(final int position) {
        return VIEW_TYPE_INNER;
    }

    protected boolean isFooterItemViewType(final int viewType) {
        return viewType == VIEW_TYPE_FOOTER;
    }

    protected boolean isHeaderItemViewType(final int viewType) {
        return viewType == VIEW_TYPE_HEADER;
    }

    protected boolean isInnerItemViewType(final int viewType) {
        return viewType == VIEW_TYPE_INNER;
    }

    protected void onBindFooterItemViewHolder(
        @NonNull final TViewHolder holder, final int position) {
    }

    protected void onBindHeaderItemViewHolder(
        @NonNull final TViewHolder holder, final int position) {
    }

    protected void onBindInnerItemViewHolder(
        @NonNull final TViewHolder holder, final int position) {
    }

    @NonNull
    protected TViewHolder onCreateFooterItemViewHolder(final ViewGroup parent, final int viewType) {
        throw new IllegalArgumentException("Unknown view type: " + viewType);
    }

    @NonNull
    protected TViewHolder onCreateHeaderItemViewHolder(final ViewGroup parent, final int viewType) {
        throw new IllegalArgumentException("Unknown view type: " + viewType);
    }

    @NonNull
    protected TViewHolder onCreateInnerItemViewHolder(final ViewGroup parent, final int viewType) {
        throw new IllegalArgumentException("Unknown view type: " + viewType);
    }
}
