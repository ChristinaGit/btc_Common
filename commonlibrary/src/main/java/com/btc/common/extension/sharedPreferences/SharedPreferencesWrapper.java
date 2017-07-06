package com.btc.common.extension.sharedPreferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.contract.Contracts;

import org.apache.commons.lang3.EnumUtils;

import java.util.Map;
import java.util.Set;

@Accessors(prefix = "_")
public abstract class SharedPreferencesWrapper {
    public static final String DEFAULT_STRING = null;

    public static final int DEFAULT_INT = 0;

    public static final boolean DEFAULT_BOOLEAN = false;

    public static final long DEFAULT_LONG = 0L;

    public static final float DEFAULT_FLOAT = 0F;

    public static final Set<String> DEFAULT_STRING_SET = null;

    protected SharedPreferencesWrapper(@NonNull final SharedPreferences baseSharedPreferences) {
        Contracts.requireNonNull(baseSharedPreferences, "baseSharedPreferences == null");

        _baseSharedPreferences = baseSharedPreferences;
    }

    protected final boolean getBoolean(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getBoolean(key, DEFAULT_BOOLEAN);
    }

    @Nullable
    protected final <T extends Enum<T>> T getEnum(
        @NonNull final String key, @NonNull final Class<T> enumClass) {
        Contracts.requireNonNull(key, "key == null");
        Contracts.requireNonNull(enumClass, "enumClass == null");

        return getEnum(key, enumClass, null);
    }

    @Nullable
    protected final <T extends Enum<T>> T getEnum(
        @NonNull final String key, @NonNull final Class<T> enumClass, @Nullable final T defValue) {
        Contracts.requireNonNull(key, "key == null");
        Contracts.requireNonNull(enumClass, "enumClass == null");

        final T result;

        if (contains(key)) {
            final val enumName = getString(key);
            result = EnumUtils.getEnum(enumClass, enumName);
        } else {
            result = defValue;
        }

        return result;
    }

    protected final float getFloat(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getFloat(key, DEFAULT_FLOAT);
    }

    protected final int getInt(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getInt(key, DEFAULT_INT);
    }

    protected final long getLong(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getLong(key, DEFAULT_LONG);
    }

    @Nullable
    protected final Boolean getNullableBoolean(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getNullableBoolean(key, null);
    }

    @Nullable
    protected final Boolean getNullableBoolean(
        @NonNull final String key, @Nullable final Boolean defValue) {
        Contracts.requireNonNull(key, "key == null");

        return contains(key) ? (Boolean) getBoolean(key) : defValue;
    }

    @Nullable
    protected final Float getNullableFloat(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getNullableFloat(key, null);
    }

    @Nullable
    protected final Float getNullableFloat(
        @NonNull final String key, @Nullable final Float defValue) {
        Contracts.requireNonNull(key, "key == null");

        return contains(key) ? (Float) getFloat(key) : defValue;
    }

    @Nullable
    protected final Integer getNullableInt(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getNullableInt(key, null);
    }

    @Nullable
    protected final Integer getNullableInt(
        @NonNull final String key, @Nullable final Integer defValue) {
        Contracts.requireNonNull(key, "key == null");

        return contains(key) ? (Integer) getInt(key) : defValue;
    }

    @Nullable
    protected final Long getNullableLong(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getNullableLong(key, null);
    }

    @Nullable
    protected final Long getNullableLong(
        @NonNull final String key, @Nullable final Long defValue) {
        Contracts.requireNonNull(key, "key == null");

        return contains(key) ? (Long) getLong(key) : defValue;
    }

    @Nullable
    protected final String getString(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getString(key, DEFAULT_STRING);
    }

    @Nullable
    protected final Set<String> getStringSet(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getStringSet(key, DEFAULT_STRING_SET);
    }

    protected boolean contains(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        return getBaseSharedPreferences().contains(key);
    }

    @NonNull
    protected SharedPreferences.Editor edit() {
        return getBaseSharedPreferences().edit();
    }

    protected Map<String, ?> getAll() {
        return getBaseSharedPreferences().getAll();
    }

    protected boolean getBoolean(@NonNull final String key, final boolean defValue) {
        Contracts.requireNonNull(key, "key == null");

        return getBaseSharedPreferences().getBoolean(key, defValue);
    }

    protected float getFloat(@NonNull final String key, final float defValue) {
        Contracts.requireNonNull(key, "key == null");

        return getBaseSharedPreferences().getFloat(key, defValue);
    }

    protected int getInt(@NonNull final String key, final int defValue) {
        Contracts.requireNonNull(key, "key == null");

        return getBaseSharedPreferences().getInt(key, defValue);
    }

    protected long getLong(@NonNull final String key, final long defValue) {
        Contracts.requireNonNull(key, "key == null");

        return getBaseSharedPreferences().getLong(key, defValue);
    }

    @Nullable
    protected String getString(@NonNull final String key, @Nullable final String defValue) {
        Contracts.requireNonNull(key, "key == null");

        return getBaseSharedPreferences().getString(key, defValue);
    }

    @Nullable
    protected Set<String> getStringSet(
        @NonNull final String key, @Nullable final Set<String> defValues) {
        Contracts.requireNonNull(key, "key == null");

        return getBaseSharedPreferences().getStringSet(key, defValues);
    }

    protected void registerOnSharedPreferenceChangeListener(
        @NonNull final SharedPreferences.OnSharedPreferenceChangeListener listener) {
        Contracts.requireNonNull(listener, "listener == null");

        getBaseSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
    }

    protected void unregisterOnSharedPreferenceChangeListener(
        @NonNull final SharedPreferences.OnSharedPreferenceChangeListener listener) {
        Contracts.requireNonNull(listener, "listener == null");

        getBaseSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final SharedPreferences _baseSharedPreferences;
}
