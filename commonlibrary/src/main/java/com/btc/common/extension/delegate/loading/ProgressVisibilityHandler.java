package com.btc.common.extension.delegate.loading;

import android.support.annotation.NonNull;
import android.view.View;

import lombok.val;

import com.btc.common.contract.Contracts;
import com.btc.common.extension.view.ContentLoaderProgressBar;

public class ProgressVisibilityHandler extends SimpleVisibilityHandler {
    public ProgressVisibilityHandler() {
    }

    public ProgressVisibilityHandler(final int hideVisibility) {
        super(hideVisibility);
    }

    @Override
    public void changeVisibility(@NonNull final View view, final boolean visible) {
        Contracts.requireNonNull(view, "view == null");

        if (view instanceof ContentLoaderProgressBar) {
            final val progressBar = (ContentLoaderProgressBar) view;
            if (visible) {
                progressBar.show();
            } else {
                progressBar.hide();
            }
        } else {
            super.changeVisibility(view, visible);
        }
    }
}
