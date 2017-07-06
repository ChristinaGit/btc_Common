package com.btc.common.utility;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.TypedValue;

import lombok.val;

import com.btc.common.contract.Contracts;

public final class ThemeUtils {
    public static int resolveColor(
        @NonNull final Context context, @AttrRes final int attributeId) {
        Contracts.requireNonNull(context, "context == null");

        return resolveColor(context, attributeId, 0);
    }

    @Nullable
    public static ColorStateList resolveColorStateList(
        @NonNull final Context context, @AttrRes final int attributeId) {
        Contracts.requireNonNull(context, "context == null");

        final ColorStateList baseColor;

        final val value = new TypedValue();
        if (context.getTheme().resolveAttribute(attributeId, value, true)) {
            baseColor = AppCompatResources.getColorStateList(context, value.resourceId);
        } else {
            baseColor = null;
        }

        return baseColor;
    }

    public static int resolveColor(
        @NonNull final Context context, @AttrRes final int attributeId, final int defaultValue) {
        Contracts.requireNonNull(context, "context == null");

        final val theme = context.getTheme();
        final val resources = context.getResources();

        final int result;

        final val typedValue = new TypedValue();
        if (theme.resolveAttribute(attributeId, typedValue, true)) {
            result = ResourcesCompat.getColor(resources, typedValue.resourceId, theme);
        } else {
            result = defaultValue;
        }

        return result;
    }

    @Nullable
    public static Integer tryResolveColor(
        @NonNull final Context context, @AttrRes final int attributeId) {
        Contracts.requireNonNull(context, "context == null");

        final val theme = context.getTheme();
        final val resources = context.getResources();

        final Integer result;

        final val typedValue = new TypedValue();
        if (theme.resolveAttribute(attributeId, typedValue, true)) {
            result = ResourcesCompat.getColor(resources, typedValue.resourceId, theme);
        } else {
            result = null;
        }

        return result;
    }

    private ThemeUtils() {
        Contracts.unreachable();
    }
}
