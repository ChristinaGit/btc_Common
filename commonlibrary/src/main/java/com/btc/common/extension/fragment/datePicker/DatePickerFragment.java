package com.btc.common.extension.fragment.datePicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.btc.common.event.Events;
import com.btc.common.event.generic.Event;
import com.btc.common.event.generic.ManagedEvent;

@Accessors(prefix = "_")
public class DatePickerFragment extends AppCompatDialogFragment
    implements DatePickerDialog.OnDateSetListener {
    @NonNull
    public final Event<DateEventArgs> getDateSetEvent() {
        return _dateSetEvent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        return onCreateDatePickerDialog();
    }

    @CallSuper
    @Override
    public void onDateSet(
        final DatePicker view, final int year, final int month, final int dayOfMonth) {
        _dateSetEvent.rise(new DateEventArgs(year, month, dayOfMonth));
    }

    @NonNull
    protected DatePickerDialog onCreateDatePickerDialog() {
        return new DatePickerDialog(getActivity(),
                                    this,
                                    getInitialYear(),
                                    getInitialMonth(),
                                    getInitialDayOfMonth());
    }

    @NonNull
    private final ManagedEvent<DateEventArgs> _dateSetEvent = Events.createEvent();

    @Getter
    @Setter
    private int _initialDayOfMonth;

    @Getter
    @Setter
    private int _initialMonth;

    @Getter
    @Setter
    private int _initialYear;
}
