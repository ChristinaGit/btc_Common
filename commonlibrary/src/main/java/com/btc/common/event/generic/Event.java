package com.btc.common.event.generic;

import android.support.annotation.NonNull;

import com.btc.common.event.eventArgs.EventArgs;

public interface Event<TEventArgs extends EventArgs> {
    void addHandler(@NonNull EventHandler<TEventArgs> handler);

    void removeHandler(@NonNull EventHandler<TEventArgs> handler);
}
