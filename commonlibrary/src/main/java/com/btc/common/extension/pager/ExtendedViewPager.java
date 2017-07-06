package com.btc.common.extension.pager;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.btc.common.contract.Contracts;

@Accessors(prefix = "_")
public class ExtendedViewPager extends ViewPager {
    public ExtendedViewPager(@NonNull final Context context) {
        super(Contracts.requireNonNull(context, "context == null"));
    }

    public ExtendedViewPager(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(Contracts.requireNonNull(context, "context == null"), attrs);
    }

    @CallSuper
    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev) {
        return isSwipeable() && super.onInterceptTouchEvent(ev);
    }

    @CallSuper
    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        return isSwipeable() && super.onTouchEvent(ev);
    }

    @Getter
    @Setter
    private boolean _swipeable = true;
}
