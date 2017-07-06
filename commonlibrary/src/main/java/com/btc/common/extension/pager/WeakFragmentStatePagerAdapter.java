package com.btc.common.extension.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.contract.Contracts;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;

@Accessors(prefix = "_")
public abstract class WeakFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    public final void notifyActivePageChanged(@Nullable final Integer activePage) {
        if (!Objects.equals(_activePage, activePage)) {
            _activePage = activePage;

            final int pageCount = getCount();
            for (int i = 0; i < pageCount; i++) {
                final val fragment = getFragment(i);
                if (fragment instanceof ActivePage) {
                    ((ActivePage) fragment).setPageActive(Objects.equals(i, _activePage));
                }
            }
        }
    }

    @Override
    public int getCount() {
        final int count;

        final val pageFactory = getPageFactory();
        if (pageFactory != null) {
            count = pageFactory.getPageCount();
        } else {
            count = 0;
        }

        return count;
    }

    @Nullable
    public Fragment getFragment(final int position) {
        final Fragment result;

        final Reference<Fragment> fragmentRef = getFragments().get(position);

        if (fragmentRef != null) {
            result = fragmentRef.get();
        } else {
            result = null;
        }

        return result;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment pageFragment;

        final val pageFactory = getPageFactory();

        if (pageFactory != null) {
            pageFragment = pageFactory.createPageFragment(position);
        } else {
            pageFragment = null;
        }

        return pageFragment;
    }

    @CallSuper
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final val item = super.instantiateItem(container, position);

        if (item instanceof Fragment) {
            final val fragment = (Fragment) item;

            onInstantiateFragment(fragment, position);
        }

        if (item instanceof ActivePage) {
            final val activePage = (ActivePage) item;

            activePage.setPageActive(Objects.equals(position, getActivePage()));
        }

        return item;
    }

    @CallSuper
    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        super.destroyItem(container, position, object);

        if (object instanceof Fragment) {
            final val fragment = (Fragment) object;

            onDestroyFragment(fragment, position);
        }
    }

    protected WeakFragmentStatePagerAdapter(@NonNull final FragmentManager fragmentManager) {
        super(Contracts.requireNonNull(fragmentManager, "fragmentManager == null"));
    }

    @CallSuper
    protected void onDestroyFragment(@NonNull final Fragment fragment, final int position) {
        Contracts.requireNonNull(fragment, "fragment == null");

        getFragments().remove(position);
    }

    @CallSuper
    protected void onInstantiateFragment(@NonNull final Fragment fragment, final int position) {
        Contracts.requireNonNull(fragment, "fragment == null");

        getFragments().append(position, new WeakReference<>(fragment));
    }

    @Getter(value = AccessLevel.PRIVATE)
    @NonNull
    private final SparseArray<Reference<Fragment>> _fragments = new SparseArray<>();

    @Getter
    @Nullable
    private Integer _activePage;

    @Getter
    @Setter
    @Nullable
    private PageFactory _pageFactory;
}
