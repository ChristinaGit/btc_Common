package com.btc.common.utility;

import com.btc.common.contract.Contracts;

public final class FlagUtils {
    public static int toggleFlag(final int value, final int flag) {
        return setFlagEnabled(value, flag, !hasFlag(value, flag));
    }

    public static int setFlagEnabled(final int value, final int flag, final boolean enabled) {
        return enabled ? addFlag(value, flag) : removeFlag(value, flag);
    }

    public static int addFlag(final int value, final int flag) {
        return value | flag;
    }

    public static int removeFlag(final int value, final int flag) {
        return value & (~flag);
    }

    public static boolean hasFlag(final int value, final int flag) {
        return (value & flag) != 0;
    }

    public static boolean isPureFlag(final int flag) {
        return flag > 0 && (flag & (flag - 1)) == 0;
    }

    private FlagUtils() {
        Contracts.unreachable();
    }
}
