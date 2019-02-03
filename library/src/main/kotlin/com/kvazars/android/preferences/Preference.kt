package com.kvazars.android.preferences

typealias Observer<T> = (preference: Preference<T>, value: T) -> Unit

interface Preference<T> {
    fun get(): T

    fun set(value: T)

    fun clear()

    fun observe(observer: Observer<T>): Connection<T>

    fun observe(receiveInitialValue: Boolean, observer: Observer<T>): Connection<T>

    fun disconnect(observer: Observer<T>)

    class Connection<T>(private val preference: Preference<T>, private val observer: Observer<T>) : Disposable {
        override fun dispose() {
            preference.disconnect(observer)
        }
    }

    interface Disposable {
        fun dispose()
    }
}
