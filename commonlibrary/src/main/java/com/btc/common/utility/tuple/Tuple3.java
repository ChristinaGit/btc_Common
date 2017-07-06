package com.btc.common.utility.tuple;

import android.support.annotation.Nullable;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString(doNotUseGetters = true)
@Accessors(prefix = "_")
public final class Tuple3<T1, T2, T3> {
    /*package-private*/ Tuple3(
        @Nullable final T1 $1, @Nullable final T2 $2, @Nullable final T3 $3) {
        _1 = $1;
        _2 = $2;
        _3 = $3;
    }

    @Getter
    @Nullable
    private final T1 _1;

    @Getter
    @Nullable
    private final T2 _2;

    @Getter
    @Nullable
    private final T3 _3;
}
