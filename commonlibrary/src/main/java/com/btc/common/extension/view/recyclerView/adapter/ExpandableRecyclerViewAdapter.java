package com.btc.common.extension.view.recyclerView.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import com.android.internal.util.Predicate;
import com.btc.common.R;
import com.btc.common.contract.Contracts;
import com.btc.common.extension.view.recyclerView.viewHolder.ExtendedRecyclerViewHolder;

import java.util.HashSet;
import java.util.Set;

@Accessors(prefix = "_")
public abstract class ExpandableRecyclerViewAdapter<TGroupParentItem, TGroupChildItem>
    extends HierarchyRecyclerViewAdapter<TGroupParentItem, TGroupChildItem> {
    public final void collapseAllGroups() {
        for (final val groupParentIndex : _expandedGroups) {
            collapseGroup(groupParentIndex);
        }
    }

    public final void collapseGroup(final int groupParentIndex) {
        if (isExpanded(groupParentIndex)) {
            final int groupChildCount = getGroupChildCountOfGroup(groupParentIndex);

            final int groupParentPosition = getGroupParentAdapterPosition(groupParentIndex);

            _expandedGroups.remove(groupParentIndex);

            if (groupChildCount > 0) {
                notifyItemChanged(groupParentPosition);
                notifyItemRangeRemoved(groupParentPosition + 1, groupChildCount);
            }
        }
    }

    public final void expandAllGroups() {
        final val groupParentCount = getGroupParentCount();
        for (int i = 0; i < groupParentCount; i++) {
            expandGroup(i);
        }
    }

    public final void expandGroup(final int groupParentIndex) {
        if (isCollapsed(groupParentIndex)) {
            final int groupChildCount = getGroupChildCountOfGroup(groupParentIndex);

            final int groupParentPosition = getGroupParentAdapterPosition(groupParentIndex);

            _expandedGroups.add(groupParentIndex);

            if (groupChildCount > 0) {
                notifyItemChanged(groupParentPosition);
                notifyItemRangeInserted(groupParentPosition + 1, groupChildCount);
            }
        }
    }

    public final boolean isCollapsed(final int groupParentIndex) {
        return !isExpanded(groupParentIndex);
    }

    public final boolean isExpanded(final int groupParentIndex) {
        return _expandedGroups.contains(groupParentIndex);
    }

    public final void resetGroupExpandState() {
        _expandedGroups.clear();
    }

    public final void setGroupExpanded(final int groupParentIndex, final boolean expanded) {
        if (expanded) {
            expandGroup(groupParentIndex);
        } else {
            collapseGroup(groupParentIndex);
        }
    }

    @Override
    public int getGroupChildCount() {
        int childCount = 0;

        final int parentCount = getGroupParentCount();

        for (int i = 0; i < parentCount; i++) {
            if (isExpanded(i)) {
                childCount += getGroupChildCountOfGroup(i);
            }
        }

        return childCount;
    }

    @Override
    @Nullable
    protected final Integer findGroupChildRelativePosition(
        @NonNull final Predicate<TGroupChildItem> predicate) {
        Contracts.requireNonNull(predicate, "predicate == null");

        Integer groupChildPosition = null;

        int position = 0;
        final int groupParentCount = getGroupParentCount();
search:
        for (int i = 0; i < groupParentCount; i++) {
            position++;

            if (isExpanded(i)) {
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
        }

        return groupChildPosition;
    }

    @Override
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

            if (isExpanded(currentGroupParentIndex)) {
                final val groupChildCount = getGroupChildCountOfGroup(currentGroupParentIndex);

                groupEnd += groupChildCount;
            }

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

    @Override
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
            if (isExpanded(currentGroupParentIndex)) {
                groupEnd += getGroupChildCountOfGroup(currentGroupParentIndex);
            }

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

    @Override
    protected int getGroupParentRelativePosition(final int groupParentIndex) {
        int position = 0;

        final int groupParentCount = getGroupParentCount();
        for (int i = 0; i < groupParentCount && i < groupParentIndex; i++) {
            position++;
            if (isExpanded(i)) {
                position += getGroupChildCountOfGroup(i);
            }
        }

        return position;
    }

    @Override
    @CallSuper
    protected void onBindGroupChildViewHolder(
        @NonNull final ExtendedRecyclerViewHolder holder,
        @NonNull final TGroupChildItem item,
        final int position,
        final int groupParentIndex,
        final int groupChildIndex) {
        super.onBindGroupChildViewHolder(
            Contracts.requireNonNull(holder, "holder == null"),
            Contracts.requireNonNull(item, "item == null"),
            position,
            groupParentIndex,
            groupChildIndex);

        holder.itemView.setTag(R.id.tag_item_group_parent_index, groupParentIndex);
        holder.itemView.setTag(R.id.tag_item_group_child_index, groupChildIndex);
        holder.itemView.setOnClickListener(_riseGroupChildClickOnClick);
    }

    @Override
    @CallSuper
    protected void onBindGroupParentViewHolder(
        @NonNull final ExtendedRecyclerViewHolder holder,
        @NonNull final TGroupParentItem item,
        final int position,
        final int groupParentIndex) {
        super.onBindGroupParentViewHolder(
            Contracts.requireNonNull(holder, "holder == null"),
            Contracts.requireNonNull(item, "item == null"),
            position,
            groupParentIndex);

        holder.itemView.setTag(R.id.tag_item_group_parent_index, groupParentIndex);
        holder.itemView.setOnClickListener(_riseGroupParentClickOnClick);
    }

    public boolean isExpandableGroup(final int groupParentIndex) {
        return true;
    }

    @CallSuper
    protected void onGroupChildClick(final int groupParentIndex, final int groupChildIndex) {
    }

    @CallSuper
    protected void onGroupParentClick(final int groupParentIndex) {
        if (isExpandableGroup(groupParentIndex)) {
            setGroupExpanded(groupParentIndex, !isExpanded(groupParentIndex));
        }
    }

    @Getter
    @NonNull
    private final Set<Integer> _expandedGroups = new HashSet<>();

    @NonNull
    private final View.OnClickListener _riseGroupChildClickOnClick = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            final val groupParentIndex = (int) v.getTag(R.id.tag_item_group_parent_index);
            final val groupChildIndex = (int) v.getTag(R.id.tag_item_group_child_index);

            onGroupChildClick(groupParentIndex, groupChildIndex);
        }
    };

    @NonNull
    private final View.OnClickListener _riseGroupParentClickOnClick = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            final int groupParentIndex = (int) v.getTag(R.id.tag_item_group_parent_index);

            onGroupParentClick(groupParentIndex);
        }
    };
}
