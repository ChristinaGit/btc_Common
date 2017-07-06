package com.btc.common.extension.pagination;

public interface PaginateInterface {
    boolean isAllItemsLoaded();

    boolean isPageLoading();

    void onLoadNextPage();
}
