package com.btc.common.utility;

import android.support.annotation.NonNull;
import android.view.View;

import lombok.val;

import com.btc.common.contract.Contracts;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public final class ImeUtils {
    public static void showIme(@NonNull final View view) {
        Contracts.requireNonNull(view, "view == null");

        showIme(view, 0);
    }

    public static void showIme(@NonNull final View view, final int flags) {
        Contracts.requireNonNull(view, "view == null");

        final val imm = ContextUtils.getInputMethodManager(view.getContext());
        if (imm != null) {
            imm.showSoftInput(view, flags);
        }
    }

    public static void hideIme(@NonNull final View view) {
        Contracts.requireNonNull(view, "view == null");

        hideIme(view, 0);
    }

    public static void hideIme(@NonNull final View view, final int flags) {
        Contracts.requireNonNull(view, "view == null");

        final val imm = ContextUtils.getInputMethodManager(view.getContext());
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), flags);
        }
    }

    public static void hideImeDelayed(@NonNull final View view, final long delay) {
        Contracts.requireNonNull(view, "view == null");

        hideImeDelayed(view, 0, delay);
    }

    public static void hideImeDelayed(@NonNull final View view, final int flags, final long delay) {
        Contracts.requireNonNull(view, "view == null");

        final Reference<View> viewRef = new WeakReference<>(view);
        HandlerUtils.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final val viewHost = viewRef.get();
                if (viewHost != null) {
                    hideIme(viewHost, flags);
                }
            }
        }, delay);
    }

    public static void showImeDelayed(@NonNull final View view, final long delay) {
        Contracts.requireNonNull(view, "view == null");

        showImeDelayed(view, 0, delay);
    }

    public static void showImeDelayed(@NonNull final View view, final int flags, final long delay) {
        Contracts.requireNonNull(view, "view == null");

        final Reference<View> viewRef = new WeakReference<>(view);
        HandlerUtils.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final val viewHost = viewRef.get();
                if (viewHost != null) {
                    showIme(viewHost, flags);
                }
            }
        }, delay);
    }

    private ImeUtils() {
        Contracts.unreachable();
    }
}
