package com.btc.common.extension.delegate.loading;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;

import java.lang.ref.WeakReference;

@Accessors(prefix = "_")
public class LoadingViewDelegate {
    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    @Nullable
    public final View getContentView() {
        final View contentView;

        if (_contentViewRef != null) {
            contentView = _contentViewRef.get();
        } else {
            contentView = null;
        }

        return contentView;
    }

    public final void setContentView(@Nullable final View contentView) {
        if (contentView == null) {
            _contentViewRef = null;
        } else {
            _contentViewRef = new WeakReference<>(contentView);
        }
    }

    @NonNull
    public final VisibilityHandler getDefaultChangeViewVisibilityHandler() {
        if (_defaultChangeViewVisibilityHandler == null) {
            _defaultChangeViewVisibilityHandler = new SimpleVisibilityHandler();
        }

        return _defaultChangeViewVisibilityHandler;
    }

    @Nullable
    public final View getErrorView() {
        final View errorView;

        if (_errorViewRef != null) {
            errorView = _errorViewRef.get();
        } else {
            errorView = null;
        }

        return errorView;
    }

    public final void setErrorView(@Nullable final View errorView) {
        if (errorView == null) {
            _errorViewRef = null;
        } else {
            _errorViewRef = new WeakReference<>(errorView);
        }
    }

    @Nullable
    public final View getLoadingView() {
        final View loadingView;

        if (_loadingViewRef != null) {
            loadingView = _loadingViewRef.get();
        } else {
            loadingView = null;
        }

        return loadingView;
    }

    public void setLoadingView(
        @Nullable final View loadingView) {
        if (loadingView == null) {
            _loadingViewRef = null;
        } else {
            _loadingViewRef = new WeakReference<>(loadingView);
        }
    }

    @Nullable
    public final View getNoContentView() {
        final View noContentView;

        if (_noContentViewRef != null) {
            noContentView = _noContentViewRef.get();
        } else {
            noContentView = null;
        }

        return noContentView;
    }

    public final void setNoContentView(@Nullable final View noContentView) {
        if (noContentView == null) {
            _noContentViewRef = null;
        } else {
            _noContentViewRef = new WeakReference<>(noContentView);
        }
    }

    public final void invalidateContentView() {
        final val contentView = getContentView();
        final val noContentView = getNoContentView();

        if (isContentVisible()) {
            if (hasContent()) {
                if (contentView != null) {
                    final val visibilityHandler = getInternalContentVisibilityHandler();
                    visibilityHandler.changeVisibility(contentView, true);
                }
                if (noContentView != null) {
                    final val visibilityHandler = getInternalNoContentVisibilityHandler();
                    visibilityHandler.changeVisibility(noContentView, false);
                }
            } else {
                if (noContentView != null) {
                    final val visibilityHandler = getInternalNoContentVisibilityHandler();
                    visibilityHandler.changeVisibility(noContentView, true);
                }
                if (contentView != null) {
                    final val visibilityHandler = getInternalContentVisibilityHandler();
                    visibilityHandler.changeVisibility(contentView, false);
                }
            }
        } else {
            if (contentView != null) {
                final val visibilityHandler = getInternalContentVisibilityHandler();
                visibilityHandler.changeVisibility(contentView, false);
            }
            if (noContentView != null) {
                final val visibilityHandler = getInternalNoContentVisibilityHandler();
                visibilityHandler.changeVisibility(noContentView, false);
            }
        }
    }

    public final void invalidateErrorView() {
        final val errorView = getErrorView();
        if (errorView != null) {
            final val visibilityHandler = getInternalErrorVisibilityHandler();
            visibilityHandler.changeVisibility(errorView, isErrorVisible());
        }
    }

    public final void invalidateLoadingView() {
        final val loadingView = getLoadingView();
        if (loadingView != null) {
            final val visibilityHandler = getInternalLoadingVisibilityHandler();
            visibilityHandler.changeVisibility(loadingView, isLoadingVisible());
        }
    }

