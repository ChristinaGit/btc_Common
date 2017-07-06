package com.btc.common.extension.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import rx.Observable;
import rx.subjects.BehaviorSubject;

import com.btc.common.contract.Contracts;
import com.btc.common.control.adviser.ResourceAdviser;
import com.btc.common.event.Events;
import com.btc.common.event.generic.Event;
import com.btc.common.event.generic.ManagedEvent;
import com.btc.common.event.notice.ManagedNoticeEvent;
import com.btc.common.event.notice.NoticeEvent;
import com.btc.common.extension.ViewBinder;
import com.btc.common.extension.eventArgs.ActivityResultEventArgs;
import com.btc.common.extension.eventArgs.BundleEventArgs;
import com.btc.common.extension.eventArgs.PermissionResultEventArgs;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

@Accessors(prefix = "_")
public abstract class ExtendedActivity extends AppCompatActivity
    implements ViewBinder, ObservableActivity, LifecycleProvider<ActivityEvent>, ResourceAdviser {
    @NonNull
    @Override
    public final AppCompatActivity asActivity() {
        return this;
    }

    @Override
    @NonNull
    public final Event<ActivityResultEventArgs> getActivityResultEvent() {
        return _activityResultEvent;
    }

    @Override
    @NonNull
    public final Event<BundleEventArgs> getCreateEvent() {
        return _createEvent;
    }

    @Override
    @NonNull
    public final NoticeEvent getDestroyEvent() {
        return _destroyEvent;
    }

    @Override
    @NonNull
    public final NoticeEvent getPauseEvent() {
        return _pauseEvent;
    }

    @NonNull
    @Override
    public final Event<PermissionResultEventArgs> getPermissionResultEvent() {
        return _permissionResultEvent;
    }

    @Override
    @NonNull
    public final Event<BundleEventArgs> getRestoreInstanceStateEvent() {
        return _restoreInstanceStateEvent;
    }

    @Override
    @NonNull
    public final NoticeEvent getResumeEvent() {
        return _resumeEvent;
    }

    @Override
    @NonNull
    public final Event<BundleEventArgs> getSaveInstanceStateEvent() {
        return _saveInstanceStateEvent;
    }

    @Override
    @NonNull
    public final NoticeEvent getStartEvent() {
        return _startEvent;
    }

    @Override
    @NonNull
    public final NoticeEvent getStopEvent() {
        return _stopEvent;
    }

    @NonNull
    @Override
    public final NoticeEvent getAcquireResourcesEvent() {
        return _acquireResourcesEvent;
    }

    @NonNull
    @Override
    public final NoticeEvent getReleaseResourcesEvent() {
        return _releaseResourcesEvent;
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return getLifecycleSubject().asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final ActivityEvent event) {
        Contracts.requireNonNull(event, "event == null");

        return RxLifecycle.bindUntilEvent(getLifecycleSubject(), event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(getLifecycleSubject());
    }

    @CallSuper
    @Override
    public void bindViews() {
        unbindViews();

        _unbinder = ButterKnife.bind(this);
    }

    @CallSuper
    @Override
    public void bindViews(@NonNull final View source) {
        Contracts.requireNonNull(source, "source == null");

        unbindViews();

        _unbinder = ButterKnife.bind(this, source);
    }

    @CallSuper
    @Override
    public void unbindViews() {
        final val unbinder = getUnbinder();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public void setSubtitle(@StringRes final int subtitleId) {
        final val actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitleId);
        }

        final val supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setSubtitle(subtitleId);
        }
    }

    public void setSubtitle(@Nullable final String subtitle) {
        final val actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }

        final val supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setSubtitle(subtitle);
        }
    }

    @CallSuper
    protected void onAcquireResources() {
        _acquireResourcesEvent.rise();
    }

    @CallSuper
    @Override
    protected void onActivityResult(
        final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final val eventArgs = new ActivityResultEventArgs(requestCode, resultCode, data);
        _activityResultEvent.rise(eventArgs);
    }

    @CallSuper
    @Override
    protected void onPause() {
        super.onPause();

        _pauseEvent.rise();

        getLifecycleSubject().onNext(ActivityEvent.PAUSE);
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();

        _resumeEvent.rise();

        getLifecycleSubject().onNext(ActivityEvent.RESUME);
    }

    @Override
    public void onRequestPermissionsResult(
        final int requestCode,
        @NonNull final String[] permissions,
        @NonNull final int[] grantResults) {
        //@formatter:off
        super.onRequestPermissionsResult(
            requestCode,
            Contracts.requireNonNull(permissions, "permissions == null"),
            Contracts.requireNonNull(grantResults, "grantResults == null"));
        //@formatter:on

        _permissionResultEvent.rise(new PermissionResultEventArgs(permissions, grantResults));
    }

    @CallSuper
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onInjectMembers();

        onAcquireResources();

        _createEvent.rise(new BundleEventArgs(savedInstanceState));

        getLifecycleSubject().onNext(ActivityEvent.CREATE);
    }

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();

        _startEvent.rise();

        getLifecycleSubject().onNext(ActivityEvent.START);
    }

    @CallSuper
    @Override
    protected void onStop() {
        super.onStop();

        getLifecycleSubject().onNext(ActivityEvent.STOP);

        _stopEvent.rise();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();

        _destroyEvent.rise();

        getLifecycleSubject().onNext(ActivityEvent.DESTROY);

        onReleaseResources();

        onReleaseInjectedMembers();
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        _saveInstanceStateEvent.rise(new BundleEventArgs(outState));
    }

    @CallSuper
    protected void onInjectMembers() {
    }

    @CallSuper
    protected void onReleaseInjectedMembers() {
    }

    @CallSuper
    protected void onReleaseResources() {
        _releaseResourcesEvent.rise();
    }

    @CallSuper
    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        _restoreInstanceStateEvent.rise(new BundleEventArgs(savedInstanceState));
    }

    @NonNull
    private final ManagedNoticeEvent _acquireResourcesEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedEvent<ActivityResultEventArgs> _activityResultEvent = Events.createEvent();

    @NonNull
    private final ManagedEvent<BundleEventArgs> _createEvent = Events.createEvent();

    @NonNull
    private final ManagedNoticeEvent _destroyEvent = Events.createNoticeEvent();

    @Getter(AccessLevel.PRIVATE)
    @NonNull
    private final BehaviorSubject<ActivityEvent> _lifecycleSubject = BehaviorSubject.create();

    @NonNull
    private final ManagedNoticeEvent _pauseEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedEvent<PermissionResultEventArgs> _permissionResultEvent =
        Events.createEvent();

    @NonNull
    private final ManagedNoticeEvent _releaseResourcesEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedEvent<BundleEventArgs> _restoreInstanceStateEvent = Events.createEvent();

    @NonNull
    private final ManagedNoticeEvent _resumeEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedEvent<BundleEventArgs> _saveInstanceStateEvent = Events.createEvent();

    @NonNull
    private final ManagedNoticeEvent _startEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedNoticeEvent _stopEvent = Events.createNoticeEvent();

    @Getter(AccessLevel.PROTECTED)
    @Nullable
    private Unbinder _unbinder;
}
