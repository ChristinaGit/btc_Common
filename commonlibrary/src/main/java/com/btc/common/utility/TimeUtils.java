package com.btc.common.utility;

import android.text.format.DateUtils;

import com.btc.common.contract.Contracts;

public final class TimeUtils {
    public static long getAlignedDay(final long time) {
        return NumberUtils.getAlignedValue(time, DateUtils.DAY_IN_MILLIS);
    }

    public static long getTime(final long date) {
        return date % DateUtils.DAY_IN_MILLIS;
    }

    public static long getTime(final int hourOfDay, final int minute) {
        return hourOfDay * DateUtils.HOUR_IN_MILLIS + minute * DateUtils.MINUTE_IN_MILLIS;
    }

    private TimeUtils() {
        Contracts.unreachable();
    }
}
