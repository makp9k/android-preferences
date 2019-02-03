# Android Preferences

With this library dealing with application preferences becomes much more expressive and concise.

It is indented to be applied to kotlin projects in the first place, but nothing stops you from using it in the good old java apps.
The core idea is to create a clean abstraction on top of the android shared preferences (or any other persistence mechanism) and provide a convenient and thread-safe API for reading, writing and observing preference values.

## Release notes
### 0.0.4
Bugfixing

### 0.0.3
Ability to clear preference and set its value back to the default
```kotlin
preference.clear()
```

## Usage

#### Primitive types

An app preference of a primitive type can be declared with a single line of code

```kotlin
val myPreference by sharedPrefs.boolean(true)
```

Where ``sharedPrefs`` is a reference to the android ``SharedPreferences``, and the default value of the property is set to ``true``.
Such minimalistic syntax is provided in kotlin by using [Delegated Properties](https://kotlinlang.org/docs/reference/delegated-properties.html) and [Extension functions](https://kotlinlang.org/docs/reference/extensions.html)

#### Custom types

Along with all primitive types that ``SharedPreferences`` support it is also possible to store any type of a value by utilizing the ``SharedPreferences.generic(defaultValue, reader, writer)`` extension method.
The use of this method is pretty straightforward. It takes 3 parameters: ``defaultValue``, ``reader`` and ``writer``, where

* ``defaultValue`` is self-explanatory

* The ``reader`` is a function that is used to retrieve the data from a ``SharedPreferences``. It has the following signature:
```kotlin
SharedPreferences.(key: String, defaultValue: T) -> T
```
It takes a key of a type ``String`` and a ``defaultValue`` of a preference type as input parameters and should return a value of a corresponding type. For instance, this is a basic implementation of a reader that returns an instance of a ``Point`` type:

```kotlin
{ key, defValue ->
  val serializedValue = getString(key, null)
  if (serializedValue == null) {
      defValue
  } else {
      val components = serializedValue.split(',')
      Point(components[0].toInt(), components[1].toInt())
  }
}
```

* The ``writer`` function, in turn, has following signature:
```kotlin
SharedPreferences.(key: String, value: T) -> Unit
```

It is used to serialize the value and store it in the android `SharedPreferences`. Continuing the example, the writer function will be:
```kotlin
{ key, value ->
  val serializedValue = "${value.x},${value.y}"
  edit().putString(key, serializedValue).apply()
}
```

So the complete declaration of an app preference of a type ``Point`` would be something like this:
```kotlin
val pointPreference by prefs.generic(
    Point(0,0),
    { key, defValue ->
        val serializedValue = getString(key, null)
        if (serializedValue == null) {
            defValue
        } else {
            val components = serializedValue.split(',')
            Point(components[0].toInt(), components[1].toInt())
        }
    },
    { key, value ->
        val serializedValue = "${value.x},${value.y}"
        edit().putString(key, serializedValue).apply()
    }
)
```

Sometimes it makes sense to extract lambdas in order to reuse them multiple times.

#### Accessing stored values

To get the actual value of the preference call
```kotlin
val value = preference.get()
```

Similarly, to save new value just call
```kotlin
preference.set(value)
```

#### Reacting to changes

The most interesting part of this library is the ability to observe preference changes. Simply call
```kotlin
preference.observe { pref, value -> /* your code here */ }
```
and the lambda passed into the observe method will be called immediately with an actual preference value and subsequently every time the value changes.

If there is no need in receiving the initial preference value, the overloaded method to the rescue:
```kotlin
preference.observe(false) { pref, value -> /* your code here */ }
```

#### Preventing memory leaks

Please don't forget to call
```kotlin
preference.disconnect(observer)
```
to avoid possible memory leaks. Do not hesitate to use android studio profiler to verify the correctness of your code.

For convenience purposes, the ``Preference.observe()`` returns an object of a ``Disposable`` type, representing a connection between the preference and the listener.
This object has a single method called ``Disposable.dispose()`` which automatically invokes ``preference.disconnect(observer)``, eliminating the need for keeping references to all preferences listeners.
Is it especially useful if there are plenty of listeners that should be disconnected altogether after, let's say ``onDestroy()`` event.
Then the code would look something like this:

```kotlin
private val disposables = mutableListOf<Preference.Disposable>()

override fun onCreate() {
    ...

    preference1
        .observe { _, _ -> /* your code here */ }
        .apply { disposables.add(this) }

    preference2
        .observe { _, _ -> /* your code here */ }
        .apply { disposables.add(this) }

    preference3
        .observe { _, _ -> /* your code here */ }
        .apply { disposables.add(this) }
}

override fun onDestroy() {
    ...

    disposables.forEach { it.dispose() }
}
```

Much cleaner!

#### Databinding

There are also a couple of extension methods that provide a neat way of binding application preferences directly to the android databinding observables.

Simply declare your databinding observable and then call
```kotlin
preference.connectTo(myDatabindingObservable)
```
That's it!

#### Sample

For a complete sample please refer to [sample-kotlin](sample-kotlin) or [sample-java](sample-java) projects.
