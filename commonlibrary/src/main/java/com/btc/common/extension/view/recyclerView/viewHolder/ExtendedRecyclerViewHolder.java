package com.btc.common.extension.view.recyclerView.viewHolder;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.btc.common.contract.Contracts;
import com.btc.common.extension.ViewBinder;

@Accessors(prefix = "_")
public abstract class ExtendedRecyclerViewHolder extends RecyclerView.ViewHolder
    implements ViewBinder {
    @NonNull
    public final Context getContext() {
        return itemView.getContext();
    }

    @Nullable
    public final <T> T getTag(@NonNull final Class<T> tagClass, @IdRes final int tagId) {
        Contracts.requireNonNull(tagClass, "tagClass == null");

        final val tag = itemView.getTag(tagId);
        if (tagClass.isInstance(tag)) {
            return (T) tag;
        } else {
            return null;
        }
    }

    public final <T> void setTag(@IdRes final int tagId, @Nullable final T tag) {
        itemView.setTag(tagId, tag);
    }

    @CallSuper
    @Override
    public void bindViews() {
        unbindViews();

        _unbinder = ButterKnife.bind(this, itemView);
    }

    @CallSuper
    @Override
    public void bindViews(@NonNull final View source) {
        Contracts.requireNonNull(source, "source == null");

        unbindViews();

        _unbinder = ButterKnife.bind(this, source);
    }

    @CallSuper
    @Override
    public void unbindViews() {
        if (_unbinder != null) {
            _unbinder.unbind();
        }
    }

    protected ExtendedRecyclerViewHolder(@NonNull final View itemView) {
        super(Contracts.requireNonNull(itemView, "itemView == null"));
    }

    @Getter
    private Unbinder _unbinder;
}
