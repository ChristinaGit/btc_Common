package com.btc.common.extension.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

@Accessors(prefix = "_")
public abstract class ExtendedDialogFragment extends AppCompatDialogFragment
    implements ViewBinder, ObservableFragment, LifecycleProvider<FragmentEvent>, ResourceAdviser {
    @NonNull
    @Override
    public final Fragment asFragment() {
        return this;
    }

    @Override
    @NonNull
    public final Event<ActivityResultEventArgs> getActivityResultEvent() {
        return _activityResultEvent;
    }

    @Override
    @NonNull
    public final NoticeEvent getAttachEvent() {
        return _attachEvent;
    }

    @Override
    @NonNull
    public final Event<BundleEventArgs> getCreateEvent() {
        return _createEvent;
    }

    @Override
    @NonNull
    public final Event<BundleEventArgs> getCreateViewEvent() {
        return _createViewEvent;
    }

    @Override
    @NonNull
    public final NoticeEvent getDestroyEvent() {
        return _destroyEvent;
    }

    @Override
    @NonNull
    public final NoticeEvent getDestroyViewEvent() {
        return _destroyViewEvent;
    }

    @Override
    @NonNull
    public final NoticeEvent getDetachEvent() {
        return _detachEvent;
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
    public final Event<BundleEventArgs> getViewCreatedEvent() {
        return _viewCreatedEvent;
    }

    @Override
    @NonNull
    public final Event<BundleEventArgs> getViewStateRestoredEvent() {
        return _viewStateRestoredEvent;
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
    public final Observable<FragmentEvent> lifecycle() {
        return getLifecycleSubject().asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final FragmentEvent event) {
        Contracts.requireNonNull(event, "event == null");

        return RxLifecycle.bindUntilEvent(getLifecycleSubject(), event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(getLifecycleSubject());
    }

    @CallSuper
    @Override
    public void bindViews() {
        unbindViews();

        final val view = getView();
        if (view != null) {
            _unbinder = ButterKnife.bind(this, view);
        }
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

    @CallSuper
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final val eventArgs = new ActivityResultEventArgs(requestCode, resultCode, data);
        _activityResultEvent.rise(eventArgs);
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
    @Nullable
    @Override
    public View onCreateView(
        final LayoutInflater inflater,
        @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        final val view = super.onCreateView(inflater, container, savedInstanceState);

        onInjectMembers();

        onAcquireResources();

        _createViewEvent.rise(new BundleEventArgs(savedInstanceState));

        return view;
    }

    @CallSuper
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _viewCreatedEvent.rise(new BundleEventArgs(savedInstanceState));

        getLifecycleSubject().onNext(FragmentEvent.CREATE_VIEW);
    }

    @CallSuper
    @Override
    public void onViewStateRestored(@Nullable final Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        _viewStateRestoredEvent.rise(new BundleEventArgs(savedInstanceState));
    }

    @Override
    public void onResume() {
        super.onResume();

        _resumeEvent.rise();

        getLifecycleSubject().onNext(FragmentEvent.RESUME);
    }

    @CallSuper
    @Override
    public void onPause() {
        super.onPause();

        _pauseEvent.rise();

        getLifecycleSubject().onNext(FragmentEvent.PAUSE);
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();

        _destroyEvent.rise();

        getLifecycleSubject().onNext(FragmentEvent.DESTROY);

        onReleaseResources();

        onReleaseInjectedMembers();
    }

    @CallSuper
    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        _attachEvent.rise();

        getLifecycleSubject().onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        _detachEvent.rise();

        getLifecycleSubject().onNext(FragmentEvent.DETACH);
    }

    @CallSuper
    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _createEvent.rise(new BundleEventArgs(savedInstanceState));

        getLifecycleSubject().onNext(FragmentEvent.CREATE);
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();

        _startEvent.rise();

        getLifecycleSubject().onNext(FragmentEvent.START);
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        _saveInstanceStateEvent.rise(new BundleEventArgs(outState));
    }

    @CallSuper
    @Override
    public void onStop() {
        super.onStop();

        _stopEvent.rise();

        getLifecycleSubject().onNext(FragmentEvent.STOP);
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        _destroyViewEvent.rise();

        getLifecycleSubject().onNext(FragmentEvent.DESTROY_VIEW);
    }

    @CallSuper
    protected void onAcquireResources() {
        _acquireResourcesEvent.rise();
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

    @NonNull
    private final ManagedNoticeEvent _acquireResourcesEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedEvent<ActivityResultEventArgs> _activityResultEvent = Events.createEvent();

    @NonNull
    private final ManagedNoticeEvent _attachEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedEvent<BundleEventArgs> _createEvent = Events.createEvent();

    @NonNull
    private final ManagedEvent<BundleEventArgs> _createViewEvent = Events.createEvent();

    @NonNull
    private final ManagedNoticeEvent _destroyEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedNoticeEvent _destroyViewEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedNoticeEvent _detachEvent = Events.createNoticeEvent();

    @Getter(AccessLevel.PRIVATE)
    private final BehaviorSubject<FragmentEvent> _lifecycleSubject = BehaviorSubject.create();

    @NonNull
    private final ManagedNoticeEvent _pauseEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedEvent<PermissionResultEventArgs> _permissionResultEvent =
        Events.createEvent();

    @NonNull
    private final ManagedNoticeEvent _releaseResourcesEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedNoticeEvent _resumeEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedEvent<BundleEventArgs> _saveInstanceStateEvent = Events.createEvent();

    @NonNull
    private final ManagedNoticeEvent _startEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedNoticeEvent _stopEvent = Events.createNoticeEvent();

    @NonNull
    private final ManagedEvent<BundleEventArgs> _viewCreatedEvent = Events.createEvent();

    private final ManagedEvent<BundleEventArgs> _viewStateRestoredEvent = Events.createEvent();

    @Getter(AccessLevel.PROTECTED)
    @Nullable
    private Unbinder _unbinder;
}
