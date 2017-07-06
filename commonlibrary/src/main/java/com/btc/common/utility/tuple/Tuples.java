package com.btc.common.utility.tuple;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.btc.common.contract.Contracts;

public final class Tuples {
    @NonNull
    public static <T1> Tuple1<T1> from(@Nullable final T1 $1) {
        return new Tuple1<>($1);
    }

    @NonNull
    public static <T1, T2> Tuple2<T1, T2> from(@Nullable final T1 $1, @Nullable final T2 $2) {
        return new Tuple2<>($1, $2);
    }

    @NonNull
    public static <T1, T2, T3> Tuple3<T1, T2, T3> from(
        @Nullable final T1 $1, @Nullable final T2 $2, @Nullable final T3 $3) {
        return new Tuple3<>($1, $2, $3);
    }

    @NonNull
    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> from(
        @Nullable final T1 $1,
        @Nullable final T2 $2,
        @Nullable final T3 $3,
        @Nullable final T4 $4) {
        return new Tuple4<>($1, $2, $3, $4);
    }

    private Tuples() {
        Contracts.unreachable();
    }
}
