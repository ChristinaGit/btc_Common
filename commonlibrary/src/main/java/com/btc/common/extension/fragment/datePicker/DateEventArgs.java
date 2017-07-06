package com.btc.common.extension.fragment.datePicker;

import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.event.eventArgs.EventArgs;

@Accessors(prefix = "_")
public class DateEventArgs extends EventArgs {
    public DateEventArgs(final int year, final int month, final int dayOfMonth) {
        _year = year;
        _month = month;
        _dayOfMonth = dayOfMonth;
    }

    @Getter
    private final int _dayOfMonth;

    @Getter
    private final int _month;

    @Getter
    private final int _year;
}
