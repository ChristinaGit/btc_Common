package com.btc.common.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.btc.common.contract.Contracts;

public final class SettingsIntentUtils {
    @NonNull
    public static Intent getApplicationDetailsSettingsIntent(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return new Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + context.getApplicationInfo().packageName));
    }

    public static void startOpenApplicationSettings(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        context.startActivity(getApplicationDetailsSettingsIntent(context));
    }

    private SettingsIntentUtils() {
        Contracts.unreachable();
    }
}
