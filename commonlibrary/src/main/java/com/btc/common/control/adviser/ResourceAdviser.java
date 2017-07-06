package com.btc.common.control.adviser;

import android.support.annotation.NonNull;

import com.btc.common.event.notice.NoticeEvent;

public interface ResourceAdviser {
    @NonNull
    NoticeEvent getAcquireResourcesEvent();

    @NonNull
    NoticeEvent getReleaseResourcesEvent();
}
