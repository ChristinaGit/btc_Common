package com.btc.common.extension.view.recyclerView.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.extension.view.recyclerView.viewHolder.ExtendedRecyclerViewHolder;

import java.util.List;

@Accessors(prefix = "_")
public abstract class RecyclerViewListAdapter<TItem, TViewHolder extends ExtendedRecyclerViewHolder>
    extends WrappedRecyclerViewAdapter<TViewHolder> {
    @NonNull
    public final TItem getItem(final int position) {
        return getItemByRelativePosition(getInnerItemRelativePosition(position));
    }

    @NonNull
    public final TItem getItemByRelativePosition(final int position) {
        final TItem item;

        final val items = getItems();
        if (items != null) {
            item = items.get(position);
        } else {
            throw new IllegalStateException("No item by position: " + position);
        }

        return item;
    }

    @Override
    public int getInnerItemCount() {
        final int itemCount;

        final val items = getItems();
        if (items != null) {
            itemCount = items.size();
        } else {
            itemCount = 0;
        }

        return itemCount;
    }

    @Nullable
    public abstract List<TItem> getItems();
}
