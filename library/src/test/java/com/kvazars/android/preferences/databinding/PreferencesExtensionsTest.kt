package com.kvazars.android.preferences.databinding

import android.content.SharedPreferences
import android.databinding.*
import com.kvazars.android.preferences.persistent.*
import com.kvazars.android.preferences.persistent.stub.SharedPreferencesStub
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PreferencesExtensionsTest {

    private val prefs: SharedPreferences = SharedPreferencesStub()

    @Test
    fun shouldConnectAndDisconnectToObservableInt() {
        val observable = ObservableInt(0)
        val preference = prefs.int("key", 1)
        val binding = preference.connectTo(observable)

        assertThat(observable.get()).isEqualTo(1)

        preference.set(2)
        assertThat(observable.get()).isEqualTo(2)

        observable.set(3)
        assertThat(preference.get()).isEqualTo(3)

        binding.dispose()

        preference.set(4)
        assertThat(observable.get()).isEqualTo(3)

        observable.set(5)
        assertThat(preference.get()).isEqualTo(4)
    }

    @Test
    fun shouldConnectAndDisconnectToObservableFloat() {
        val observable = ObservableFloat(0F)
        val preference = prefs.float("key", 1F)
        val binding = preference.connectTo(observable)

        assertThat(observable.get()).isEqualTo(1F)

        preference.set(2F)
        assertThat(observable.get()).isEqualTo(2F)

        observable.set(3F)
        assertThat(preference.get()).isEqualTo(3F)

        binding.dispose()

        preference.set(4F)
        assertThat(observable.get()).isEqualTo(3F)

        observable.set(5F)
        assertThat(preference.get()).isEqualTo(4F)
    }

    @Test
    fun shouldConnectAndDisconnectToObservableLong() {
        val observable = ObservableLong(0L)
        val preference = prefs.long("key", 1L)
        val binding = preference.connectTo(observable)

        assertThat(observable.get()).isEqualTo(1L)

        preference.set(2L)
        assertThat(observable.get()).isEqualTo(2L)

        observable.set(3L)
        assertThat(preference.get()).isEqualTo(3L)

        binding.dispose()

        preference.set(4L)
        assertThat(observable.get()).isEqualTo(3L)

        observable.set(5L)
        assertThat(preference.get()).isEqualTo(4L)
    }

    @Test
    fun shouldConnectAndDisconnectToObservableBoolean() {
        val observable = ObservableBoolean(false)
        val preference = prefs.boolean("key", true)
        val binding = preference.connectTo(observable)

        assertThat(observable.get()).isEqualTo(true)

        preference.set(false)
        assertThat(observable.get()).isEqualTo(false)

        observable.set(true)
        assertThat(preference.get()).isEqualTo(true)

        binding.dispose()

        preference.set(false)
        assertThat(observable.get()).isEqualTo(true)

        observable.set(true)
        assertThat(preference.get()).isEqualTo(false)
    }

    @Test
    fun shouldConnectAndDisconnectToObservableField() {
        val observable = ObservableField("0")
        val preference = prefs.string("key", "1")
        val binding = preference.connectTo(observable)

        assertThat(observable.get()).isEqualTo("1")

        preference.set("2")
        assertThat(observable.get()).isEqualTo("2")

        observable.set("3")
        assertThat(preference.get()).isEqualTo("3")

        binding.dispose()

        preference.set("4")
        assertThat(observable.get()).isEqualTo("3")

        observable.set("5")
        assertThat(preference.get()).isEqualTo("4")
    }

}