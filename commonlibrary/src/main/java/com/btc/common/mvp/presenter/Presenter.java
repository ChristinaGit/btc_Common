package com.btc.common.mvp.presenter;

import android.support.annotation.NonNull;

import com.btc.common.mvp.screen.Screen;

public interface Presenter<TScreen extends Screen> {
    void bindScreen(@NonNull TScreen screen);

    void unbindScreen();
}
