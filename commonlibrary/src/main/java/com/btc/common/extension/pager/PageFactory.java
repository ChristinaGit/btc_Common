package com.btc.common.extension.pager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public interface PageFactory {
    @NonNull
    Fragment createPageFragment(int position);

    int getPageCount();
}
