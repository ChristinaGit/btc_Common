package com.btc.common.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.btc.common.contract.Contracts;

public final class DrawableUtils {
    public static void setTint(
        @NonNull final Context context,
        @NonNull final Drawable drawable,
        @AttrRes final int attributeId) {
        Contracts.requireNonNull(context, "context == null");
        Contracts.requireNonNull(drawable, "drawable == null");

        final Integer tint = ThemeUtils.tryResolveColor(context, attributeId);
        if (tint != null) {
            DrawableCompat.setTint(drawable, tint);
        }
    }

    private DrawableUtils() {
        Contracts.unreachable();
    }
}
