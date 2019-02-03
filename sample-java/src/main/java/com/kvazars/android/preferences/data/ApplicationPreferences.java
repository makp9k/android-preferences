package com.kvazars.android.preferences.data;

import android.content.SharedPreferences;

import com.kvazars.android.preferences.Preference;
import com.kvazars.android.preferences.persistent.PersistentPreferenceHelper;

public class ApplicationPreferences {

    private final Preference<Boolean> mTrackingEnabled;

    public ApplicationPreferences(SharedPreferences prefs) {
        mTrackingEnabled = PersistentPreferenceHelper.asBooleanProperty(prefs, "trackingEnabled", false);
    }

    public Preference<Boolean> isTrackingEnabled() {
        return mTrackingEnabled;
    }
}
