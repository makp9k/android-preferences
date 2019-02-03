package com.kvazars.android.preferences.data

import android.content.SharedPreferences
import com.kvazars.android.preferences.persistent.boolean

class ApplicationPreferences(prefs: SharedPreferences) {

    val trackingEnabled by prefs.boolean(true)

}