package com.kvazars.android.preferences

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BasePreferenceTest {

    class TestValuesTuple<T>(val defaultValue: T, val initialValue: T, val newValue: T)

    private val testSuiteValues = arrayListOf(
        TestValuesTuple(-1, 0, 100500),
        TestValuesTuple(-1,0, 100500F),
        TestValuesTuple(-1,0, 100500L),
        TestValuesTuple(false, false, true),
        TestValuesTuple(Object(), Object(), Object()),
        TestValuesTuple("defaultValue", "initialValue", "newValue"),
        TestValuesTuple(emptySet(), setOf("string1", "string2"), setOf("string11", "string12"))
    )

    @Test
    fun shouldStoreTestSuiteValues() {
        testSuiteValues.forEach {
            shouldStoreValue(it)
        }
    }

    private fun <T> shouldStoreValue(testValues: TestValuesTuple<T>) {
        val preference = BasePreference(testValues.defaultValue, testValues.initialValue)

        preference.set(testValues.newValue)

        assertThat(preference.get()).isEqualTo(testValues.newValue)
    }

    @Test
    fun shouldObserveTestSuiteValues() {
        testSuiteValues.forEach {
            shouldObserveValues(it)
            shouldObserveValuesButSkipInitial(it)
        }
    }

    private fun <T> shouldObserveValues(testValues: TestValuesTuple<T>) {
        val preference = BasePreference(testValues.defaultValue, testValues.initialValue)
        val observedPreferences = mutableListOf<Preference<T>>()
        val observedValues = mutableListOf<T>()
        preference.observe { p, value ->
            observedPreferences += p
            observedValues += value
        }

        preference.set(testValues.newValue)

        assertThat(observedPreferences).hasSize(2)
        assertThat(observedValues).hasSize(2)
        assertThat(observedValues).isEqualTo(listOf(testValues.initialValue, testValues.newValue))
    }

    private fun <T> shouldObserveValuesButSkipInitial(testValues: TestValuesTuple<T>) {
        val preference = BasePreference(testValues.defaultValue, testValues.initialValue)
        val observedPreferences = mutableListOf<Preference<T>>()
        val observedValues = mutableListOf<T>()
        preference.observe(false) { p, value ->
            observedPreferences += p
            observedValues += value
        }

        preference.set(testValues.newValue)

        assertThat(observedPreferences).hasSize(1)
        assertThat(observedValues).isEqualTo(listOf(testValues.newValue))
    }

    @Test
    fun shouldDisconnectObserversOfTestSuiteValues() {
        testSuiteValues.forEach {
            shouldDisconnectObserver(it)
        }
    }

    private fun <T> shouldDisconnectObserver(testValues: TestValuesTuple<T>) {
        val preference = BasePreference(testValues.defaultValue, testValues.initialValue)
        val observedPreferences = mutableListOf<Preference<T>>()
        val observedValues = mutableListOf<T>()
        val connection = preference.observe { p, value ->
            observedPreferences += p
            observedValues += value
        }

        preference.set(testValues.newValue)
        preference.set(testValues.initialValue)
        connection.dispose()
        preference.set(testValues.newValue)

        assertThat(observedPreferences).hasSize(3)
        assertThat(observedValues).isEqualTo(listOf(testValues.initialValue, testValues.newValue, testValues.initialValue))
    }

    @Test
    fun shouldClearTestSuiteValues() {
        testSuiteValues.forEach {
            shouldClearValue(it)
        }
    }

    private fun <T> shouldClearValue(testValues: TestValuesTuple<T>) {
        val preference = BasePreference(testValues.defaultValue, testValues.initialValue)

        preference.set(testValues.newValue)
        preference.clear()

        assertThat(preference.get()).isEqualTo(testValues.defaultValue)
    }

}
