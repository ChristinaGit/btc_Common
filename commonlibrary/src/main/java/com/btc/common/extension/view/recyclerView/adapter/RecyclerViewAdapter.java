package com.btc.common.extension.view.recyclerView.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btc.common.contract.Contracts;
import com.btc.common.extension.view.recyclerView.viewHolder.ExtendedRecyclerViewHolder;

public abstract class RecyclerViewAdapter<TViewHolder extends ExtendedRecyclerViewHolder>
    extends RecyclerView.Adapter<TViewHolder> {
    private static int _viewTypeIndexer = 1;

    protected static int newViewType() {
        return ++_viewTypeIndexer;
    }

    @NonNull
    protected final View inflateView(
        @LayoutRes final int layoutId, @NonNull final ViewGroup parent) {
        Contracts.requireNonNull(parent, "parent == null");

        if (_layoutInflater == null) {
            _layoutInflater = LayoutInflater.from(parent.getContext());
        }

        return _layoutInflater.inflate(layoutId, parent, false);
    }

    @Nullable
    private LayoutInflater _layoutInflater;
}
