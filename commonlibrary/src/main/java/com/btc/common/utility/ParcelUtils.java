package com.btc.common.utility;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.btc.common.contract.Contracts;

public final class ParcelUtils {
    public static final byte BOOLEAN_TRUE = 1;

    public static final byte BOOLEAN_FALSE = 0;

    public static void writeBoolean(@NonNull final Parcel parcel, final boolean value) {
        Contracts.requireNonNull(parcel, "parcel == null");

        parcel.writeByte(value ? BOOLEAN_TRUE : BOOLEAN_FALSE);
    }

    public static boolean readBoolean(@NonNull final Parcel parcel) {
        Contracts.requireNonNull(parcel, "parcel == null");

        final boolean result;

        final byte booleanByte = parcel.readByte();

        if (booleanByte == BOOLEAN_FALSE) {
            result = false;
        } else if (booleanByte == BOOLEAN_TRUE) {
            result = true;
        } else {
            throw new RuntimeException("Illegal boolean representation: (byte)" + booleanByte);
        }

        return result;
    }

    private ParcelUtils() {
        Contracts.unreachable();
    }
}
