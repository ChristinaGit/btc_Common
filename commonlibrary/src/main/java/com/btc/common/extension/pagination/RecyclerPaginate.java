package com.btc.common.extension.pagination;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.contract.Contracts;

@Accessors(prefix = "_")
public class RecyclerPaginate implements Paginate {
    @NonNull
    public static RecyclerPaginate.Builder builder(
        @NonNull final RecyclerView recyclerView,
        @NonNull final PaginateInterface paginateInterface) {
        Contracts.requireNonNull(recyclerView, "recyclerView == null");
        Contracts.requireNonNull(paginateInterface, "paginateInterface == null");

        return new RecyclerPaginate.Builder(recyclerView, paginateInterface);
    }

    public RecyclerPaginate(
        @NonNull final RecyclerView recyclerView,
        @NonNull final PaginateInterface paginateInterface,
        final int loadingTriggerThreshold) {
        Contracts.requireNonNull(recyclerView, "recyclerView == null");
        Contracts.requireNonNull(paginateInterface, "paginateInterface == null");

        _recyclerView = recyclerView;
        _paginateInterface = paginateInterface;
        _loadingTriggerThreshold = loadingTriggerThreshold;
    }

    @Override
    public void bind() {
        getRecyclerView().addOnScrollListener(_checkOffsetOnScroll);
    }

    @Override
    public void checkPageLoading() {
        final val recyclerView = getRecyclerView();
        final val layoutManager = recyclerView.getLayoutManager();
        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = layoutManager.getItemCount();

        final int firstVisibleItemPosition;
        if (layoutManager instanceof LinearLayoutManager) {
            final val linearLayoutManager = (LinearLayoutManager) layoutManager;
            firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (layoutManager.getChildCount() > 0) {
                final val staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                firstVisibleItemPosition =
                    staggeredGridLayoutManager.findFirstVisibleItemPositions(null)[0];
            } else {
                firstVisibleItemPosition = 0;
            }
        } else {
            throw new IllegalStateException(
                RecyclerView.LayoutManager.class.getSimpleName() + " needs to subclass " +
                LinearLayoutManager.class.getSimpleName() + " or " +
                StaggeredGridLayoutManager.class.getSimpleName());
        }

        if ((totalItemCount - visibleItemCount) <=
            (firstVisibleItemPosition + getLoadingTriggerThreshold()) && totalItemCount > 0) {
            final val paginateInterface = getPaginateInterface();
            if (!paginateInterface.isPageLoading() && !paginateInterface.isAllItemsLoaded()) {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        final val paginateInterface = getPaginateInterface();
                        if (!paginateInterface.isPageLoading() &&
                            !paginateInterface.isAllItemsLoaded()) {
                            paginateInterface.onLoadNextPage();
                        }
                    }
                });
            }
        }
    }

    @CallSuper
    @Override
    public void unbind() {
        getRecyclerView().removeOnScrollListener(_checkOffsetOnScroll);
    }

    @CallSuper
    protected void onRecyclerViewScrolled(
        final RecyclerView recyclerView, final int dx, final int dy) {
        checkPageLoading();
    }

    @Getter(AccessLevel.PROTECTED)
    private final int _loadingTriggerThreshold;

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final PaginateInterface _paginateInterface;

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final RecyclerView _recyclerView;

    @NonNull
    private final RecyclerView.OnScrollListener _checkOffsetOnScroll =
        new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                onRecyclerViewScrolled(recyclerView, dx, dy);
            }
        };

    public static class Builder {
        public Builder(
            @NonNull final RecyclerView recyclerView,
            @NonNull final PaginateInterface paginateInterface) {
            Contracts.requireNonNull(recyclerView, "recyclerView == null");
            Contracts.requireNonNull(paginateInterface, "paginateInterface == null");

            _recyclerView = recyclerView;
            _paginateInterface = paginateInterface;
        }

        @NonNull
        public final Builder setLoadingTriggerThreshold(final int threshold) {
            _loadingTriggerThreshold = threshold;

            return this;
        }

        @NonNull
        public Paginate build() {
            return new RecyclerPaginate(_recyclerView,
                                        _paginateInterface,
                                        _loadingTriggerThreshold);
        }

        @NonNull
        private final PaginateInterface _paginateInterface;

        @NonNull
        private final RecyclerView _recyclerView;

        private int _loadingTriggerThreshold = 5;
    }
}
