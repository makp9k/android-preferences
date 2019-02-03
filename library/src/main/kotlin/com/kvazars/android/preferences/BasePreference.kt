package com.kvazars.android.preferences

import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.atomic.AtomicReference

open class BasePreference<T>(private val defaultValue: T, initialValue: T) : Preference<T> {

    private val observers = CopyOnWriteArraySet<Observer<T>>()

    protected val value = AtomicReference<T>(initialValue)

    override fun get(): T {
        return value.get()
    }

    override fun set(value: T) {
        val oldValue = this.value.get()
        this.value.set(value)
        if (oldValue != value) {
            notifyObservers(value)
        }
    }

    override fun clear() {
        set(defaultValue)
    }

    override fun observe(observer: Observer<T>): Preference.Connection<T> {
        return observe(true, observer)
    }

    override fun observe(receiveInitialValue: Boolean, observer: Observer<T>): Preference.Connection<T> {
        observers += observer
        if (receiveInitialValue) {
            observer(this, get())
        }
        return Preference.Connection(this, observer)
    }

    override fun disconnect(observer: Observer<T>) {
        observers -= observer
    }

    private fun notifyObservers(value: T) {
        observers.forEach { it(this, value) }
    }
}
