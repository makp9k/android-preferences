package com.kvazars.android.preferences

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import android.widget.Toast
import com.kvazars.android.preferences.data.ApplicationPreferences

class SampleApplication : Application() {

    private lateinit var preferences: ApplicationPreferences

    override fun onCreate() {
        super.onCreate()

        preferences = ApplicationPreferences(PreferenceManager.getDefaultSharedPreferences(this))

        preferences.trackingEnabled.observe(false) { _, value ->
            Toast.makeText(this, "Tracking was ${if (value) "enabled" else "disabled"}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun getApplicationPreferences(context: Context): ApplicationPreferences {
            return (context.applicationContext as SampleApplication).preferences
        }
    }

}