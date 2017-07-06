package com.btc.common.extension.eventArgs;

import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.event.eventArgs.EventArgs;

@Accessors(prefix = "_")
public class PositionEventArgs extends EventArgs {
    public PositionEventArgs(final int position) {
        _position = position;
    }

    @Getter
    private final int _position;
}
