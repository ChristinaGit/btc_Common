package com.btc.common.extension.view.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.btc.common.contract.Contracts;

public class DefaultSpanLookup implements SpanLookup {
    @Override
    public int getSpanCount(@NonNull final RecyclerView.LayoutManager layoutManager) {
        Contracts.requireNonNull(layoutManager, "layoutManager == null");

        final int spanCount;

        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof LinearLayoutManager) {
            spanCount = 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else {
            spanCount = 1;
        }

        return spanCount;
    }
}
