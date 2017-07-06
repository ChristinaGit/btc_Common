package com.btc.common.extension.view.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public interface ViewHolderBinder<TViewHolder extends RecyclerView.ViewHolder> {
    void bindViewHolder(@NonNull TViewHolder holder, int position);
}
