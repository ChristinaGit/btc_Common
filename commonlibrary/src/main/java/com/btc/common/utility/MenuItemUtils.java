package com.btc.common.utility;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import lombok.val;

import com.btc.common.contract.Contracts;

public final class MenuItemUtils {
    public static void setCheckableItemIcon(
        @NonNull final MenuItem menuItem,
        @DrawableRes final int checkedId,
        @DrawableRes final int uncheckedId) {
        Contracts.requireNonNull(menuItem, "menuItem == null");

        menuItem.setIcon(menuItem.isChecked() ? checkedId : uncheckedId);
    }

    public static void setCheckableItemIcon(
        @NonNull final MenuItem menuItem, final Drawable checked, final Drawable unchecked) {
        Contracts.requireNonNull(menuItem, "menuItem == null");
        Contracts.requireNonNull(checked, "checked == null");
        Contracts.requireNonNull(unchecked, "unchecked == null");

        menuItem.setIcon(menuItem.isChecked() ? checked : unchecked);
    }

    public static void setIconAlphaPercent(@NonNull final MenuItem menuItem, final int alpha) {
        Contracts.requireNonNull(menuItem, "menuItem == null");

        setIconAlpha(menuItem, 255 * alpha / 100);
    }

    public static void setIconAlpha(@NonNull final MenuItem menuItem, final int alpha) {
        Contracts.requireNonNull(menuItem, "menuItem == null");

        final val icon = menuItem.getIcon();
        if (icon != null) {
            icon.setAlpha(alpha);
        }
    }

    private MenuItemUtils() {
        Contracts.unreachable();
    }
}
