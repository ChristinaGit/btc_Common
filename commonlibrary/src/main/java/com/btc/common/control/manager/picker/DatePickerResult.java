package com.btc.common.control.manager.picker;

import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString(doNotUseGetters = true)
@Accessors(prefix = "_")
public final class DatePickerResult {
    @NonNull
    public static DatePickerResult selected(final int year, final int month, final int dayOfMonth) {
        return new DatePickerResult(true, year, month, dayOfMonth);
    }

    @NonNull
    public static DatePickerResult canceled(
        final int initialYear, final int initialMonth, final int initialDayOfMonth) {
        return new DatePickerResult(false, initialYear, initialMonth, initialDayOfMonth);
    }

    protected DatePickerResult(
        final boolean selected, final int year, final int month, final int dayOfMonth) {
        _selected = selected;
        _year = year;
        _month = month;
        _dayOfMonth = dayOfMonth;
    }

    @Getter
    private final int _dayOfMonth;

    @Getter
    private final int _month;

    @Getter
    private final boolean _selected;

    @Getter
    private final int _year;
}
