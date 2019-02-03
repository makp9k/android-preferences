package com.kvazars.android.preferences.ui;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class ViewModel {

    private final ObservableBoolean mTrackingEnabled = new ObservableBoolean(false);
    private final ObservableField<CharSequence> mLogsText = new ObservableField<>("");

    public ObservableBoolean isTrackingEnabled() {
        return mTrackingEnabled;
    }

    public ObservableField<CharSequence> getLogsText() {
        return mLogsText;
    }
}
