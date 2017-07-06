package com.btc.common.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import lombok.val;

import com.btc.common.contract.Contracts;

public final class ViewUtils {
    public static float toPx(@NonNull final Context context, final float dp) {
        Contracts.requireNonNull(context, "context == null");

        final val resources = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                         dp,
                                         resources.getDisplayMetrics());
    }

    public static int getScreenWidth(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return getDisplayMetrics(context).widthPixels;
    }

    public static int getScreenHeight(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return getDisplayMetrics(context).heightPixels;
    }

    @NonNull
    public static DisplayMetrics getDisplayMetrics(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        final val displayMetrics = new DisplayMetrics();
        final val windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }

        return displayMetrics;
    }

    public static void draw(@NonNull final View view, @NonNull final Bitmap bitmap) {
        Contracts.requireNonNull(view, "view == null");
        Contracts.requireNonNull(bitmap, "bitmap == null");

        draw(view, new Canvas(bitmap));
    }

    public static void draw(@NonNull final View view, @NonNull final Canvas canvas) {
        Contracts.requireNonNull(view, "view == null");
        Contracts.requireNonNull(canvas, "canvas == null");

        view.measure(canvas.getWidth(), canvas.getHeight());
        view.layout(0, 0, canvas.getWidth(), canvas.getHeight());
        view.invalidate();
        view.draw(canvas);
    }

    private ViewUtils() {
        Contracts.unreachable();
    }
}