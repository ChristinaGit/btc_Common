package com.btc.common.utility;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.btc.common.CharsetType;
import com.btc.common.contract.Contracts;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class UriUtils {
    public static final String NUMBER_PLACEHOLDER = "#";

    public static final String PATH_SEPARATOR = "/";

    public static final String SCHEMA_SEPARATOR = ":";

    @NonNull
    public static String encode(@NonNull final String str, @NonNull final CharsetType charsetType) {
        Contracts.requireNonNull(str, "str == null");
        Contracts.requireNonNull(charsetType, "charsetType == null");

        try {
            return URLEncoder.encode(str, charsetType.getCharsetName());
        } catch (final UnsupportedEncodingException e) {
            throw ExceptionUtils.asRuntimeException(e);
        }
    }

    @NonNull
    public static String decode(@NonNull final String str, @NonNull final CharsetType charsetType) {
        Contracts.requireNonNull(str, "str == null");
        Contracts.requireNonNull(charsetType, "charsetType == null");

        try {
            return URLDecoder.decode(str, charsetType.getCharsetName());
        } catch (final UnsupportedEncodingException e) {
            throw ExceptionUtils.asRuntimeException(e);
        }
    }

    @NonNull
    public static String combinePath(@NonNull final String... pathSegments) {
        Contracts.requireNonNull(pathSegments, "pathSegments == null");

        return TextUtils.join(PATH_SEPARATOR, pathSegments);
    }

    private UriUtils() {
        Contracts.unreachable();
    }
}
