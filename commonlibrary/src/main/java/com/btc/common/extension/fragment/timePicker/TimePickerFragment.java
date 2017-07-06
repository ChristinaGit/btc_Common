package com.btc.common.extension.fragment.timePicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.btc.common.event.Events;
import com.btc.common.event.generic.Event;
import com.btc.common.event.generic.ManagedEvent;

@Accessors(prefix = "_")
public class TimePickerFragment extends AppCompatDialogFragment
    implements TimePickerDialog.OnTimeSetListener {
    @NonNull
    public final Event<TimeEventArgs> getTimeSetEvent() {
        return _timeSetEvent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(),
                                    this,
                                    getInitialHourOfDay(),
                                    getInitialMinute(),
                                    DateFormat.is24HourFormat(getActivity()));
    }

    @CallSuper
    @Override
    public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
        _timeSetEvent.rise(new TimeEventArgs(hourOfDay, minute));
    }

    @NonNull
    private final ManagedEvent<TimeEventArgs> _timeSetEvent = Events.createEvent();

    @Getter
    @Setter
    private int _initialHourOfDay;

    @Getter
    @Setter
    private int _initialMinute;
}
