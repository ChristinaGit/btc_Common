package com.btc.common.extension.delegate.loading;

import android.support.annotation.NonNull;
import android.view.View;

import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.contract.Contracts;

@Accessors(prefix = "_")
public class SimpleVisibilityHandler implements VisibilityHandler {
    public static final int DEFAULT_HIDE_VISIBILITY = View.GONE;

    public SimpleVisibilityHandler() {
        this(DEFAULT_HIDE_VISIBILITY);
    }

    public SimpleVisibilityHandler(final int hideVisibility) {
        _hideVisibility = hideVisibility;
    }

    @Override
    public void changeVisibility(@NonNull final View view, final boolean visible) {
        Contracts.requireNonNull(view, "view == null");

        view.setVisibility(visible ? View.VISIBLE : getHideVisibility());
    }

    @Getter
    private final int _hideVisibility;
}
