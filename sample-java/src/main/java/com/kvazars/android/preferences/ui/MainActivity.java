package com.kvazars.android.preferences.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kvazars.android.preferences.Preference;
import com.kvazars.android.preferences.R;
import com.kvazars.android.preferences.SampleApplication;
import com.kvazars.android.preferences.data.ApplicationPreferences;
import com.kvazars.android.preferences.databinding.ActivityMainBinding;
import com.kvazars.android.preferences.databinding.PreferenceBinding;
import com.kvazars.android.preferences.databinding.PreferenceDatabindingHelper;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    private ApplicationPreferences mApplicationPreferences;
    private List<Preference.Disposable> mDisposables = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplicationPreferences = SampleApplication.getApplicationPreferences(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewModel viewModel = new ViewModel();
        binding.setModel(viewModel);

        initTrackingSwitch(viewModel);
        initLogsText(viewModel);
    }

    private void initTrackingSwitch(ViewModel viewModel) {
        PreferenceBinding<Boolean> preferenceBinding = PreferenceDatabindingHelper.connectTo(
                mApplicationPreferences.isTrackingEnabled(),
                viewModel.isTrackingEnabled()
        );
        mDisposables.add(preferenceBinding);
    }

    private void initLogsText(ViewModel viewModel) {
        Preference.Connection<Boolean> connection = mApplicationPreferences.isTrackingEnabled().observe((preference, value) -> {
            StringBuilder stringBuilder = new StringBuilder(viewModel.getLogsText().get());
            stringBuilder.append(String.format("Preference isTrackingEnabled was set to %s", String.valueOf(value)));
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            viewModel.getLogsText().set(stringBuilder);
            return Unit.INSTANCE;
        });
        mDisposables.add(connection);
    }

    @Override
    protected void onDestroy() {
        for (Preference.Disposable disposable : mDisposables) {
            disposable.dispose();
        }
        super.onDestroy();
    }
}
