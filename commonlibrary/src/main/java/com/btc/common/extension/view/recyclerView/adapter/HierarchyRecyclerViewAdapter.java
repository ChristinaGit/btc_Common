package com.btc.common.extension.view.recyclerView.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import lombok.val;

import com.android.internal.util.Predicate;
import com.btc.common.contract.Contracts;
import com.btc.common.extension.view.recyclerView.viewHolder.ExtendedRecyclerViewHolder;

public abstract class HierarchyRecyclerViewAdapter<TGroupParentItem, TGroupChildItem>
    extends WrappedRecyclerViewAdapter<ExtendedRecyclerViewHolder> {
    public static final int GROUP_PARENT_CHILD_POSITION = -1;

    public static final int VIEW_TYPE_GROUP_PARENT = newViewType();

    public static final int VIEW_TYPE_GROUP_CHILD = newViewType();

    public int getGroupChildCount() {
        int childCount = 0;

        final int parentCount = getGroupParentCount();

        for (int i = 0; i < parentCount; i++) {
            childCount += getGroupChildCountOfGroup(i);
        }

        return childCount;
    }

    @Override
    public int getInnerItemCount() {
        return getGroupParentCount() + getGroupChildCount();
    }

    @Override
    protected int getInnerItemViewType(final int position) {
        final int viewType;

        final int groupChildIndex = getGroupChildIndex(position);
        if (groupChildIndex == GROUP_PARENT_CHILD_POSITION) {
            viewType = getGroupParentViewType(getGroupParentIndex(position));
        } else {
            viewType = getGroupChildViewType(getGroupParentIndex(position), groupChildIndex);
        }

        return viewType;
    }

    @Override
    protected boolean isInnerItemViewType(final int viewType) {
        return isGroupParentViewType(viewType) || isGroupChildViewType(viewType);
    }

    @CallSuper
    @Override
    protected void onBindInnerItemViewHolder(
        @NonNull final ExtendedRecyclerViewHolder holder, final int position) {
        Contracts.requireNonNull(holder, "holder == null");

        final int viewType = getItemViewType(position);
        if (isGroupParentViewType(viewType)) {
            final int groupParentIndex = getGroupParentIndex(position);
            final val item = getGroupParent(groupParentIndex);
            onBindGroupParentViewHolder(holder, item, position, groupParentIndex);
        } else if (isGroupChildViewType(viewType)) {
            final int groupParentIndex = getGroupParentIndex(position);
            final int groupChildIndex = getGroupChildIndex(position);
            final val item = getGroupChild(groupParentIndex, groupChildIndex);
            onBindGroupChildViewHolder(holder, item, position, groupParentIndex, groupChildIndex);
        } else {
            super.onBindInnerItemViewHolder(holder, position);
        }
    }

    @CallSuper
    @NonNull
    @Override
    protected ExtendedRecyclerViewHolder onCreateInnerItemViewHolder(
        final ViewGroup parent, final int viewType) {
        final ExtendedRecyclerViewHolder holder;

        if (isGroupParentViewType(viewType)) {
            holder = onCreateGroupParentViewHolder(parent, viewType);
        } else if (isGroupChildViewType(viewType)) {
            holder = onCreateGroupChildViewHolder(parent, viewType);
        } else {
            holder = super.onCreateInnerItemViewHolder(parent, viewType);
        }

        return holder;
    }

    public void notifyGroupChildItemChanged(final int groupParentIndex, final int groupChildIndex) {
        notifyItemChanged(getGroupChildAdapterPosition(groupParentIndex, groupChildIndex));
    }

    public void notifyGroupParentItemChanged(final int groupParentIndex) {
        notifyItemChanged(getGroupParentAdapterPosition(groupParentIndex));
    }

    @NonNull
    public abstract TGroupChildItem getGroupChild(
        final int groupParentIndex, final int groupChildIndex);

    public abstract int getGroupChildCountOfGroup(final int groupParentIndex);

    @NonNull
    public abstract TGroupParentItem getGroupParent(final int groupParentIndex);

    public abstract int getGroupParentCount();

    @Nullable
    protected final Integer findGroupChildPosition(
        @NonNull final Predicate<TGroupChildItem> predicate) {
        Contracts.requireNonNull(predicate, "predicate == null");

        final Integer position;

        final val relativePosition = findGroupChildRelativePosition(predicate);
        if (relativePosition != null) {
            position = getInnerItemAdapterPosition(relativePosition);
        } else {
            position = null;
        }

        return position;
    }

    protected final int getGroupChildIndex(final int position) {
        return getGroupChildIndexByRelativePosition(getInnerItemRelativePosition(position));
    }

    @Nullable
    protected Integer findGroupChildRelativePosition(
        @NonNull final Predicate<TGroupChildItem> predicate) {
        Contracts.requireNonNull(predicate, "predicate == null");

        Integer groupChildPosition = null;

        int position = 0;
        final int groupParentCount = getGroupParentCount();
search:
        for (int i = 0; i < groupParentCount; i++) {
            position++;

            final int groupChildCount = getGroupChildCountOfGroup(i);
            for (int j = 0; j < groupChildCount; j++) {
                final val groupChild = getGroupChild(i, j);
                if (predicate.apply(groupChild)) {
                    groupChildPosition = position;
                    break search;
                }

                position++;
            }
        }

        return groupChildPosition;
    }

    protected int getGroupChildAdapterPosition(
        final int groupParentIndex, final int groupChildIndex) {
        return getGroupParentAdapterPosition(groupParentIndex) + 1 + groupChildIndex;
    }

    protected int getGroupChildIndexByRelativePosition(final int relativePosition) {
        Integer groupChildIndex = null;

        final int groupParentCount = getGroupParentCount();

        int groupStart;
        int groupEnd = 0;

        int currentGroupParentIndex = -1;
        do {
            currentGroupParentIndex++;

            groupStart = groupEnd;
            groupEnd = groupStart + 1;

            final val groupChildCount = getGroupChildCountOfGroup(currentGroupParentIndex);

            groupEnd += groupChildCount;

            if (groupStart < relativePosition && relativePosition < groupEnd) {
                groupChildIndex = relativePosition - groupStart - 1;
                break;
            } else if (groupStart == relativePosition) {
                groupChildIndex = GROUP_PARENT_CHILD_POSITION;
                break;
            }
        } while (groupParentCount > currentGroupParentIndex);

        if (groupChildIndex == null) {
            throw new IndexOutOfBoundsException(
                "No group at relative position: " + relativePosition);
        }

        return groupChildIndex;
    }

    protected int getGroupChildViewType(
        final int groupParentIndex, final int groupChildIndex) {
        return VIEW_TYPE_GROUP_CHILD;
    }

    protected int getGroupParentAdapterPosition(final int groupParentIndex) {
        return getInnerItemAdapterPosition(getGroupParentRelativePosition(groupParentIndex));
    }

    protected int getGroupParentIndex(final int position) {
        return getGroupParentIndexByRelativePosition(getInnerItemRelativePosition(position));
    }

    protected int getGroupParentIndexByRelativePosition(final int position) {
        Integer groupParentIndex = null;

        final int groupParentCount = getGroupParentCount();

        int groupStart;
        int groupEnd = 0;

        int currentGroupParentIndex = -1;
        do {
            currentGroupParentIndex++;

            groupStart = groupEnd;
            groupEnd = groupStart + 1;
            groupEnd += getGroupChildCountOfGroup(currentGroupParentIndex);

            if (groupStart <= position && position < groupEnd) {
                groupParentIndex = currentGroupParentIndex;
                break;
            }
        } while (groupParentCount > currentGroupParentIndex);

        if (groupParentIndex == null) {
            throw new IndexOutOfBoundsException("No group at position: " + position);
        }

        return groupParentIndex;
    }

    protected int getGroupParentRelativePosition(final int groupParentIndex) {
        int position = 0;

        final int groupParentCount = getGroupParentCount();
        for (int i = 0; i < groupParentCount && i < groupParentIndex; i++) {
            position++;
            position += getGroupChildCountOfGroup(i);
        }

        return position;
    }

    protected int getGroupParentViewType(final int groupParentIndex) {
        return VIEW_TYPE_GROUP_PARENT;
    }

    protected boolean isGroupChildViewType(final int viewType) {
        return VIEW_TYPE_GROUP_CHILD == viewType;
    }

    protected boolean isGroupParentViewType(final int viewType) {
        return VIEW_TYPE_GROUP_PARENT == viewType;
    }

    @CallSuper
    protected void onBindGroupChildViewHolder(
        @NonNull final ExtendedRecyclerViewHolder holder,
        @NonNull final TGroupChildItem item,
        final int position,
        final int groupParentIndex,
        final int groupChildIndex) {
        Contracts.requireNonNull(holder, "holder == null");
        Contracts.requireNonNull(item, "item == null");
    }

    @CallSuper
    protected void onBindGroupParentViewHolder(
        @NonNull final ExtendedRecyclerViewHolder holder,
        @NonNull final TGroupParentItem item,
        final int position,
        final int groupParentIndex) {
        Contracts.requireNonNull(holder, "holder == null");
        Contracts.requireNonNull(item, "item == null");
    }

    @NonNull
    protected abstract ExtendedRecyclerViewHolder onCreateGroupChildViewHolder(
        final ViewGroup parent, final int viewType);

    @NonNull
    protected abstract ExtendedRecyclerViewHolder onCreateGroupParentViewHolder(
        final ViewGroup parent, final int viewType);
}
