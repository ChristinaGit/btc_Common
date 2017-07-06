package com.btc.common.utility;

import android.support.annotation.NonNull;

import com.btc.common.contract.Contracts;

public final class MimeTypeUtils {
    @NonNull
    public static String combineDirContentType(
        @NonNull final String companyName, @NonNull final String contentTypeSimpleName) {
        Contracts.requireNonNull(companyName, "companyName == null");
        Contracts.requireNonNull(contentTypeSimpleName, "contentTypeSimpleName == null");

        return "vnd.android.cursor.dir/vnd." + companyName + "." + contentTypeSimpleName;
    }

    @NonNull
    public static String combineItemContentType(
        @NonNull final String companyName, @NonNull final String contentTypeSimpleName) {
        Contracts.requireNonNull(companyName, "companyName == null");
        Contracts.requireNonNull(contentTypeSimpleName, "contentTypeSimpleName == null");

        return "vnd.android.cursor.item/vnd." + companyName + "." + contentTypeSimpleName;
    }

    private MimeTypeUtils() {
        Contracts.unreachable();
    }
}
