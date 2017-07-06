package com.btc.common.extension.view.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface RecyclerViewCreator {
    void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    @NonNull
    RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);
}
