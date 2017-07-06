package com.btc.common.extension.sharedPreferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.btc.common.contract.Contracts;

import java.util.Set;

public abstract class SharedPreferencesEditorWrapper extends SharedPreferencesWrapper {
    protected SharedPreferencesEditorWrapper(
        @NonNull final SharedPreferences baseSharedPreferences) {
        super(Contracts.requireNonNull(baseSharedPreferences, "baseSharedPreferences == null"));
    }

    protected final void clear() {
        save(edit().clear());
    }

    protected final void putBoolean(@NonNull final String key, @Nullable final Boolean value) {
        Contracts.requireNonNull(key, "key == null");

        if (value != null) {
            putBoolean(key, (boolean) value);
        } else {
            remove(key);
        }
    }

    protected final void putBoolean(@NonNull final String key, final boolean value) {
        Contracts.requireNonNull(key, "key == null");

        save(edit().putBoolean(key, value));
    }

    protected final <T extends Enum<T>> void putEnum(
        @NonNull final String key, @Nullable final T value) {
        Contracts.requireNonNull(key, "key == null");

        if (value != null) {
            putString(key, value.name());
        } else {
            remove(key);
        }
    }

    protected final void putFloat(@NonNull final String key, @Nullable final Float value) {
        Contracts.requireNonNull(key, "key == null");

        if (value != null) {
            putFloat(key, (float) value);
        } else {
            remove(key);
        }
    }

    protected final void putFloat(@NonNull final String key, final float value) {
        Contracts.requireNonNull(key, "key == null");

        save(edit().putFloat(key, value));
    }

    protected final void putInt(@NonNull final String key, @Nullable final Integer value) {
        Contracts.requireNonNull(key, "key == null");

        if (value != null) {
            putInt(key, (int) value);
        } else {
            remove(key);
        }
    }

    protected final void putInt(@NonNull final String key, final int value) {
        Contracts.requireNonNull(key, "key == null");

        save(edit().putInt(key, value));
    }

    protected final void putLong(@NonNull final String key, @Nullable final Long value) {
        Contracts.requireNonNull(key, "key == null");

        if (value != null) {
            putLong(key, (long) value);
        } else {
            remove(key);
        }
    }

    protected final void putLong(@NonNull final String key, final long value) {
        Contracts.requireNonNull(key, "key == null");

        save(edit().putLong(key, value));
    }

    protected final void putString(@NonNull final String key, @Nullable final String value) {
        Contracts.requireNonNull(key, "key == null");

        save(edit().putString(key, value));
    }

    protected final void putStringSet(
        @NonNull final String key, @Nullable final Set<String> value) {
        Contracts.requireNonNull(key, "key == null");

        save(edit().putStringSet(key, value));
    }

    protected void remove(@NonNull final String key) {
        Contracts.requireNonNull(key, "key == null");

        save(edit().remove(key));
    }

    protected void save(@NonNull final SharedPreferences.Editor editor) {
        Contracts.requireNonNull(editor, "editor == null");

        editor.apply();
    }
}
