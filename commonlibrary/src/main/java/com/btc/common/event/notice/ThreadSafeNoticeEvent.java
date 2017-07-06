package com.btc.common.event.notice;

import android.support.annotation.NonNull;

import lombok.val;

import com.btc.common.contract.Contracts;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ThreadSafeNoticeEvent implements ManagedNoticeEvent {
    @Override
    public final void addHandler(@NonNull final NoticeEventHandler handler) {
        Contracts.requireNonNull(handler, "handler == null");

        _handlers.add(handler);
    }

    @Override
    public final void removeHandler(@NonNull final NoticeEventHandler handler) {
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
    public final void rise() {
        for (final val handler : _handlers) {
            handler.onEvent();
        }
    }

    @NonNull
    private final Collection<NoticeEventHandler> _handlers = new CopyOnWriteArrayList<>();
}
