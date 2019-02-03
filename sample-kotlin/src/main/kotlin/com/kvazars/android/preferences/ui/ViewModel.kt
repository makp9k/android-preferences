package com.kvazars.android.preferences.ui

import android.databinding.ObservableBoolean
import android.databinding.ObservableField

class ViewModel {

    val trackingEnabled = ObservableBoolean(false)
    val logsText = ObservableField<CharSequence>("")

}