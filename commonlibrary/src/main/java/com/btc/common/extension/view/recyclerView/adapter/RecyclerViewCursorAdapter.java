package com.btc.common.extension.view.recyclerView.adapter;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.extension.view.recyclerView.viewHolder.ExtendedRecyclerViewHolder;
import com.btc.common.utility.ResourceUtils;

@Accessors(prefix = "_")
public abstract class RecyclerViewCursorAdapter<TItem, TViewHolder extends
    ExtendedRecyclerViewHolder>
    extends RecyclerViewAdapter<TViewHolder> {

    @NonNull
    public TItem getItem(final int position) {
        final TItem item;

        final val cursor = getCursor();
        if (isDataValid() && cursor != null && cursor.moveToPosition(position)) {
            item = getItem(cursor);
        } else {
            throw new IllegalStateException("No data by position: " + position);
        }

        return item;
    }

    @Override
    public int getItemCount() {
        final int itemCount;

        final val cursor = getCursor();
        if (isDataValid() && cursor != null) {
            itemCount = cursor.getCount();
        } else {
            itemCount = 0;
        }

        return itemCount;
    }

    @CallSuper
    public void setCursor(@Nullable final Cursor cursor) {
        setCursor(cursor, true);
    }

    @CallSuper
    public void setCursor(@Nullable final Cursor cursor, final boolean notify) {
        final val oldCursor = swapCursor(cursor, notify);

        ResourceUtils.quietClose(oldCursor);
    }

    @CallSuper
    @Nullable
    public Cursor swapCursor(@Nullable final Cursor dataCursor) {
        return swapCursor(dataCursor, true);
    }

    @CallSuper
    @Nullable
    public Cursor swapCursor(
        @Nullable final Cursor cursor, final boolean notify) {
        final Cursor oldCursor;

        if (_cursor != cursor) {
            oldCursor = _cursor;

            _cursor = cursor;

            if (oldCursor != null) {
                oldCursor.unregisterDataSetObserver(getInternalDataSetObserver());
            }

            if (_cursor != null) {
                _cursor.registerDataSetObserver(getInternalDataSetObserver());
            }

            _dataValid = _cursor != null;

            if (notify) {
                final int oldItemsCount = oldCursor != null ? oldCursor.getCount() : 0;
                final int itemsCount = cursor != null ? cursor.getCount() : 0;

                final int changedCount = Math.min(oldItemsCount, itemsCount);
                final int removedCount = Math.max(0, oldItemsCount - changedCount);
                final int insertedCount = Math.max(0, itemsCount - changedCount);

                if (changedCount > 0) {
                    notifyItemRangeChanged(0, changedCount);
                }
                if (removedCount > 0) {
                    notifyItemRangeRemoved(changedCount, removedCount);
                }
                if (insertedCount > 0) {
                    notifyItemRangeInserted(changedCount, insertedCount);
                }
            }
        } else {
            oldCursor = null;
        }

        return oldCursor;
    }

    @CallSuper
    protected void onCursorSetChanged() {
        _dataValid = true;
        notifyDataSetChanged();
    }

    @CallSuper
    protected void onCursorSetInvalidated() {
        _dataValid = false;
        notifyDataSetChanged();
    }

    @NonNull
    protected abstract TItem getItem(@NonNull final Cursor cursor);

    @Getter(value = AccessLevel.PRIVATE)
    @NonNull
    private final InternalDataSetObserver _internalDataSetObserver = new InternalDataSetObserver();

    @Getter
    @Nullable
    private Cursor _cursor;

    @Getter
    private boolean _dataValid = false;

    private final class InternalDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();

            onCursorSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();

            onCursorSetInvalidated();
        }
    }
}
