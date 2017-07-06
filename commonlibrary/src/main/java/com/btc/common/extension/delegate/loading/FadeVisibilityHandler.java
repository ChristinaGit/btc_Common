package com.btc.common.extension.delegate.loading;

import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.view.View;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.btc.common.utility.AnimationViewUtils;

@Accessors(prefix = "_")
public class FadeVisibilityHandler extends SimpleVisibilityHandler {
    public static final int NO_ANIMATION = 0;

    public FadeVisibilityHandler(
        final int hideVisibility, final int fadeInAnimationId, final int fadeOutAnimationId) {
        super(hideVisibility);
        _fadeInAnimationId = fadeInAnimationId;
        _fadeOutAnimationId = fadeOutAnimationId;
    }

    public FadeVisibilityHandler(final int fadeInAnimationId, final int fadeOutAnimationId) {
        _fadeInAnimationId = fadeInAnimationId;
        _fadeOutAnimationId = fadeOutAnimationId;
    }

    @Override
    public void changeVisibility(@NonNull final View view, final boolean visible) {
        if (visible) {
            final int fadeInAnimationId = getFadeInAnimationId();
            if (fadeInAnimationId != NO_ANIMATION) {
                AnimationViewUtils.animateSetVisibility(view, View.VISIBLE, fadeInAnimationId);
            } else {
                super.changeVisibility(view, visible);
            }
        } else {
            final int fadeOutAnimationId = getFadeOutAnimationId();
            if (fadeOutAnimationId != NO_ANIMATION) {
                AnimationViewUtils.animateSetVisibility(view,
                                                        getHideVisibility(),
                                                        fadeOutAnimationId);
            } else {
                super.changeVisibility(view, visible);
            }
        }
    }

    @Getter
    @Setter
    @AnimRes
    private int _fadeInAnimationId;

    @Getter
    @Setter
    @AnimRes
    private int _fadeOutAnimationId;
}
