package com.btc.common.extension.fragment.timePicker;

import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.event.eventArgs.EventArgs;

@Accessors(prefix = "_")
public class TimeEventArgs extends EventArgs {
    public TimeEventArgs(final int hourOfDay, final int minute) {
        _hourOfDay = hourOfDay;
        _minute = minute;
    }

    @Getter
    private final int _hourOfDay;

    @Getter
    private final int _minute;
}
