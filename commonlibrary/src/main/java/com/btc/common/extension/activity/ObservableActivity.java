package com.btc.common.extension.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.btc.common.event.generic.Event;
import com.btc.common.event.notice.NoticeEvent;
import com.btc.common.extension.eventArgs.ActivityResultEventArgs;
import com.btc.common.extension.eventArgs.BundleEventArgs;
import com.btc.common.extension.eventArgs.PermissionResultEventArgs;

public interface ObservableActivity {
    @NonNull
    AppCompatActivity asActivity();

    @NonNull
    Event<ActivityResultEventArgs> getActivityResultEvent();

    @NonNull
    Event<BundleEventArgs> getCreateEvent();

    @NonNull
    NoticeEvent getDestroyEvent();

    @NonNull
    NoticeEvent getPauseEvent();

    @NonNull
    Event<PermissionResultEventArgs> getPermissionResultEvent();

    @NonNull
    Event<BundleEventArgs> getRestoreInstanceStateEvent();

    @NonNull
    NoticeEvent getResumeEvent();

    @NonNull
    Event<BundleEventArgs> getSaveInstanceStateEvent();

    @NonNull
    NoticeEvent getStartEvent();

    @NonNull
    NoticeEvent getStopEvent();
}
