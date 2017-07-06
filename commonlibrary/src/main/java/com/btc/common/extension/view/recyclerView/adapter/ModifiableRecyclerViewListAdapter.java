package com.btc.common.extension.view.recyclerView.adapter;

import android.support.annotation.NonNull;

import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.contract.Contracts;
import com.btc.common.extension.view.recyclerView.viewHolder.ExtendedRecyclerViewHolder;

import java.util.Collection;

@Accessors(prefix = "_")
public abstract class ModifiableRecyclerViewListAdapter<TItem, TViewHolder extends
    ExtendedRecyclerViewHolder>
    extends RecyclerViewListAdapter<TItem, TViewHolder> {
    public final void addItem(final int position, @NonNull final TItem newItem) {
        Contracts.requireNonNull(newItem, "newItem == null");

        addItem(position, newItem, true);
    }

    public final void addItem(
        final int position, @NonNull final TItem newItem, final boolean notify) {
        Contracts.requireNonNull(newItem, "newItem == null");

        final val items = getItems();

        if (items != null) {
            items.add(position, newItem);

            if (notify) {
                notifyItemInserted(getInnerItemAdapterPosition(position));
            }
        }
    }

    public final void addItem(@NonNull final TItem newItem) {
        Contracts.requireNonNull(newItem, "newItem == null");

        addItem(newItem, true);
    }

    public final void addItem(@NonNull final TItem newItem, final boolean notify) {
        Contracts.requireNonNull(newItem, "newItem == null");

        addItem(getInnerItemCount(), newItem, notify);
    }

    public final void addItems(@NonNull final Collection<TItem> newItems) {
        Contracts.requireNonNull(newItems, "newItems == null");

        addItems(newItems, true);
    }

    public final void addItems(@NonNull final Collection<TItem> newItems, final boolean notify) {
        Contracts.requireNonNull(newItems, "newItems == null");

        addItems(getInnerItemCount(), newItems, notify);
    }

    public final void addItems(final int position, @NonNull final Collection<TItem> newItems) {
        Contracts.requireNonNull(newItems, "newItems == null");

        addItems(position, newItems, true);
    }

    public final void addItems(
        final int position, @NonNull final Collection<TItem> newItems, final boolean notify) {
        Contracts.requireNonNull(newItems, "newItems == null");

        final val items = getItems();

        if (items != null) {
            items.addAll(position, newItems);

            if (notify) {
                notifyItemRangeInserted(getInnerItemAdapterPosition(position), newItems.size());
            }
        }
    }

    public final void changeItems(@NonNull final Collection<TItem> newItems) {
        Contracts.requireNonNull(newItems, "newItems == null");

        setItems(newItems, true);
    }

    public final void changeItems(@NonNull final Collection<TItem> newItems, final boolean notify) {
        Contracts.requireNonNull(newItems, "newItems == null");

        final val items = getItems();

        if (items != null) {
            final int itemsCount = items.size();
            final int newItemsCount = newItems.size();

            final int changedCount = Math.min(itemsCount, newItemsCount);
            final int removedCount = Math.max(0, itemsCount - changedCount);
            final int insertedCount = Math.max(0, newItemsCount - changedCount);

            items.clear();
            items.addAll(newItems);

            if (notify) {
                if (changedCount > 0) {
                    notifyItemRangeChanged(getInnerItemAdapterPosition(0), changedCount);
                }
                if (removedCount > 0) {
                    notifyItemRangeRemoved(getInnerItemAdapterPosition(changedCount), removedCount);
                }
                if (insertedCount > 0) {
                    notifyItemRangeInserted(getInnerItemAdapterPosition(changedCount),
                                            insertedCount);
                }
            }
        }
    }

    public final void removeItem(final int position) {
        removeItem(position, true);
    }

    public final void removeItem(final int position, final boolean notify) {
        final val items = getItems();

        if (items != null) {
            items.remove(position);

            if (notify) {
                notifyItemRemoved(getInnerItemAdapterPosition(position));
            }
        }
    }

    public final void removeItems(final boolean notify) {
        final val items = getItems();

        if (items != null) {
            final int itemsCount = items.size();

            items.clear();

            if (notify) {
                notifyItemRangeRemoved(getInnerItemAdapterPosition(0), itemsCount);
            }
        }
    }

    public final void removeItems() {
        removeItems(true);
    }

    public final void setItem(final int position, @NonNull final TItem newItem) {
        setItem(position, newItem, true);
    }

    public final void setItem(
        final int position, @NonNull final TItem newItem, final boolean notify) {
        final val items = getItems();

        if (items != null) {
            items.set(position, newItem);

            if (notify) {
                notifyItemChanged(getInnerItemAdapterPosition(position));
            }
        }
    }

    public final void setItems(@NonNull final Collection<TItem> newItems) {
        Contracts.requireNonNull(newItems, "newItems == null");

        setItems(newItems, true);
    }

    public final void setItems(@NonNull final Collection<TItem> newItems, final boolean notify) {
        Contracts.requireNonNull(newItems, "newItems == null");

        final val items = getItems();

        if (items != null) {
            items.clear();
            items.addAll(newItems);

            if (notify) {
                notifyDataSetChanged();
            }
        }
    }
}
