package com.btc.common.extension.eventArgs;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import com.btc.common.event.eventArgs.EventArgs;

@ToString(doNotUseGetters = true)
@Accessors(prefix = "_")
public class IdEventArgs extends EventArgs {
    public IdEventArgs(final long id) {
        _id = id;
    }

    @Getter
    private final long _id;
}
