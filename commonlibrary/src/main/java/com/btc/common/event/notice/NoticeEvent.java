package com.btc.common.event.notice;

import android.support.annotation.NonNull;

public interface NoticeEvent {
    void addHandler(@NonNull NoticeEventHandler handler);

    void removeHandler(@NonNull NoticeEventHandler handler);
}
