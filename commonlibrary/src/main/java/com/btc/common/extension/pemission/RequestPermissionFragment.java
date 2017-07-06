package com.btc.common.extension.pemission;

import android.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.ConstantBuilder;
import com.btc.common.contract.Contracts;
import com.btc.common.event.Events;
import com.btc.common.event.generic.Event;
import com.btc.common.event.generic.ManagedEvent;
import com.btc.common.utility.PermissionUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Accessors(prefix = "_")
public class RequestPermissionFragment extends Fragment {
    protected static final int PERMISSION_REQUEST_CODE = 1;

    @NonNull
    public static Builder builder(@NonNull final Activity activity) {
        Contracts.requireNonNull(activity, "activity == null");

        return new Builder(activity);
    }

    private static final String _KEY_SAVED_STATE =
        ConstantBuilder.savedStateKey(RequestPermissionFragment.class, "saved_state");

    @NonNull
    public final Event<RequestPermissionsResultEventArgs> getPermissionResultEvent() {
        return _permissionResultEvent;
    }

    @Override
    public void onRequestPermissionsResult(
        final int requestCode,
        @NonNull final String[] permissions,
        @NonNull final int[] grantResults) {
        //@formatter:off
        super.onRequestPermissionsResult(
            requestCode,
            Contracts.requireNonNull(permissions, "permissions == null"),
            Contracts.requireNonNull(grantResults, "grantResults == null"));
        //@formatter:on

        _permissionsRequested = false;

        if (grantResults.length > 0) {
            for (int i = 0; i < permissions.length; i++) {
                final val permission = permissions[i];
                final int grantResult = grantResults[i];

                getUnrequestedPermissions().remove(permission);

                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    getGrantedPermissions().add(permission);
                } else if (grantResult == PackageManager.PERMISSION_DENIED) {
                    final val explanation = getPermissionExplanations().get(permission);
                    final boolean shouldShowExplanation =
                        ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                                                            permission);
                    if (shouldShowExplanation) {
                        if (explanation != null) {
                            getRequestExplanationPermissions().add(permission);
                        }
                    } else {
                        getNeverAskAgainPermissions().add(permission);
                    }
                    getDeniedPermissions().add(permission);
                }
            }
        }

        checkPermissionsState();
    }

    @Nullable
    @Override
    public View onCreateView(
        final LayoutInflater inflater,
        @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        final val view = super.onCreateView(inflater, container, savedInstanceState);

        if (savedInstanceState != null) {
            _state = Parcels.unwrap(savedInstanceState.getParcelable(_KEY_SAVED_STATE));
        }

        if (_state != null) {
            final val deniedPermissions = _state.getDeniedPermissions();
            if (deniedPermissions != null) {
                getDeniedPermissions().addAll(deniedPermissions);
            }

            final val grantedPermissions = _state.getGrantedPermissions();
            if (grantedPermissions != null) {
                getGrantedPermissions().addAll(grantedPermissions);
            }

            final val neverAskAgainPermissions = _state.getNeverAskPermissions();
            if (neverAskAgainPermissions != null) {
                getNeverAskAgainPermissions().addAll(neverAskAgainPermissions);
            }

            final val unrequestedPermissions = _state.getUnrequestedPermissions();
            if (unrequestedPermissions != null) {
                getUnrequestedPermissions().addAll(unrequestedPermissions);
            }

            final val requestExplanationPermissions = _state.getRequestExplanationPermissions();
            if (requestExplanationPermissions != null) {
                getRequestExplanationPermissions().addAll(requestExplanationPermissions);
            }

            final val permissionExplanations = _state.getPermissionExplanations();
            if (permissionExplanations != null) {
                getPermissionExplanations().putAll(permissionExplanations);
            }

            _permissionsRequested = _state.isPermissionsRequested();
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        checkPermissionsState();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState != null) {
            if (_state == null) {
                _state = new RequestPermissionsFragmentState();
            }

            _state.setDeniedPermissions(getDeniedPermissions());
            _state.setGrantedPermissions(getGrantedPermissions());
            _state.setNeverAskPermissions(getNeverAskAgainPermissions());
            _state.setUnrequestedPermissions(getUnrequestedPermissions());
            _state.setRequestExplanationPermissions(getRequestExplanationPermissions());
            _state.setPermissionExplanations(getPermissionExplanations());
            _state.setPermissionsRequested(_permissionsRequested);

            outState.putParcelable(_KEY_SAVED_STATE, Parcels.wrap(_state));
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (_currentPermissionExplanationDialog != null) {
            _currentPermissionExplanationDialog.dismiss();
            _currentPermissionExplanationDialog = null;
        }
    }

    public void startRequest(
        @NonNull final FragmentManager fragmentManager, @Nullable final String tag) {
        Contracts.requireNonNull(fragmentManager, "fragmentManager == null");

        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction().add(this, tag).commit();
        }
    }

    protected final void removeSelf() {
        getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }

    @NonNull
    protected String[] getPermissionsArray(@NonNull final List<String> permissions) {
        Contracts.requireNonNull(permissions, "permissions == null");

        return permissions.toArray(new String[permissions.size()]);
    }

    @CallSuper
    protected void performRequestPermissions() {
        if (!_permissionsRequested) {
            _permissionsRequested = true;

            final val unrequestedPermissions = getPermissionsArray(getUnrequestedPermissions());
            if (unrequestedPermissions.length > 0) {
                requestPermissions(unrequestedPermissions, PERMISSION_REQUEST_CODE);
            }
        }
    }

    protected void performShowPermissionExplanation(@NonNull final String permission) {
        Contracts.requireNonNull(permission, "permission == null");

        getRequestExplanationPermissions().remove(permission);

        _currentPermissionExplanationDialog = new AlertDialog.Builder(getActivity())
            .setMessage(getPermissionExplanations().get(permission))
            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    checkPermissionsState();
                }
            })
            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    getDeniedPermissions().remove(permission);
                    getUnrequestedPermissions().add(0, permission);

                    checkPermissionsState();
                }
            })
            .create();
        _currentPermissionExplanationDialog.setCanceledOnTouchOutside(false);
        _currentPermissionExplanationDialog.setCancelable(false);
        _currentPermissionExplanationDialog.show();
    }

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final List<String> _deniedPermissions = new ArrayList<>(1);

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final List<String> _grantedPermissions = new ArrayList<>(1);

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final List<String> _neverAskAgainPermissions = new ArrayList<>(1);

    @Getter
    @NonNull
    private final Map<String, String> _permissionExplanations = new HashMap<>(1);

    @NonNull
    private final ManagedEvent<RequestPermissionsResultEventArgs> _permissionResultEvent =
        Events.createEvent();

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final List<String> _requestExplanationPermissions = new ArrayList<>(1);

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final List<String> _unrequestedPermissions = new ArrayList<>(1);

    @Nullable
    private AlertDialog _currentPermissionExplanationDialog;

    private boolean _permissionsRequested = false;

    @Nullable
    private RequestPermissionsFragmentState _state;

    private void checkPermissionsState() {
        if (!getUnrequestedPermissions().isEmpty()) {
            performRequestPermissions();
        } else if (!getRequestExplanationPermissions().isEmpty()) {
            performShowPermissionExplanation(getRequestExplanationPermissions().get(0));
        } else {
            risePermissionResultEvent();
            removeSelf();
        }
    }

    private void risePermissionResultEvent() {
        final val deniedPermissions = getDeniedPermissions();
        final val requestExplanationPermissions = getRequestExplanationPermissions();

        deniedPermissions.addAll(requestExplanationPermissions);
        requestExplanationPermissions.clear();

        final val eventArgs = new RequestPermissionsResultEventArgs(deniedPermissions,
                                                                    getGrantedPermissions(),
                                                                    getNeverAskAgainPermissions(),
                                                                    getUnrequestedPermissions());
        _permissionResultEvent.rise(eventArgs);
    }

    @Accessors(prefix = "_")
    public static class Builder {
        public Builder(@NonNull final Activity activity) {
            Contracts.requireNonNull(activity, "activity == null");

            _activity = activity;
        }

        @NonNull
        public Builder addPermission(@NonNull final String permission) {
            Contracts.requireNonNull(permission, "permission == null");

            return addPermission(permission, null);
        }

        @NonNull
        public Builder addPermission(
            @NonNull final String permission, @Nullable final String explanation) {
            Contracts.requireNonNull(permission, "permission == null");

            if (explanation != null) {
                getExplanations().put(permission, explanation);
            }

            getPermissions().add(permission);

            return this;
        }

        @NonNull
        public Builder addPermissions(@NonNull final Collection<String> permissions) {
            Contracts.requireNonNull(permissions, "permissions == null");

            getPermissions().addAll(permissions);

            return this;
        }

        @NonNull
        public RequestPermissionFragment build() {
            final val checkPermissionsResult =
                PermissionUtils.checkPermissions(getActivity(), getPermissions());

            final val fragment = new RequestPermissionFragment();

            final val deniedPermissions = checkPermissionsResult.getDeniedPermissions();
            final val grantedPermissions = checkPermissionsResult.getGrantedPermissions();
            final val neverAskAgainPermissions =
                checkPermissionsResult.getNeverAskAgainPermissions();

            fragment.getUnrequestedPermissions().addAll(deniedPermissions);
            fragment.getGrantedPermissions().addAll(grantedPermissions);
            fragment.getNeverAskAgainPermissions().addAll(neverAskAgainPermissions);

            fragment.getPermissionExplanations().putAll(getExplanations());

            fragment.getUnrequestedPermissions().removeAll(fragment.getNeverAskAgainPermissions());

            return fragment;
        }

        @Getter(AccessLevel.PROTECTED)
        @NonNull
        private final Activity _activity;

        @Getter(AccessLevel.PROTECTED)
        @NonNull
        private final Map<String, String> _explanations = new HashMap<>();

        @Getter(AccessLevel.PROTECTED)
        @NonNull
        private final List<String> _permissions = new ArrayList<>();
    }
}
