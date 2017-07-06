package com.btc.common.extension.view.recyclerView.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.R;
import com.btc.common.contract.Contracts;

@Accessors(prefix = "_")
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    public static void setDecorationMode(
        @NonNull final View view, @NonNull final DecorationMode decorationMode) {
        Contracts.requireNonNull(view, "view == null");
        Contracts.requireNonNull(decorationMode, "decorationMode == null");

        view.setTag(R.id.tag_item_divider_decoration_mode, decorationMode);
    }

    @NonNull
    public static DecorationMode getDecorationMode(@NonNull final View view) {
        Contracts.requireNonNull(view, "view == null");

        final val tag = view.getTag(R.id.tag_item_divider_decoration_mode);

        return tag instanceof DecorationMode ? (DecorationMode) tag : DecorationMode.ALL;
    }

    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;

    public static final int VERTICAL = OrientationHelper.VERTICAL;

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public DividerItemDecoration(@NonNull final Context context, final int orientation) {
        Contracts.requireNonNull(context, "context == null");

        TypedArray style = null;
        try {
            style = context.obtainStyledAttributes(ATTRS);
            _divider = style.getDrawable(0);
        } finally {
            if (style != null) {
                style.recycle();
            }
        }
        setOrientation(orientation);
    }

    @Override
    public void onDraw(final Canvas c, final RecyclerView parent, final RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        if (getOrientation() == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    @Override
    public void getItemOffsets(
        final Rect outRect,
        final View view,
        final RecyclerView parent,
        final RecyclerView.State state) {
        final val divider = getDivider();
        if (divider != null) {
            final val decorationMode = getDecorationMode(view);

            if (decorationMode != DecorationMode.NONE) {
                if (getOrientation() == VERTICAL) {
                    outRect.set(0, 0, 0, divider.getIntrinsicHeight());
                } else {
                    outRect.set(0, 0, divider.getIntrinsicWidth(), 0);
                }
            }
        }
    }

    @NonNull
    private final Rect _bounds = new Rect();

    @Getter(AccessLevel.PROTECTED)
    @Nullable
    private final Drawable _divider;

    @Getter
    @Setter
    private int _orientation;

    private void drawHorizontal(@NonNull final Canvas canvas, @NonNull final RecyclerView parent) {
        Contracts.requireNonNull(canvas, "canvas == null");
        Contracts.requireNonNull(parent, "parent == null");

        final val divider = getDivider();

        if (divider != null) {
            canvas.save();
            final int top;
            final int bottom;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                parent.getClipToPadding()) {
                top = parent.getPaddingTop();
                bottom = parent.getHeight() - parent.getPaddingBottom();
                canvas.clipRect(
                    parent.getPaddingLeft(),
                    top,
                    parent.getWidth() - parent.getPaddingRight(),
                    bottom);
            } else {
                top = 0;
                bottom = parent.getHeight();
            }

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final val view = parent.getChildAt(i);
                final val decorationMode = getDecorationMode(view);

                if (decorationMode != DecorationMode.NONE) {
                    parent.getLayoutManager().getDecoratedBoundsWithMargins(view, _bounds);
                    final int right = _bounds.right + Math.round(ViewCompat.getTranslationX(view));
                    final int left = right - divider.getIntrinsicWidth();
                    divider.setBounds(left, top, right, bottom);
                    divider.draw(canvas);
                }
            }
            canvas.restore();
        }
    }

    private void drawVertical(final Canvas canvas, final RecyclerView parent) {
        Contracts.requireNonNull(canvas, "canvas == null");
        Contracts.requireNonNull(parent, "parent == null");

        final val divider = getDivider();

        if (divider != null) {
            canvas.save();
            final int left;
            final int right;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                parent.getClipToPadding()) {
                left = parent.getPaddingLeft();
                right = parent.getWidth() - parent.getPaddingRight();
                canvas.clipRect(
                    left,
                    parent.getPaddingTop(),
                    right,
                    parent.getHeight() - parent.getPaddingBottom());
            } else {
                left = 0;
                right = parent.getWidth();
            }

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final val view = parent.getChildAt(i);
                final val decorationMode = getDecorationMode(view);

                if (decorationMode != DecorationMode.NONE) {
                    parent.getDecoratedBoundsWithMargins(view, _bounds);
                    final int bottom =
                        _bounds.bottom + Math.round(ViewCompat.getTranslationY(view));
                    final int top = bottom - divider.getIntrinsicHeight();
                    divider.setBounds(left, top, right, bottom);
                    divider.draw(canvas);
                }
            }
            canvas.restore();
        }
    }

    public enum DecorationMode {
        ALL, NONE
    }
}