    public final void setContentVisible(final boolean visible) {
        if (_contentVisible != visible) {
            final boolean anyViewVisible = isVisible();

            _contentVisible = visible;

            onContentVisibilityChanged();

            if (isVisible() != anyViewVisible) {
                onVisibilityChanged();
            }
        }
    }

    public final void setErrorVisible(final boolean visible) {
        if (_errorVisible != visible) {
            final boolean anyViewVisible = isVisible();

            _errorVisible = visible;

            onErrorVisibilityChanged();

            if (isVisible() != anyViewVisible) {
                onVisibilityChanged();
            }
        }
    }

    public final void setHasContent(final boolean hasContent) {
        if (_hasContent != hasContent) {
            _hasContent = hasContent;

            onContentChanged();
        }
    }

    public final void setLoadingVisible(final boolean visible) {
        if (_loadingVisible != visible) {
            final boolean anyViewVisible = isVisible();

            _loadingVisible = visible;

            onLoadingVisibilityChanged();

            if (isVisible() != anyViewVisible) {
                onVisibilityChanged();
            }
        }
    }

    @CallSuper
    public void hideAll() {
        setContentVisible(false);
        setLoadingVisible(false);
        setErrorVisible(false);
    }

    @CallSuper
    public void invalidateViews() {
        invalidateContentView();
        invalidateLoadingView();
        invalidateErrorView();
    }

    public boolean isVisible() {
        return isLoadingVisible() || isContentVisible() || isErrorVisible();
    }

    @CallSuper
    public void showContent(final boolean hasContent) {
        setHasContent(hasContent);
        setContentVisible(true);
        setLoadingVisible(false);
        setErrorVisible(false);
    }

    @CallSuper
    public void showContent() {
        showContent(true);
    }

    @CallSuper
    public void showError() {
        setContentVisible(false);
        setLoadingVisible(false);
        setErrorVisible(true);
    }

    @CallSuper
    public void showLoading() {
        setContentVisible(false);
        setLoadingVisible(true);
        setErrorVisible(false);
    }

    @NonNull
    protected final VisibilityHandler getInternalContentVisibilityHandler() {
        final val visibilityHandler = getContentVisibilityHandler();
        final val defaultVisibilityHandler = getDefaultChangeViewVisibilityHandler();

        return visibilityHandler == null ? defaultVisibilityHandler : visibilityHandler;
    }

    @NonNull
    protected final VisibilityHandler getInternalErrorVisibilityHandler() {
        final val visibilityHandler = getErrorVisibilityHandler();
        final val defaultVisibilityHandler = getDefaultChangeViewVisibilityHandler();

        return visibilityHandler == null ? defaultVisibilityHandler : visibilityHandler;
    }

    @NonNull
    protected final VisibilityHandler getInternalLoadingVisibilityHandler() {
        final val visibilityHandler = getLoadingVisibilityHandler();
        final val defaultVisibilityHandler = getDefaultChangeViewVisibilityHandler();

        return visibilityHandler == null ? defaultVisibilityHandler : visibilityHandler;
    }

    @NonNull
    protected final VisibilityHandler getInternalNoContentVisibilityHandler() {
        final val visibilityHandler = getNoContentVisibilityHandler();
        final val defaultVisibilityHandler = getDefaultChangeViewVisibilityHandler();

        return visibilityHandler == null ? defaultVisibilityHandler : visibilityHandler;
    }

    @CallSuper
    protected void onContentChanged() {
        invalidateContentView();
    }

    @CallSuper
    protected void onContentVisibilityChanged() {
        invalidateContentView();
    }

    @CallSuper
    protected void onErrorVisibilityChanged() {
        invalidateErrorView();
    }

    @CallSuper
    protected void onLoadingVisibilityChanged() {
        invalidateLoadingView();
    }

    @CallSuper
    protected void onVisibilityChanged() {
        if (_visibilityChangedListener != null) {
            _visibilityChangedListener.onVisibilityChanged();
        }
    }

