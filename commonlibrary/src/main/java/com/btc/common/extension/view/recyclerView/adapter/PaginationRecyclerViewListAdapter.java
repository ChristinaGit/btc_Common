package com.btc.common.extension.view.recyclerView.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.contract.Contracts;
import com.btc.common.extension.view.recyclerView.viewHolder.ExtendedRecyclerViewHolder;

@Accessors(prefix = "_")
public abstract class PaginationRecyclerViewListAdapter<TItem>
    extends ModifiableRecyclerViewListAdapter<TItem, ExtendedRecyclerViewHolder> {
    public static final int VIEW_TYPE_LOADING = newViewType();

    @Override
    public int getFooterItemCount() {
        return super.getFooterItemCount() + (isLoadingNextPage() ? 1 : 0);
    }

    @Override
    protected int getFooterItemViewType(final int position) {
        return VIEW_TYPE_LOADING;
    }

    @Override
    protected boolean isFooterItemViewType(final int viewType) {
        return super.isFooterItemViewType(viewType) || viewType == VIEW_TYPE_LOADING;
    }

    @Override
    protected void onBindFooterItemViewHolder(
        @NonNull final ExtendedRecyclerViewHolder holder, final int position) {
        Contracts.requireNonNull(holder, "holder == null");

        final int viewType = getFooterItemViewType(position);

        if (viewType == VIEW_TYPE_LOADING) {
            onBindLoadingViewHolder(holder, position);
        } else {
            super.onBindFooterItemViewHolder(holder, position);
        }
    }

    @NonNull
    @Override
    protected ExtendedRecyclerViewHolder onCreateFooterItemViewHolder(
        final ViewGroup parent, final int viewType) {

        final ExtendedRecyclerViewHolder holder;

        if (viewType == VIEW_TYPE_LOADING) {
            holder = onCreateLoadingViewHolder(parent, viewType);
        } else {
            holder = super.onCreateFooterItemViewHolder(parent, viewType);
        }

        return holder;
    }

    public void setLoadingNextPage(final boolean loadingNextPage) {
        if (_loadingNextPage != loadingNextPage) {
            _loadingNextPage = loadingNextPage;

            onLoadingNextPageStateChanged();
        }
    }

    @CallSuper
    protected void onBindLoadingViewHolder(
        final ExtendedRecyclerViewHolder holder, final int position) {
    }

    @CallSuper
    protected void onLoadingNextPageStateChanged() {
        if (isLoadingNextPage()) {
            notifyItemInserted(getFooterItemAdapterPosition(0));
        } else {
            notifyItemRemoved(getFooterItemAdapterPosition(0));
        }
    }

    @NonNull
    protected abstract ExtendedRecyclerViewHolder onCreateLoadingViewHolder(
        final ViewGroup parent, final int viewType);

    @Getter
    private boolean _loadingNextPage;
}
