package com.btc.common.control.manager.picker;

import android.support.annotation.NonNull;

import rx.Observable;

import java.util.Calendar;

public interface TimePickerManager {
    @NonNull
    Observable<DatePickerResult> pickDate(@NonNull final Calendar calendar);

    @NonNull
    Observable<DatePickerResult> pickDate(final int year, final int month, final int dayOfMonth);

    @NonNull
    Observable<DatePickerResult> pickDate(final int year, final int month);

    @NonNull
    Observable<DatePickerResult> pickDate(final int year);

    @NonNull
    Observable<DatePickerResult> pickDate(final long time);

    @NonNull
    Observable<DatePickerResult> pickDate();

    @NonNull
    Observable<TimePickerResult> pickTime(final int hourOfDay);

    @NonNull
    Observable<TimePickerResult> pickTime(final long time);

    @NonNull
    Observable<TimePickerResult> pickTime();

    @NonNull
    Observable<TimePickerResult> pickTime(final int hourOfDay, final int minute);

    @NonNull
    Observable<TimePickerResult> pickTime(@NonNull final Calendar calendar);
}
