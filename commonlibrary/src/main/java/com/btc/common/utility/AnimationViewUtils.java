package com.btc.common.utility;

import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AnimationUtils;

import lombok.val;

import com.btc.common.contract.Contracts;

public final class AnimationViewUtils {
    public static void animateSetVisibility(@NonNull final View view, final int visibility) {
        Contracts.requireNonNull(view, "view == null");

        animateSetVisibility(view, visibility, android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void animateSetVisibility(
        @NonNull final View view,
        final int visibility,
        @AnimRes final int in,
        @AnimRes final int out) {
        Contracts.requireNonNull(view, "view == null");

        if (visibility != view.getVisibility()) {
            final boolean visible = visibility == View.VISIBLE;
            animateSetVisibility(view, visibility, visible ? in : out);
        }
    }

    public static void animateSetVisibility(
        @NonNull final View view, final int visibility, @AnimRes final int anim) {
        Contracts.requireNonNull(view, "view == null");

        if (visibility != view.getVisibility()) {
            final val animation = AnimationUtils.loadAnimation(view.getContext(), anim);
            view.setVisibility(visibility);
            view.startAnimation(animation);
        }
    }

    private AnimationViewUtils() {
        Contracts.unreachable();
    }
}
