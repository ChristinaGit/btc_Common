package com.btc.common.mvp.presenter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.contract.Contracts;
import com.btc.common.event.notice.NoticeEventHandler;
import com.btc.common.mvp.screen.Screen;

@Accessors(prefix = "_")
public abstract class BasePresenter<TScreen extends Screen> implements Presenter<TScreen> {
    @Override
    public void bindScreen(@NonNull final TScreen screen) {
        Contracts.requireNonNull(screen, "screen == null");

        if (_screen != null) {
            throw new IllegalStateException("Presenter is already bound.");
        }

        onBindScreen(screen);

        _screen = screen;
    }

    @Override
    public final void unbindScreen() {
        if (_screen == null) {
            throw new IllegalStateException("Presenter is already unbound.");
        }

        onUnbindScreen(_screen);
    }

    @CallSuper
    protected void onBindScreen(@NonNull final TScreen screen) {
        Contracts.requireNonNull(screen, "screen == null");

        screen.getScreenCreateEvent().addHandler(_screenCreateHandler);
        screen.getScreenAppearEvent().addHandler(_screenAppearHandler);
        screen.getScreenDisappearEvent().addHandler(_screenDisappearHandler);
        screen.getScreenDestroyEvent().addHandler(_screenDestroyHandler);
    }

    @CallSuper
    protected void onScreenAppear(@NonNull final TScreen screen) {
        Contracts.requireNonNull(screen, "screen == null");
    }

    @CallSuper
    protected void onScreenCreate(@NonNull final TScreen screen) {
        Contracts.requireNonNull(screen, "screen == null");
    }

    @CallSuper
    protected void onScreenDestroy(@NonNull final TScreen screen) {
        Contracts.requireNonNull(screen, "screen == null");
    }

    @CallSuper
    protected void onScreenDisappear(@NonNull final TScreen screen) {
        Contracts.requireNonNull(screen, "screen == null");
    }

    @CallSuper
    protected void onUnbindScreen(@NonNull final TScreen screen) {
        Contracts.requireNonNull(screen, "screen == null");

        screen.getScreenCreateEvent().removeHandler(_screenCreateHandler);
        screen.getScreenAppearEvent().removeHandler(_screenAppearHandler);
        screen.getScreenDisappearEvent().removeHandler(_screenDisappearHandler);
        screen.getScreenDestroyEvent().removeHandler(_screenDestroyHandler);
    }

    @Getter(AccessLevel.PROTECTED)
    @Nullable
    private TScreen _screen;

    @NonNull
    private final NoticeEventHandler _screenAppearHandler = new NoticeEventHandler() {
        @Override
        public void onEvent() {
            final val screen = getScreen();

            if (screen != null) {
                onScreenAppear(screen);
            } else {
                throw new IllegalStateException("Screen is null in internal event.");
            }
        }
    };

    @NonNull
    private final NoticeEventHandler _screenCreateHandler = new NoticeEventHandler() {
        @Override
        public void onEvent() {
            final val screen = getScreen();

            if (screen != null) {
                onScreenCreate(screen);
            } else {
                throw new IllegalStateException("Screen is null in internal event.");
            }
        }
    };

    @NonNull
    private final NoticeEventHandler _screenDestroyHandler = new NoticeEventHandler() {
        @Override
        public void onEvent() {
            final val screen = getScreen();

            if (screen != null) {
                onScreenDestroy(screen);
            } else {
                throw new IllegalStateException("Screen is null in internal event.");
            }
        }
    };

    @NonNull
    private final NoticeEventHandler _screenDisappearHandler = new NoticeEventHandler() {
        @Override
        public void onEvent() {
            final val screen = getScreen();

            if (screen != null) {
                onScreenDisappear(screen);
            } else {
                throw new IllegalStateException("Screen is null in internal event.");
            }
        }
    };
}
