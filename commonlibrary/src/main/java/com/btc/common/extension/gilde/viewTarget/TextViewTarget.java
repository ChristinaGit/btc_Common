package com.btc.common.extension.gilde.viewTarget;

import android.support.annotation.NonNull;
import android.widget.TextView;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.contract.Contracts;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

@Accessors(prefix = "_")
public class TextViewTarget extends ViewTarget<TextView, GlideDrawable> {
    public TextViewTarget(
        @NonNull final TextView view, @NonNull final DrawablePosition drawablePosition) {
        super(Contracts.requireNonNull(view, "view == null"));
        Contracts.requireNonNull(drawablePosition, "drawablePosition == null");

        _drawablePosition = drawablePosition;
    }

    @Override
    public void onResourceReady(
        final GlideDrawable resource, final GlideAnimation<? super GlideDrawable> glideAnimation) {

        final val view = getView();
        switch (getDrawablePosition()) {
            case START:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(resource, null, null, null);
                break;
            case TOP:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, resource, null, null);
                break;
            case END:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, resource, null);
                break;
            case BOTTOM:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resource);
                break;
        }
    }

    @Getter
    @NonNull
    private final DrawablePosition _drawablePosition;

    public enum DrawablePosition {
        START,
        TOP,
        END,
        BOTTOM
    }
}
