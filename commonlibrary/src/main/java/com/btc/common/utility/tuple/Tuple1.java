package com.btc.common.utility.tuple;

import android.support.annotation.Nullable;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString(doNotUseGetters = true)
@Accessors(prefix = "_")
public final class Tuple1<T1> {
    /*package-private*/ Tuple1(@Nullable final T1 $1) {
        _1 = $1;
    }

    @Getter
    @Nullable
    private final T1 _1;
}
