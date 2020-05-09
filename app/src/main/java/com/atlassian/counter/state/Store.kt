package com.atlassian.counter.state

import androidx.compose.Model

@Model
class Store<T, A>(initialValue: T, val reducer: (T, A) -> T) {
    var value: T = initialValue

    fun send(action: A) {
        value = reducer(value, action)
    }
}