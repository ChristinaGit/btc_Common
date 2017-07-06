package com.btc.common.extension.eventArgs;

import android.net.Uri;
import android.support.annotation.Nullable;

import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.event.eventArgs.EventArgs;

@Accessors(prefix = "_")
public class UriEventArgs extends EventArgs {
    public UriEventArgs(@Nullable final Uri uri) {
        _uri = uri;
    }

    @Getter
    @Nullable
    private final Uri _uri;
}
