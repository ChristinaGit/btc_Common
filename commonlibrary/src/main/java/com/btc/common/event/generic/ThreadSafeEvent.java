package com.btc.common.event.generic;

import android.support.annotation.NonNull;

import lombok.val;

import com.btc.common.contract.Contracts;
import com.btc.common.event.eventArgs.EventArgs;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ThreadSafeEvent<TEventArgs extends EventArgs>
    implements ManagedEvent<TEventArgs> {
    @Override
    public final void addHandler(@NonNull final EventHandler<TEventArgs> handler) {
        Contracts.requireNonNull(handler, "handler == null");

        _handlers.add(handler);
    }

    @Override
    public final void removeHandler(@NonNull final EventHandler<TEventArgs> handler) {
        Contracts.requireNonNull(handler, "handler == null");

        _handlers.remove(handler);
    }

    @Override
    public final boolean hasHandlers() {
        return !_handlers.isEmpty();
    }

    @Override
    public final void removeAllHandlers() {
        _handlers.clear();
    }

    @Override
    public final void rise(@NonNull final TEventArgs eventArgs) {
        Contracts.requireNonNull(eventArgs, "eventArgs == null");

        for (final val handler : _handlers) {
            handler.onEvent(eventArgs);
        }
    }

    @NonNull
    private final Collection<EventHandler<TEventArgs>> _handlers = new CopyOnWriteArrayList<>();
}
