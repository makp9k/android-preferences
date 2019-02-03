package com.kvazars.android.preferences.persistent.stub

import android.content.SharedPreferences

class SharedPreferencesStub : SharedPreferences {

    private val storage = mutableMapOf<String, Any?>()

    override fun contains(key: String): Boolean {
        return storage.containsKey(key)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return storage[key] as? Boolean ?: defValue
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        //no op
    }

    override fun getInt(key: String, defValue: Int): Int {
        return storage[key] as? Int ?: defValue
    }

    override fun getAll(): MutableMap<String, *> {
        return storage
    }

    override fun edit(): SharedPreferences.Editor {
        return EditorStub(this)
    }

    override fun getLong(key: String, defValue: Long): Long {
        return storage[key] as? Long ?: defValue
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return storage[key] as? Float ?: defValue
    }

    @Suppress("UNCHECKED_CAST")
    override fun getStringSet(
        key: String,
        defValues: MutableSet<String>
    ): MutableSet<String> {
        return storage[key] as? MutableSet<String> ?: defValues
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {

    }

    override fun getString(key: String, defValue: String): String {
        return storage[key] as? String ?: defValue
    }

    class EditorStub(private val sharedPreferencesStub: SharedPreferencesStub) :
        SharedPreferences.Editor {

        private val tmpStorage = sharedPreferencesStub.storage.toMutableMap()

        override fun clear(): SharedPreferences.Editor {
            tmpStorage.clear()
            return this
        }

        override fun putLong(key: String, value: Long): SharedPreferences.Editor {
            tmpStorage[key] = value
            return this
        }

        override fun putInt(key: String, value: Int): SharedPreferences.Editor {
            tmpStorage[key] = value
            return this
        }

        override fun remove(key: String): SharedPreferences.Editor {
            tmpStorage.remove(key)
            return this
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
            tmpStorage[key] = value
            return this
        }

        override fun putStringSet(
            key: String,
            values: MutableSet<String>?
        ): SharedPreferences.Editor {
            tmpStorage[key] = values
            return this
        }

        override fun commit(): Boolean {
            sharedPreferencesStub.storage.apply {
                clear()
                putAll(tmpStorage)
            }
            return true
        }

        override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
            tmpStorage[key] = value
            return this
        }

        override fun apply() {
            commit()
        }

        override fun putString(key: String, value: String?): SharedPreferences.Editor {
            tmpStorage[key] = value
            return this
        }
    }
}
