package com.btc.common.control.manager.picker;

import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString(doNotUseGetters = true)
@Accessors(prefix = "_")
public final class TimePickerResult {
    @NonNull
    public static TimePickerResult selected(final int hourOfDay, final int minute) {
        return new TimePickerResult(true, hourOfDay, minute);
    }

    @NonNull
    public static TimePickerResult canceled(final int initialHourOfDay, final int initialMinute) {
        return new TimePickerResult(false, initialHourOfDay, initialMinute);
    }

    protected TimePickerResult(final boolean selected, final int hourOfDay, final int minute) {
        _selected = selected;
        _hourOfDay = hourOfDay;
        _minute = minute;
    }

    @Getter
    private final int _hourOfDay;

    @Getter
    private final int _minute;

    @Getter
    private final boolean _selected;
}
