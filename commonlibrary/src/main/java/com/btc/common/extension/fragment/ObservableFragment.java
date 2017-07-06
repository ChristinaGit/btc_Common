package com.btc.common.extension.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.btc.common.event.generic.Event;
import com.btc.common.event.notice.NoticeEvent;
import com.btc.common.extension.eventArgs.ActivityResultEventArgs;
import com.btc.common.extension.eventArgs.BundleEventArgs;
import com.btc.common.extension.eventArgs.PermissionResultEventArgs;

public interface ObservableFragment {
    @NonNull
    Fragment asFragment();

    @NonNull
    Event<ActivityResultEventArgs> getActivityResultEvent();

    @NonNull
    NoticeEvent getAttachEvent();

    @NonNull
    Event<BundleEventArgs> getCreateEvent();

    @NonNull
    Event<BundleEventArgs> getCreateViewEvent();

    @NonNull
    NoticeEvent getDestroyEvent();

    @NonNull
    NoticeEvent getDestroyViewEvent();

    @NonNull
    NoticeEvent getDetachEvent();

    @NonNull
    NoticeEvent getPauseEvent();

    @NonNull
    Event<PermissionResultEventArgs> getPermissionResultEvent();

    @NonNull
    NoticeEvent getResumeEvent();

    @NonNull
    Event<BundleEventArgs> getSaveInstanceStateEvent();

    @NonNull
    NoticeEvent getStartEvent();

    @NonNull
    NoticeEvent getStopEvent();

    @NonNull
    Event<BundleEventArgs> getViewCreatedEvent();

    @NonNull
    Event<BundleEventArgs> getViewStateRestoredEvent();
}
