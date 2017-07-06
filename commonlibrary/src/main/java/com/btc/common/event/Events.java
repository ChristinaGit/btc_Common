package com.btc.common.event;

import android.support.annotation.NonNull;

import com.btc.common.contract.Contracts;
import com.btc.common.event.eventArgs.EventArgs;
import com.btc.common.event.generic.ManagedEvent;
import com.btc.common.event.generic.ThreadSafeEvent;
import com.btc.common.event.notice.ManagedNoticeEvent;
import com.btc.common.event.notice.ThreadSafeNoticeEvent;

public final class Events {
    @NonNull
    public static ManagedNoticeEvent createNoticeEvent() {
        return new ThreadSafeNoticeEvent();
    }

    @NonNull
    public static <TEventArgs extends EventArgs> ManagedEvent<TEventArgs> createEvent() {
        return new ThreadSafeEvent<>();
    }

    @NonNull
    public static ManagedNoticeEvent createThreadSafeNoticeEvent() {
        return new ThreadSafeNoticeEvent();
    }

    @NonNull
    public static <TEventArgs extends EventArgs> ManagedEvent<TEventArgs> createThreadSafeEvent() {
        return new ThreadSafeEvent<>();
    }

    private Events() {
        Contracts.unreachable();
    }
}