    @Nullable
    private WeakReference<View> _contentViewRef;

    @Getter
    @Setter
    @Nullable
    private VisibilityHandler _contentVisibilityHandler;

    @Getter
    private boolean _contentVisible;

    @Nullable
    private VisibilityHandler _defaultChangeViewVisibilityHandler;

    @Nullable
    private WeakReference<View> _errorViewRef;

    @Getter
    @Setter
    @Nullable
    private VisibilityHandler _errorVisibilityHandler;

    @Getter
    private boolean _errorVisible;

    @Getter
    @Accessors(fluent = true, prefix = "_")
    private boolean _hasContent;

    @Nullable
    private WeakReference<View> _loadingViewRef;

    @Getter
    @Setter
    @Nullable
    private VisibilityHandler _loadingVisibilityHandler;

    @Getter
    private boolean _loadingVisible;

    @Nullable
    private WeakReference<View> _noContentViewRef;

    @Getter
    @Setter
    @Nullable
    private VisibilityHandler _noContentVisibilityHandler;

    @Nullable
    private VisibilityChangedListener _visibilityChangedListener;

    public interface VisibilityChangedListener {
        void onVisibilityChanged();
    }

    public static class Builder {
        @NonNull
        public LoadingViewDelegate build() {
            final val result = new LoadingViewDelegate();

            result.setContentView(_contentView);
            result.setContentVisibilityHandler(_contentVisibilityHandler);

            result.setErrorView(_errorView);
            result.setErrorVisibilityHandler(_errorVisibilityHandler);

            result.setNoContentView(_noContentView);
            result.setNoContentVisibilityHandler(_noContentVisibilityHandler);

            result.setLoadingView(_loadingView);
            result.setLoadingVisibilityHandler(_loadingVisibilityHandler);

            result._visibilityChangedListener = _visibilityChangedListener;

            result.invalidateViews();

            return result;
        }

        @NonNull
        public Builder setContentView(@Nullable final View contentView) {
            _contentView = contentView;

            return this;
        }

        @NonNull
        public Builder setContentVisibilityHandler(
            @Nullable final VisibilityHandler visibilityHandler) {
            _contentVisibilityHandler = visibilityHandler;

            return this;
        }

        @NonNull
        public Builder setErrorView(@Nullable final View errorView) {
            _errorView = errorView;

            return this;
        }

        @NonNull
        public Builder setErrorVisibilityHandler(
            @Nullable final VisibilityHandler visibilityHandler) {
            _errorVisibilityHandler = visibilityHandler;

            return this;
        }

        @NonNull
        public Builder setLoadingView(@Nullable final View loadingView) {
            _loadingView = loadingView;

            return this;
        }

        @NonNull
        public Builder setLoadingVisibilityHandler(
            @Nullable final VisibilityHandler visibilityHandler) {
            _loadingVisibilityHandler = visibilityHandler;

            return this;
        }

        @NonNull
        public Builder setNoContentView(@Nullable final View noContentView) {
            _noContentView = noContentView;

            return this;
        }

        @NonNull
        public Builder setNoContentVisibilityHandler(
            @Nullable final VisibilityHandler visibilityHandler) {
            _noContentVisibilityHandler = visibilityHandler;

            return this;
        }

        @NonNull
        public Builder setVisibilityChangedListener(
            @Nullable final VisibilityChangedListener visibilityChangedListener) {
            _visibilityChangedListener = visibilityChangedListener;

            return this;
        }

        @Nullable
        private View _contentView;

        @Nullable
        private VisibilityHandler _contentVisibilityHandler;

        @Nullable
        private View _errorView;

        @Nullable
        private VisibilityHandler _errorVisibilityHandler;

        @Nullable
        private View _loadingView;

        @Nullable
        private VisibilityHandler _loadingVisibilityHandler;

        @Nullable
        private View _noContentView;

        @Nullable
        private VisibilityHandler _noContentVisibilityHandler;

        @Nullable
        private VisibilityChangedListener _visibilityChangedListener;
    }
}
