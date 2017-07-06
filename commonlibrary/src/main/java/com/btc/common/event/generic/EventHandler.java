package com.btc.common.event.generic;

import android.support.annotation.NonNull;

import com.btc.common.event.eventArgs.EventArgs;

public interface EventHandler<TEventArgs extends EventArgs> {
    void onEvent(@NonNull TEventArgs eventArgs);
}
