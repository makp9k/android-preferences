package com.kvazars.android.preferences;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.kvazars.android.preferences.data.ApplicationPreferences;


public class SampleApplication extends Application {

    private ApplicationPreferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        mPreferences = new ApplicationPreferences(PreferenceManager.getDefaultSharedPreferences(this));

        mPreferences.isTrackingEnabled().observe(false, (preference, value) -> {
            Toast.makeText(this, "Tracking was " + (value ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
            return kotlin.Unit.INSTANCE;
        });
    }

    public static ApplicationPreferences getApplicationPreferences(Context context) {
        return ((SampleApplication) context.getApplicationContext()).mPreferences;
    }
}
