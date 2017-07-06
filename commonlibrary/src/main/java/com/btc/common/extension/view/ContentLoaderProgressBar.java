package com.btc.common.extension.view;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.contract.Contracts;

@Accessors(prefix = "_")
public class ContentLoaderProgressBar extends ProgressBar {
    public static final long MINIMUM_SHOW_TIME = 500L;

    public static final long MINIMUM_DELAY = 500L;

    private static final long NO_TIME = -1L;

    public ContentLoaderProgressBar(@NonNull final Context context) {
        super(Contracts.requireNonNull(context, "context == null"));

        initialize(null, 0, 0);
    }

    public ContentLoaderProgressBar(
        @NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(Contracts.requireNonNull(context, "context == null"), attrs);

        initialize(attrs, 0, 0);
    }

    public ContentLoaderProgressBar(
        @NonNull final Context context,
        @Nullable final AttributeSet attrs,
        final int defStyleAttr) {
        super(Contracts.requireNonNull(context, "context == null"), attrs, defStyleAttr);

        initialize(attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ContentLoaderProgressBar(
        @NonNull final Context context,
        @Nullable final AttributeSet attrs,
        final int defStyleAttr,
        final int defStyleRes) {
        super(Contracts.requireNonNull(context, "context == null"),
              attrs,
              defStyleAttr,
              defStyleRes);

        initialize(attrs, defStyleAttr, defStyleRes);
    }

    @CallSuper
    public void hide() {
        if (_shown) {
            _shown = false;
            if (_attachedToWindow) {
                removeCallbacks(_showCallback);
            }
            final long diff = SystemClock.uptimeMillis() - _startTime;
            if (_startTime == NO_TIME || diff >= MINIMUM_SHOW_TIME) {
                setVisibility(GONE);
                _startTime = NO_TIME;
            } else {
                postDelayed(_hideCallback, MINIMUM_SHOW_TIME - diff);
            }
        }
    }

    @CallSuper
    public void reset(final boolean shown) {
        removeCallbacks(_hideCallback);
        removeCallbacks(_showCallback);
        _shown = shown;
        _startTime = NO_TIME;
        setVisibility(shown ? VISIBLE : GONE);
    }

    @CallSuper
    public void show() {
        if (!_shown) {
            _shown = true;
            if (_attachedToWindow) {
                removeCallbacks(_hideCallback);
                if (_startTime == NO_TIME) {
                    postDelayed(_showCallback, MINIMUM_DELAY);
                }
            }
        }
    }

    @CallSuper
    protected void initialize(
        @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        _shown = getVisibility() == View.VISIBLE;
    }

    @CallSuper
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        _attachedToWindow = true;
        if (_shown && (getVisibility() != View.VISIBLE)) {
            postDelayed(_showCallback, MINIMUM_DELAY);
        }
    }

    @CallSuper
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        _attachedToWindow = false;
        removeCallbacks(_hideCallback);
        removeCallbacks(_showCallback);
        if (!_shown && _startTime != NO_TIME) {
            setVisibility(View.GONE);
        }
        _startTime = NO_TIME;
    }

    private boolean _attachedToWindow = false;

    @Getter
    private boolean _shown;

    private long _startTime = NO_TIME;

    @NonNull
    private final Runnable _hideCallback = new Runnable() {
        @Override
        public void run() {
            setVisibility(View.GONE);
            _startTime = -1L;
        }
    };

    @NonNull
    private final Runnable _showCallback = new Runnable() {
        @Override
        public void run() {
            _startTime = SystemClock.uptimeMillis();
            setVisibility(View.VISIBLE);
        }
    };
}