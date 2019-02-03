package com.kvazars.android.preferences.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kvazars.android.preferences.Preference
import com.kvazars.android.preferences.SampleApplication
import com.kvazars.android.preferences.data.ApplicationPreferences
import com.kvazars.android.preferences.databinding.connectTo
import com.kvazars.preferences.R
import com.kvazars.preferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var applicationPreferences: ApplicationPreferences
    private val disposables = mutableListOf<Preference.Disposable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applicationPreferences = SampleApplication.getApplicationPreferences(this)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val viewModel = ViewModel()
        binding.model = viewModel

        initTrackingSwitch(viewModel)
        initLogsText(viewModel)
    }

    private fun initTrackingSwitch(viewModel: ViewModel) {
        val preferenceBinding = applicationPreferences.trackingEnabled.connectTo(
            viewModel.trackingEnabled
        )
        disposables += preferenceBinding
    }

    private fun initLogsText(viewModel: ViewModel) {
        val connection = applicationPreferences.trackingEnabled.observe({ _, value ->
            val logs = buildString {
                append(viewModel.logsText.get())
                append("Preference isTrackingEnabled was set to: $value")
                append("\n")
                append("\n")
            }
            viewModel.logsText.set(logs)
        })
        disposables += connection
    }

    override fun onDestroy() {
        disposables.forEach { it.dispose() }
        super.onDestroy()
    }
}
