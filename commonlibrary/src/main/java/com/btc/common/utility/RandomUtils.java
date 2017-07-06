package com.btc.common.utility;

import android.support.annotation.NonNull;

import com.btc.common.contract.Contracts;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public final class RandomUtils {
    public static int nextInt(@NonNull final Random random, final int min, final int max) {
        Contracts.requireNonNull(random, "random == null");

        return random.nextInt(max - min) + min;
    }

    @Nullable
    public static <T> T randomItem(@NotNull final Random random, @Nullable final T[] array) {
        Contracts.requireNonNull(random, "random == null");

        final T result;

        if (array == null || array.length == 0) {
            result = null;
        } else if (array.length == 1) {
            result = array[0];
        } else {
            result = array[nextInt(random, 0, array.length - 1)];
        }

        return result;
    }

    private RandomUtils() {
        Contracts.unreachable();
    }
}
