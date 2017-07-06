package com.btc.common.utility;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.btc.common.contract.Contracts;

public final class TextViewUtils {
    public static void setStrikeThroughEnabled(
        @NonNull final TextView textView, final boolean enabled) {
        Contracts.requireNonNull(textView, "textView == null");

        textView.setPaintFlags(FlagUtils.setFlagEnabled(textView.getPaintFlags(),
                                                        Paint.STRIKE_THRU_TEXT_FLAG,
                                                        enabled));
    }

    private TextViewUtils() {
        Contracts.unreachable();
    }
}
