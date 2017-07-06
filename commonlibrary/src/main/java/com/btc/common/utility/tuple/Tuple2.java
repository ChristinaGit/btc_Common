package com.btc.common.utility.tuple;

import android.support.annotation.Nullable;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString(doNotUseGetters = true)
@Accessors(prefix = "_")
public final class Tuple2<T1, T2> {
    /*package-private*/ Tuple2(@Nullable final T1 $1, @Nullable final T2 $2) {
        _1 = $1;
        _2 = $2;
    }

    @Getter
    @Nullable
    private final T1 _1;

    @Getter
    @Nullable
    private final T2 _2;
}
