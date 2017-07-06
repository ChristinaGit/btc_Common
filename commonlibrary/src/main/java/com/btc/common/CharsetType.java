package com.btc.common;

import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.contract.Contracts;

@Accessors(prefix = "_")
public enum CharsetType {
    ISO_8859_1("ISO-8859-1"),

    US_ASCII("US-ASCII"),

    UTF_16("UTF-16"),

    UTF_16BE("UTF-16BE"),

    UTF_16LE("UTF-16LE"),

    UTF_8("UTF-8");

    @Getter
    @NonNull
    private final String _charsetName;

    CharsetType(@NonNull final String charsetName) {
        Contracts.requireNonNull(charsetName, "charsetName == null");

        _charsetName = charsetName;
    }
}
