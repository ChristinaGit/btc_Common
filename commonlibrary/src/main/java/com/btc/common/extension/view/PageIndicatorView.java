package com.btc.common.extension.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.View;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.R;
import com.btc.common.contract.Contracts;

@Accessors(prefix = "_")
public class PageIndicatorView extends View {
    public PageIndicatorView(@NonNull final Context context) {
        super(Contracts.requireNonNull(context, "context == null"));

        initialize(context, null, 0, 0);
    }

    public PageIndicatorView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(Contracts.requireNonNull(context, "context == null"), attrs);

        initialize(context, attrs, 0, 0);
    }

    public PageIndicatorView(
        @NonNull final Context context,
        @Nullable final AttributeSet attrs,
        final int defStyleAttr) {
        super(Contracts.requireNonNull(context, "context == null"), attrs, defStyleAttr);

        initialize(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PageIndicatorView(
        @NonNull final Context context,
        @Nullable final AttributeSet attrs,
        final int defStyleAttr,
        final int defStyleRes) {
        super(Contracts.requireNonNull(context, "context == null"),
              attrs,
              defStyleAttr,
              defStyleRes);

        initialize(context, attrs, defStyleAttr, defStyleRes);
    }

    public final void setIndicatorCount(final int indicatorCount) {
        if (_indicatorCount != indicatorCount) {
            _indicatorCount = indicatorCount;

            requestLayout();
            invalidate();
        }
    }

    public final void setIndicatorId(final int indicatorId) {
        if (_indicatorId != indicatorId) {
            _indicatorId = indicatorId;

            setIndicatorDrawable(AppCompatResources.getDrawable(getContext(), _indicatorId));
        }
    }

    public final void setIndicatorSelectedTint(@ColorInt final int indicatorSelecedTint) {
        if (_indicatorSelectedTint != indicatorSelecedTint) {
            _indicatorSelectedTint = indicatorSelecedTint;

            invalidate();
        }
    }

    public final void setIndicatorSpacing(final int indicatorSpacing) {
        if (_indicatorSpacing != indicatorSpacing) {
            _indicatorSpacing = indicatorSpacing;

            requestLayout();
            invalidate();
        }
    }

    public final void setIndicatorTint(@ColorInt final int indicatorTint) {
        if (_indicatorTint != indicatorTint) {
            _indicatorTint = indicatorTint;

            requestLayout();
            invalidate();
        }
    }

    public final void setSelectedItem(final int selectedItem) {
        if (_selectedItem != selectedItem) {
            _selectedItem = selectedItem;

            requestLayout();
            invalidate();
        }
    }

    @CallSuper
    @Override
    public void postInvalidate() {
        if (!_noInvalidate) {
            super.postInvalidate();
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        final val indicatorDrawable = getIndicatorDrawable();
        if (indicatorDrawable == null) {
            return;
        }

        final boolean rtl = getLayoutDirection() == LAYOUT_DIRECTION_RTL;

        final int height = getHeight();
        final int width = getWidth();

        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        final int paddingLeft = rtl ? getPaddingEnd() : getPaddingStart();
        final int paddingRight = rtl ? getPaddingStart() : getPaddingEnd();

        final int availableWidth = width - paddingLeft - paddingRight;
        final int availableHeight = height - paddingTop - paddingBottom;

        final val indicatorBounds = getIndicatorBounds();

        final val stateSelected = getStateSelected();
        final val stateDefault = getStateDefault();

        final int indicatorSpacing = getIndicatorSpacing();

        final int indicatorTint = getIndicatorTint();
        final int indicatorSelectedTint = getIndicatorSelectedTint();

        final int indicatorCount = getIndicatorCount();
        final int selectedItem = getInternalSelectedItem();

        int left = paddingLeft;
        int right = 0;
        final int top = paddingTop;
        final int bottom = top + availableHeight;
        for (int i = 0; i < indicatorCount; i++) {
            final boolean isSelectedItem = i == selectedItem;
            final val state = isSelectedItem ? stateSelected : stateDefault;
            final int tint = isSelectedItem ? indicatorSelectedTint : indicatorTint;
            indicatorDrawable.setState(state);

            final int indicatorWidth = indicatorDrawable.getIntrinsicWidth();

            if (right == 0) {
                right = indicatorWidth;
            }

            indicatorBounds.set(left, top, right, bottom);
            indicatorDrawable.setBounds(indicatorBounds);

            indicatorDrawable.setColorFilter(tint, PorterDuff.Mode.SRC_ATOP);
            indicatorDrawable.draw(canvas);

            left += indicatorWidth;
            right += indicatorWidth;

            if (i != indicatorCount - 1) {
                left += indicatorSpacing;
                right += indicatorSpacing;
            }
        }
    }

    @CallSuper
    @Override
    protected Parcelable onSaveInstanceState() {
        final val savedState = new SavedState(super.onSaveInstanceState());
        savedState.setSelectedItem(getSelectedItem());
        return savedState;
    }

    @CallSuper
    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        final val savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setSelectedItem(savedState.getSelectedItem());
        requestLayout();
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

        int height = 0;
        int width = 0;
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);

        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        final int paddingStart = getPaddingStart();
        final int paddingEnd = getPaddingEnd();

        final val indicatorDrawable = getIndicatorDrawable();
        final int indicatorSpacing = getIndicatorSpacing();

        final val stateSelected = getStateSelected();
        final val stateDefault = getStateDefault();

        final int indicatorCount = getIndicatorCount();
        final int selectedItem = getInternalSelectedItem();

        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            height += paddingTop + paddingBottom;

            if (indicatorDrawable != null) {
                for (int i = 0; i < indicatorCount; i++) {
                    indicatorDrawable.setState(i == selectedItem ? stateSelected : stateDefault);

                    final int indicatorHeight = indicatorDrawable.getIntrinsicHeight();

                    height = Math.max(height, indicatorHeight);
                }
            }
        } else {
            height = measuredHeight;
        }

        if (widthSpecMode == MeasureSpec.AT_MOST || widthSpecMode == MeasureSpec.UNSPECIFIED) {
            width += paddingStart + paddingEnd;

            if (indicatorDrawable != null) {
                for (int i = 0; i < indicatorCount; i++) {
                    indicatorDrawable.setState(i == selectedItem ? stateSelected : stateDefault);

                    final int indicatorWidth = indicatorDrawable.getIntrinsicWidth();

                    if (i != indicatorCount - 1) {
                        width += indicatorSpacing;
                    }

                    width += indicatorWidth;
                }
            }
        } else {
            width = measuredWidth;
        }

        setMeasuredDimension(width, height);
    }

    public void setIndicatorDrawable(@Nullable final Drawable indicatorDrawable) {
        _indicatorDrawable = indicatorDrawable;

        postInvalidate();
    }

    protected int getInternalSelectedItem() {
        final boolean rtl = getLayoutDirection() == LAYOUT_DIRECTION_RTL;
        final int indicatorCount = getIndicatorCount();
        final int selectedItem = rtl ? indicatorCount - 1 - getSelectedItem() : getSelectedItem();
        return Math.min(selectedItem, indicatorCount - 1);
    }

    @Getter(AccessLevel.PRIVATE)
    @NonNull
    private final Rect _indicatorBounds = new Rect();

    @Getter(AccessLevel.PROTECTED)
    private final int[] _stateDefault = new int[0];

    @Getter(AccessLevel.PROTECTED)
    private final int[] _stateSelected = new int[]{android.R.attr.state_selected};

    @Getter
    private int _indicatorCount;

    @Getter
    @Nullable
    private Drawable _indicatorDrawable;

    @Getter
    @DrawableRes
    private int _indicatorId;

    @ColorInt
    @Getter
    private int _indicatorSelectedTint;

    @Getter
    private int _indicatorSpacing;

    @ColorInt
    @Getter
    private int _indicatorTint;

    private boolean _noInvalidate = false;

    @Getter
    private int _selectedItem;

    private void initialize(
        @NonNull final Context context,
        @Nullable final AttributeSet attrs,
        final int defStyleAttr,
        final int defStyleRes) {
        Contracts.requireNonNull(context, "context == null");

        _noInvalidate = true;

        int indicatorId = R.drawable.ic_page_indicator;
        int indicatorCount = 0;
        int indicatorSpacing = 0;
        int indicatorTint = 0;
        int indicatorSelectedTint = 0;

        TypedArray style = null;
        if (attrs != null) {
            try {
                style = context.obtainStyledAttributes(attrs,
                                                       R.styleable.PageIndicatorView,
                                                       defStyleAttr,
                                                       defStyleRes);
                indicatorId =
                    style.getResourceId(R.styleable.PageIndicatorView_indicator, indicatorId);
                indicatorCount =
                    style.getInteger(R.styleable.PageIndicatorView_indicatorCount, indicatorCount);
                indicatorSpacing =
                    style.getDimensionPixelSize(R.styleable.PageIndicatorView_indicatorSpacing,
                                                indicatorSpacing);
                indicatorTint =
                    style.getColor(R.styleable.PageIndicatorView_indicatorTint, indicatorTint);
                indicatorSelectedTint =
                    style.getColor(R.styleable.PageIndicatorView_indicatorSelectedTint,
                                   indicatorSelectedTint);
            } finally {
                if (style != null) {
                    style.recycle();
                }
            }
        }

        setIndicatorId(indicatorId);
        setIndicatorCount(indicatorCount);
        setIndicatorSpacing(indicatorSpacing);
        setIndicatorTint(indicatorTint);
        setIndicatorSelectedTint(indicatorSelectedTint);

        _noInvalidate = false;
    }

    @Accessors(prefix = "_")
    private static class SavedState extends BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR =
            new Parcelable.Creator<SavedState>() {
                @Override
                public SavedState createFromParcel(final Parcel in) {
                    return new SavedState(in);
                }

                @Override
                public SavedState[] newArray(final int size) {
                    return new SavedState[size];
                }
            };

        public SavedState(@NonNull final Parcelable parcelable) {
            super(Contracts.requireNonNull(parcelable, "parcelable == null"));
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);

            dest.writeInt(_selectedItem);
        }

        @Getter
        @Setter
        private int _selectedItem;

        private SavedState(final Parcel in) {
            super(in);

            _selectedItem = in.readInt();
        }
    }
}
