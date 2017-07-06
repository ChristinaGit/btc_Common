package com.btc.common.utility;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import lombok.val;

import com.btc.common.R;
import com.btc.common.contract.Contracts;

public final class SwipeRefreshUtils {
    public static void setupPrimaryColors(@NonNull final SwipeRefreshLayout swipeRefreshLayout) {
        Contracts.requireNonNull(swipeRefreshLayout, "swipeRefreshLayout == null");

        final val context = swipeRefreshLayout.getContext();

        final val colorAccent = ThemeUtils.tryResolveColor(context, R.attr.colorAccent);
        final val colorPrimary = ThemeUtils.tryResolveColor(context, R.attr.colorPrimary);

        if (colorAccent != null && colorPrimary != null) {
            swipeRefreshLayout.setColorSchemeColors(colorAccent, colorPrimary);
        }
    }

    private SwipeRefreshUtils() {
        Contracts.unreachable();
    }
}
