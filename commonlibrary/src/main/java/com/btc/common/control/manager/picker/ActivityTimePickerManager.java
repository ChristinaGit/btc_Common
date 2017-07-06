package com.btc.common.control.manager.picker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import rx.Observable;
import rx.Subscriber;

import com.btc.common.contract.Contracts;
import com.btc.common.control.adviser.ResourceAdviser;
import com.btc.common.control.manager.ReleasableManager;
import com.btc.common.event.generic.EventHandler;
import com.btc.common.extension.activity.ObservableActivity;
import com.btc.common.extension.fragment.datePicker.DateEventArgs;
import com.btc.common.extension.fragment.datePicker.DatePickerFragment;
import com.btc.common.extension.fragment.timePicker.TimeEventArgs;
import com.btc.common.extension.fragment.timePicker.TimePickerFragment;

import java.util.Calendar;

@Accessors(prefix = "_")
public class ActivityTimePickerManager extends ReleasableManager implements TimePickerManager {
    public ActivityTimePickerManager(
        @NonNull final ObservableActivity observableActivity,
        @NonNull final ResourceAdviser resourceAdviser) {
        super(Contracts.requireNonNull(resourceAdviser, "resourceAdviser == null"));
        Contracts.requireNonNull(observableActivity, "observableActivity == null");

        _observableActivity = observableActivity;
    }

    @NonNull
    @Override
    public Observable<DatePickerResult> pickDate(@NonNull final Calendar calendar) {
        Contracts.requireNonNull(calendar, "calendar == null");

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return Observable.create(new Observable.OnSubscribe<DatePickerResult>() {
            @Override
            public void call(final Subscriber<? super DatePickerResult> subscriber) {
                try {
                    final val datePickerFragment = new DatePickerFragment();
                    datePickerFragment.setInitialYear(year);
                    datePickerFragment.setInitialMonth(month);
                    datePickerFragment.setInitialDayOfMonth(dayOfMonth);
                    datePickerFragment
                        .getDateSetEvent()
                        .addHandler(new EventHandler<DateEventArgs>() {
                            @Override
                            public void onEvent(@NonNull final DateEventArgs eventArgs) {
                                Contracts.requireNonNull(eventArgs, "eventArgs == null");

                                datePickerFragment.getDateSetEvent().removeHandler(this);

                                //@formatter:off
                                final val result = DatePickerResult.selected(
                                    eventArgs.getYear(),
                                    eventArgs.getMonth(),
                                    eventArgs.getDayOfMonth());
                                //@formatter:on
                                subscriber.onNext(result);
                                subscriber.onCompleted();
                            }
                        });
                    datePickerFragment.show(getActivity().getSupportFragmentManager(), null);
                } catch (final Throwable error) {
                    subscriber.onError(error);
                }
            }
        });
    }

    @NonNull
    @Override
    public Observable<DatePickerResult> pickDate(
        final int year, final int month, final int dayOfMonth) {
        final val calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return pickDate(calendar);
    }

    @NonNull
    @Override
    public Observable<DatePickerResult> pickDate(final int year, final int month) {
        final val calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return pickDate(calendar);
    }

    @NonNull
    @Override
    public Observable<DatePickerResult> pickDate(final int year) {
        final val calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return pickDate(calendar);
    }

    @NonNull
    @Override
    public Observable<DatePickerResult> pickDate(final long time) {
        final val calendar = Calendar.getInstance();

        calendar.setTimeInMillis(time);

        return pickDate(calendar);
    }

    @NonNull
    @Override
    public Observable<DatePickerResult> pickDate() {
        final val calendar = Calendar.getInstance();

        return pickDate(calendar);
    }

    @NonNull
    @Override
    public Observable<TimePickerResult> pickTime(final int hourOfDay) {
        final val calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, 0);

        return pickTime(calendar);
    }

    @NonNull
    @Override
    public Observable<TimePickerResult> pickTime(final long time) {
        final val calendar = Calendar.getInstance();

        calendar.setTimeInMillis(time);

        return pickTime(calendar);
    }

    @NonNull
    @Override
    public Observable<TimePickerResult> pickTime() {
        final val calendar = Calendar.getInstance();

        return pickTime(calendar);
    }

    @NonNull
    @Override
    public Observable<TimePickerResult> pickTime(final int hourOfDay, final int minute) {
        final val calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        return pickTime(calendar);
    }

    @NonNull
    @Override
    public Observable<TimePickerResult> pickTime(@NonNull final Calendar calendar) {
        Contracts.requireNonNull(calendar, "calendar == null");

        final int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        return Observable.create(new Observable.OnSubscribe<TimePickerResult>() {
            @Override
            public void call(final Subscriber<? super TimePickerResult> subscriber) {
                try {
                    final val timePickerFragment = new TimePickerFragment();
                    timePickerFragment.setInitialHourOfDay(hourOfDay);
                    timePickerFragment.setInitialMinute(minute);
                    timePickerFragment
                        .getTimeSetEvent()
                        .addHandler(new EventHandler<TimeEventArgs>() {
                            @Override
                            public void onEvent(@NonNull final TimeEventArgs eventArgs) {
                                Contracts.requireNonNull(eventArgs, "eventArgs == null");

                                timePickerFragment.getTimeSetEvent().removeHandler(this);

                                final val result =
                                    TimePickerResult.selected(eventArgs.getHourOfDay(),
                                                              eventArgs.getMinute());
                                subscriber.onNext(result);
                                subscriber.onCompleted();
                            }
                        });
                    timePickerFragment.show(getActivity().getSupportFragmentManager(), null);
                } catch (final Throwable throwable) {
                    subscriber.onError(throwable);
                }
            }
        });
    }

    @NonNull
    protected final AppCompatActivity getActivity() {
        return getObservableActivity().asActivity();
    }

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final ObservableActivity _observableActivity;
}
