package com.btc.common.utility;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.job.JobScheduler;
import android.content.Context;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import lombok.val;

import com.btc.common.contract.Contracts;

public final class ContextUtils {
    @NonNull
    public static String[] getStrings(
        @NonNull final Context context,
        @StringRes @NonNull final int[] stringIds) {
        Contracts.requireNonNull(context, "context == null");
        Contracts.requireNonNull(stringIds, "stringIds == null");

        final val strings = new String[stringIds.length];

        for (int i = 0; i < strings.length; i++) {
            strings[i] = context.getString(stringIds[i]);
        }

        return strings;
    }

    @Nullable
    public static WindowManager getWindowManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Nullable
    public static LayoutInflater getLayoutInflater(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Nullable
    public static ActivityManager getActivityManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    @Nullable
    public static PowerManager getPowerManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    }

    @Nullable
    public static AlarmManager getAlarmManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Nullable
    public static NotificationManager getNotificationManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Nullable
    public static KeyguardManager getKeyguardManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
    }

    @Nullable
    public static LocationManager getLocationManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Nullable
    public static SearchManager getSearchManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
    }

    @Nullable
    public static Vibrator getVibrator(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Nullable
    public static ConnectivityManager getConnectivityManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Nullable
    public static WifiManager getWifiManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Nullable
    public static AudioManager getAudioManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Nullable
    public static MediaRouter getMediaRouter(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (MediaRouter) context.getSystemService(Context.MEDIA_ROUTER_SERVICE);
    }

    @Nullable
    public static TelephonyManager getTelephonyManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @Nullable
    public static SubscriptionManager getSubscriptionManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (SubscriptionManager) context.getSystemService(Context
                                                                  .TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    @Nullable
    public static InputMethodManager getInputMethodManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Nullable
    public static UiModeManager getUiModeManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
    }

    @Nullable
    public static DownloadManager getDownloadManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    public static BatteryManager getBatteryManager(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    public static JobScheduler getJobScheduler(@NonNull final Context context) {
        Contracts.requireNonNull(context, "context == null");

        return (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    private ContextUtils() {
        Contracts.unreachable();
    }
}
