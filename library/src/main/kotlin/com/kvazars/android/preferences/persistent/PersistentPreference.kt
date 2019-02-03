@file:JvmName("PersistentPreferenceHelper")

package com.kvazars.android.preferences.persistent

import android.content.SharedPreferences
import com.kvazars.android.preferences.BasePreference
import com.kvazars.android.preferences.Preference
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class PersistentPreference<T>(
        defaultValue: T,
        reader: () -> T,
        private val writer: (T) -> Unit
) : BasePreference<T>(
        defaultValue, reader()
) {
    override fun set(value: T) {
        writer(value)
        super.set(value)
    }
}

private open class PersistentPreferenceDelegate<T>(
        private val initializer: (propertyName: String) -> Preference<T>
) : ReadOnlyProperty<Any, Preference<T>> {
    private lateinit var preference: Preference<T>

    override fun getValue(thisRef: Any, property: KProperty<*>): Preference<T> {
        if (!::preference.isInitialized) {
            preference = initializer(property.name)
        }
        return preference
    }
}

/*
    String
 */
fun SharedPreferences.string(defaultValue: String): ReadOnlyProperty<Any, Preference<String>> {
    return PersistentPreferenceDelegate { key -> string(key, defaultValue) }
}

@JvmName("asStringProperty")
fun SharedPreferences.string(key: String, defaultValue: String): Preference<String> {
    return PersistentPreference(
            defaultValue,
            { getString(key, defaultValue) },
            { value -> edit().putString(key, value).apply() }
    )
}

/*
    String set
 */
fun SharedPreferences.stringSet(defaultValue: Set<String>): ReadOnlyProperty<Any, Preference<Set<String>>> {
    return PersistentPreferenceDelegate { key -> stringSet(key, defaultValue) }
}

@JvmName("asStringSetProperty")
fun SharedPreferences.stringSet(key: String, defaultValue: Set<String>): Preference<Set<String>> {
    return PersistentPreference(
            defaultValue,
            { getStringSet(key, defaultValue) },
            { value -> edit().putStringSet(key, value).apply() }
    )
}

/*
    Int
 */
fun SharedPreferences.int(defaultValue: Int): ReadOnlyProperty<Any, Preference<Int>> {
    return PersistentPreferenceDelegate { key -> int(key, defaultValue) }
}

@JvmName("asIntProperty")
fun SharedPreferences.int(key: String, defaultValue: Int): Preference<Int> {
    return PersistentPreference(
            defaultValue,
            { getInt(key, defaultValue) },
            { value -> edit().putInt(key, value).apply() }
    )
}

/*
    Long
 */
fun SharedPreferences.long(defaultValue: Long): ReadOnlyProperty<Any, Preference<Long>> {
    return PersistentPreferenceDelegate { key -> long(key, defaultValue) }
}

@JvmName("asLongProperty")
fun SharedPreferences.long(key: String, defaultValue: Long): Preference<Long> {
    return PersistentPreference(
            defaultValue,
            { getLong(key, defaultValue) },
            { value -> edit().putLong(key, value).apply() }
    )
}

/*
    Float
 */
fun SharedPreferences.float(defaultValue: Float): ReadOnlyProperty<Any, Preference<Float>> {
    return PersistentPreferenceDelegate { key -> float(key, defaultValue) }
}

@JvmName("asFloatProperty")
fun SharedPreferences.float(key: String, defaultValue: Float): Preference<Float> {
    return PersistentPreference(
            defaultValue,
            { getFloat(key, defaultValue) },
            { value -> edit().putFloat(key, value).apply() }
    )
}

/*
    Boolean
 */
fun SharedPreferences.boolean(defaultValue: Boolean): ReadOnlyProperty<Any, Preference<Boolean>> {
    return PersistentPreferenceDelegate { key -> boolean(key, defaultValue) }
}

@JvmName("asBooleanProperty")
fun SharedPreferences.boolean(key: String, defaultValue: Boolean): Preference<Boolean> {
    return PersistentPreference(
            defaultValue,
            { getBoolean(key, defaultValue) },
            { value -> edit().putBoolean(key, value).apply() }
    )
}

/*
    Generic
 */
fun <T> SharedPreferences.generic(
        defaultValue: T,
        reader: SharedPreferences.(key: String, defaultValue: T) -> T,
        writer: SharedPreferences.(key: String, value: T) -> Unit
): ReadOnlyProperty<Any, Preference<T>> {
    return PersistentPreferenceDelegate { key -> generic(key, defaultValue, reader, writer) }
}

@JvmName("asGenericProperty")
fun <T> SharedPreferences.generic(
        key: String,
        defaultValue: T,
        reader: SharedPreferences.(key: String, defaultValue: T) -> T,
        writer: SharedPreferences.(key: String, value: T) -> Unit
): Preference<T> {
    return PersistentPreference(
            defaultValue,
            { reader(key, defaultValue) },
            { value -> writer(key, value) }
    )
}
