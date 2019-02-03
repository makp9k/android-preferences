package com.kvazars.android.preferences.persistent

import android.content.SharedPreferences
import com.kvazars.android.preferences.Preference
import com.kvazars.android.preferences.persistent.stub.SharedPreferencesStub
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PersistentPreferenceTest {

    private val prefs: SharedPreferences = SharedPreferencesStub()

    @Test
    fun shouldStoreInteger() {
        shouldStoreValuesInPreference(
                { key, default: Int -> prefs.int(key, default) },
                { key, default: Int -> prefs.getInt(key, default) },
                0, 1
        )
    }

    @Test
    fun shouldStoreFloat() {
        shouldStoreValuesInPreference(
                { key, default: Float -> prefs.float(key, default) },
                { key, default: Float -> prefs.getFloat(key, default) },
                0F, 1F
        )
    }

    @Test
    fun shouldStoreLong() {
        shouldStoreValuesInPreference(
                { key, default: Long -> prefs.long(key, default) },
                { key, default: Long -> prefs.getLong(key, default) },
                0L, 1L
        )
    }

    @Test
    fun shouldStoreBoolean() {
        shouldStoreValuesInPreference(
                { key, default: Boolean -> prefs.boolean(key, default) },
                { key, default: Boolean -> prefs.getBoolean(key, default) },
                true, false
        )
    }

    @Test
    fun shouldStoreString() {
        shouldStoreValuesInPreference(
                { key, default: String -> prefs.string(key, default) },
                { key, default: String -> prefs.getString(key, default) },
                "string1", "string2"
        )
    }

    @Test
    fun shouldStoreStringSet() {
        shouldStoreValuesInPreference(
                { key, default: Set<String> -> prefs.stringSet(key, default) },
                { key, default: Set<String> -> prefs.getStringSet(key, default) },
                setOf("def1", "def2"), setOf("value1", "value2")
        )
    }

    private fun <T> shouldStoreValuesInPreference(preferenceCreator: (key: String, default: T) -> Preference<T>,
                                                  sharedPrefsValueRetriever: (key: String, default: T) -> T,
                                                  initial: T, new: T) {
        val key = "key"
        var preference = preferenceCreator(key, initial)

        assertThat(prefs.contains(key)).isFalse()
        assertThat(sharedPrefsValueRetriever(key, initial)).isEqualTo(initial)

        preference.set(new)

        preference = preferenceCreator(key, initial)
        assertThat(prefs.contains(key)).isTrue()
        assertThat(sharedPrefsValueRetriever(key, initial)).isEqualTo(new)

        preference.clear()

        assertThat(prefs.contains(key)).isTrue()
        assertThat(sharedPrefsValueRetriever(key, initial)).isEqualTo(initial)
    }

}
