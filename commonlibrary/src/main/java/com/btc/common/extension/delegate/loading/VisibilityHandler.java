package com.btc.common.extension.delegate.loading;

import android.support.annotation.NonNull;
import android.view.View;

public interface VisibilityHandler {
    void changeVisibility(@NonNull View view, boolean visible);
}
